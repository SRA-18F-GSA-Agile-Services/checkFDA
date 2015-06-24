package com.sra.searchfda.service

import grails.converters.JSON
import grails.transaction.Transactional
import groovyx.gpars.GParsPool

@Transactional
class SearchService {

	static int maxResults=500 //maximum results desired from each database
	boolean useFiltering=true
	def grailsApplication
	def openFDAService
	def stateService
	
	List<Map> datasets=[ //dataset names
		[path:"food/enforcement",group:"recalls"],
		[path:"drug/label",group:"labels"],
		[path:"drug/event",group:"events"],
		[path:"drug/enforcement",group:"recalls"],
		[path:"device/event",group:"events"],
		[path:"device/enforcement",group:"recalls"]
	]

	Map executeSearch(String query) {
		if (grailsApplication.config.checkfda.localData) {
			return federatedSearchMock();
		} else {
			return parallelFederatedSearch(query);
		}
	}

	/**
	 * Perform a federated search across the 6 Open FDA datasets using
	 * a string-based natural language query.  Returns a JSON object with a map
	 * from group names to list of objects for those groups.
	 */
	Map federatedSearch(String query) {
		long t0=System.currentTimeMillis()
		Map<List<Map>> results=new HashMap<List<Map>>()
		datasets.each { ds -> //iterate across each dataset
			List<Map> result=filterResults(ds,search(ds,query)) //get search results for the dataset
			String group=ds.group
			if (results[group]==null) results[group]=[]
			//log.info(ds+" has "+result.size())
			results[group]+=result
		}
		long t1=System.currentTimeMillis()
		log.info("Serial Federated Search Time:"+(t1-t0))
		return(results) //return the result as JSON
	}
	
	Map parallelFederatedSearch(String query) {
		long t0=System.currentTimeMillis()
		Map<List<Map>> results=new HashMap<List<Map>>()
		List<Map> presults=null
		GParsPool.withPool(datasets.size()) {
			presults=datasets.collectParallel { ds ->
				List<Map> result=filterResults(ds,search(ds,query)) //get search results for the dataset
				[group:ds.group,result:result]
			}
		}
		presults.each { item ->
			String group=item.group
			if (results[group]==null) results[group]=[]
			results[group]+=item.result
		}
		long t1=System.currentTimeMillis()
		log.info("Parallel Federated Search Time:"+(t1-t0))
		return(results) //return the result as JSON
	}
	
	def timingComparison(String query) {
		long t0=System.currentTimeMillis()
		def result=federatedSearch(query)
		long t1=System.currentTimeMillis()
		log.info("Federated time:"+(t1-t0))
		long t3=System.currentTimeMillis()
		def presult=parallelFederatedSearch(query)
		long t4=System.currentTimeMillis()
		log.info("Parallel time:"+(t4-t3))
		log.info("result length="+result.size())
		log.info("presult length="+presult.size())
		return(presult)
	}

	Map federatedSearchMock() {
		Map<List<Map>> results=new HashMap<List<Map>>()
		datasets.each { ds ->
			String group=ds.group
			results[group] = results[group] ?: [];
			results[group] += JSON.parse(grailsApplication.parentContext.getResource("data/OpenFDA-" + ds.path.split('/').join('-') + ".txt")?.file?.getText() ?: "{results: []}").results
		}
		return(results)
	}

	/**
	 * Perform a search against the desired dataset with the given query returning
	 * a List of Maps.
	 */
	private List<Map> search(dataset,String query) {
		int count=0 // count of results for far
		List<Map> results=[] //to accumulate results
		while(true) { //while we still have results
			String result=openFDAService.query(dataset.path,query,100,count) //get a result from open fda
			if (result==null) break
			Map js=JSON.parse(result) //parse the json into a map
			int total=js.meta.results.total //get the total for the overall query
			log.info(dataset.path+" has "+total) //report (for now) how many total hits the dataset had
			results+=js.results //add the results
			count+=js.results.size() //update our count
			if (count>=total) break //if we're done with paging
			if (count>=maxResults) break //if we've reached the limit desired for each database
		}
		//log.info("total in list="+results.size())
		return(results)
	}

	private String getFilterText(){
		return grailsApplication.parentContext.getResource("data/filters.txt").file.text
	}

	private List<String> loadFilters() {
		String filterText=getFilterText()
		List<String> filters = []
		filterText.eachLine { line ->
			if (!line.startsWith("#")) {
				filters << line.trim()
			}
		}
		return (filters)
	}
	
	private void addDerivedFields(Map dataset,Map result,Map resultMap) {
		  resultMap.dataset=dataset.path //add a dataset field
		  if (dataset.group=="recalls") {
			  if (result.distribution_pattern!=null) {
			    resultMap.distribution_states=stateService.getStates(result.distribution_pattern)
			  }
		  }
	}
	
	private List<Map> filterResults(Map dataset,List<Map> results) {
		if (!useFiltering) return(results)
		List<String> filters=loadFilters()
		List<Map> newMap=new ArrayList<Map>()
		for(Map result:results) {
		  Map resultMap=new HashMap()
		  addDerivedFields(dataset,result,resultMap)
		  for(String filter:filters) {
			if (filter.startsWith(dataset.group+".")) {
				String[] path=filter.split("\\.").tail()
				filterResult(path,resultMap,result)
			}
		  }
		  newMap<<resultMap
		}
		return(newMap)
	}
	
	private void filterResult(String[] filter,Map resultMap,Map result) {
		String key=filter.first()
		String[] rest=filter.tail()
		if (result.containsKey(key)) {
	    	if (rest.size()==0) { //end of list
				resultMap[key]=result[key] //move value across
				return
			}  else {
//				String type=result[key]?.class?.name //find type
				if ((result[key] instanceof List)) { //if we are dealing with an array
				  if (resultMap[key]==null) { //if we don't h=ave an array to receive the array yet 
					  List<Map> nList=new ArrayList<Map>() //make an array of maps
					  result[key].each { //and fill it with empty hashmaps of the right length
						  nList<<new HashMap()
					  }
					  resultMap[key]=nList
				  }
				  int cnt=0 //use a counter for traversing the output list
				  result[key].each { //for each element of the list the thing we're copying from
					  filterResult(rest,resultMap[key][cnt],it)
					  cnt++
				  }
				} else {
			      if (resultMap[key]==null) resultMap[key]=new HashMap() //for default case we'll need a hashmap if it doesn't exist
				  filterResult(rest,resultMap[key],result[key]) //recurse over key
				}
			}
		}
	}
}
