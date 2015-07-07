package com.sra.searchfda.service

import com.sra.searchfda.domain.DataSet
import grails.converters.JSON
import grails.transaction.Transactional
import groovyx.gpars.GParsPool

import org.codehaus.groovy.grails.commons.GrailsApplication

@Transactional
class SearchService {

	static final int MAXRESULTS=500 //maximum results desired from each database
	static final boolean USEFILTERING=true

	GrailsApplication grailsApplication
	OpenFDAService openFDAService
	StateService stateService

	final Map datasetTotals = [:]

	Map executeSearch(String query) {
		if (grailsApplication.config.checkfda.localData) {
			return federatedSearchMock()
		}
		return parallelFederatedSearch(query)
	}
	
	Integer getDatasetTotal(DataSet dataset) {
		Integer total = datasetTotals[dataset.name] as Integer
		if (total!=null){
			 return(total)
		}
		String result=openFDAService.query(dataset.url,"",1,0) //get a result from open fda

		if (result==null) {
			log.warn("dataset " + dataset.url + " empty count query gave null response")
			//datasetTotals[dataset.path]=0
			return(0)
		}

		Map srch=JSON.parse(result) //parse the json into a map
		total=srch.meta.results.total
		//println("total="+total)
		datasetTotals[dataset.name] = total
		return(total)
	}

	/**
	 * Perform a federated search across the 6 Open FDA datasets using
	 * a string-based natural language query.  Returns a JSON object with a map
	 * from group names to list of objects for those groups.
	 */
	Map federatedSearch(String query) {
		long t0=System.currentTimeMillis()
        Map<List<Map>> results = [:]
		Map meta= [:]

		DataSet.list().each { DataSet dataSet -> //iterate across each dataset
			//iterate across each dataset
			Map result=filterResults(dataSet,search(dataSet,parseQueryTerms(query))) //get search results for the dataset
			processSearchResult(results,result,meta,dataSet.groupName,dataSet.path)
		}
		long t1=System.currentTimeMillis()
		log.info("Serial Federated Search Time:"+(t1-t0))
		results.meta=meta
		return(results) //return the result as JSON
	}
	
	private void processSearchResult(Map<String,List<Map>> results,Map result,Map meta,String group,String path) {
			if (results[group]==null) {
				results[group]=[]
			}
			results[group]+=result.results
			meta[path.replace("/","-")]=result.meta
			Map gmeta=meta[group]
			if (gmeta==null) {
				gmeta= [:]
				gmeta.hits=0
				gmeta.total=0
				meta[group]=gmeta
			}
			if (result.meta.hits!=null) {
				gmeta.hits+=result.meta.hits
			}
			if (result.meta.total!=null) {
				gmeta.total+=result.meta.total
			}
	}

	Map parallelFederatedSearch(String query) {
		long t0=System.currentTimeMillis()
		Map<String,List<Map>> results= [:]
		List<Map> presults=null
		Map meta= [:]

		List<DataSet> dataSets = DataSet.list()

		GParsPool.withPool(dataSets.size()) {
			presults=dataSets.collectParallel { ds ->
				Map result=filterResults(ds,search(ds,parseQueryTerms(query))) //get search results for the dataset
				[group:ds.groupName,result:result,ds:ds.path]
			}
		}
		Set<String> usedGroups=[]
		presults.each { item ->
			usedGroups.add(item.group)
			processSearchResult(results,item.result,meta,item.group,item.ds)
		}
		double maxRatio=0.0
		String predictedGroup=null
		usedGroups.each { grp ->
			if (meta[grp]!=null) {
				if (meta[grp].total>0) {
					double fraction=meta[grp].hits/meta[grp].total
					meta[grp].fraction=fraction
					if (fraction>maxRatio) {
						maxRatio=fraction
						predictedGroup=grp
					}
				}
			}
		}
		meta['predicted-group']=predictedGroup
		long t1=System.currentTimeMillis()
		log.info("Parallel Federated Search Time:"+(t1-t0))
		results.meta=meta
		return(results) //return the result as JSON
	}

	Map timingComparison(String query) {
		long t0=System.currentTimeMillis()
		Map result=federatedSearch(query)
		long t1=System.currentTimeMillis()
		log.info("Federated time:"+(t1-t0))
		long t3=System.currentTimeMillis()
		Map presult=parallelFederatedSearch(query)
		long t4=System.currentTimeMillis()
		log.info("Parallel time:"+(t4-t3))
		log.info("result length="+result.size())
		log.info("presult length="+presult.size())
		return(presult)
	}
	
	/**
	 * Perform a search against the desired dataset with the given query returning
	 * a List of Maps.
	 */
	protected Map search(DataSet dataset,String query) {
		int count=0 // count of results for far
		Map meta=[:]
		List<Map> results=[] //to accumulate results
		while(true) { //while we still have results
			String result=openFDAService.query(dataset.url, query, 100, count) //get a result from open fda
			if (result==null){ 
				break
			}
			Map js=JSON.parse(result) //parse the json into a map
			int total=js.meta.results.total //get the total for the overall query
			meta.hits=total
			meta.total=getDatasetTotal(dataset)
			log.info(dataset.path+" has "+total) //report (for now) how many total hits the dataset had
			results+=js.results //add the results
			count+=js.results.size() //update our count
			if (count>=total) {
				break //if we're done with paging
			}
			if (count>=MAXRESULTS) {
				break //if we've reached the limit desired for each database
			}
		}
		//log.info("total in list="+results.size())
		[meta:meta,results:results]
	}

    Map federatedSearchMock() {
        Map<List<Map>> results = [:]

		DataSet.list().each { ds ->
            String group = ds.groupName
            results[group] = results[group] ?: []
            results[group] += JSON.parse(grailsApplication.parentContext.getResource("data/OpenFDA-" + ds.path.split('/').join('-') + ".txt")?.file?.getText() ?: "{results: []}").results
        }
        return (results)
    }

    private String getFilterText() {
        return grailsApplication.parentContext.getResource("data/filters.txt").file.text
    }

	private List<String> loadFilters() {
        String filterText = getFilterText()
        List<String> filters = []
        filterText.eachLine { line ->
            if (!line.startsWith("#")) {
                filters << line.trim()
            }
        }
        return (filters)
    }

	private void addDerivedFields(DataSet dataset, Map result,Map resultMap) {
		  resultMap.dataset=dataset.path //add a dataset field
		  if (dataset.groupName=="recalls") {
			  if (result.distribution_pattern!=null) {
			    resultMap.distribution_states=stateService.getStates(result.distribution_pattern)
			  }
		  }
	}

	private Map filterResults(DataSet dataset, Map results) {
		if (!USEFILTERING) {
			return(results)
		}
		List<String> filters=loadFilters()
		List<Map> newMap=[]
		for(Map result:results.results) {
			Map resultMap=[:]
			addDerivedFields(dataset,result,resultMap)
			for(String filter:filters) {
				if (filter.startsWith(dataset.groupName+".")) {
					String[] path=filter.split("\\.").tail()
					filterResult(path,resultMap,result)
				}
			}
			newMap<<resultMap
		}
		[meta:results.meta,results:newMap]
	}
	
	private void filterResult(String[] filter, Map resultMap, Map result) {
        String key = filter.first()
        String[] rest = filter.tail()
        if (result.containsKey(key)) {
            if (rest.size() == 0) { //end of list
                resultMap[key] = result[key] //move value across
                return
            }
//			String type=result[key]?.class?.name //find type
            if ((result[key] instanceof List)) { //if we are dealing with an array
                if (resultMap[key] == null) { //if we don't h=ave an array to receive the array yet
                    List<Map> nList = [] //make an array of maps
                    result[key].each { //and fill it with empty hashmaps of the right length
                        nList << [:]
                    }
                    resultMap[key] = nList
                }
                int cnt = 0 //use a counter for traversing the output list
                result[key].each { //for each element of the list the thing we're copying from
                    filterResult(rest, resultMap[key][cnt], it)
                    cnt++
                }
            } else {
                if (resultMap[key] == null) {
					resultMap[key] = [:]
				}
                //for default case we'll need a hashmap if it doesn't exist
                filterResult(rest, resultMap[key], result[key]) //recurse over key
            }
        }
    }

    String parseQueryTerms(String queryString) {
        List nonQuotedTerms = Arrays.asList(queryString.replaceAll(/"(.*?)"/, '').split())
        List quotedTerms = (queryString =~ /"(.*?)"/).collect { it[0] }

        List allTerms = quotedTerms + nonQuotedTerms

        String parsedQuery = allTerms.join(" AND ")

        parsedQuery
    }
}
