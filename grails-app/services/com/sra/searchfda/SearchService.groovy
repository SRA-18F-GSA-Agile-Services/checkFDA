package com.sra.searchfda

import grails.converters.JSON
import grails.transaction.Transactional

import org.codehaus.groovy.grails.web.json.JSONElement

@Transactional
class SearchService {

	def grailsApplication
	String urlBase="https://api.fda.gov/" //baseURL for Open FDA API
	List<String> datasets=[ //dataset names
		"food/enforcement",
		"drug/label",
		"drug/event",
		"drug/enforcement",
		"device/event",
		"device/enforcement"
	]

	/**
	 * Perform a federated search across the 6 Open FDA datasets using
	 * a string-based natural language query.  Returns a JSON list of results.
	 */
	def String federatedSearch(String query) {
		List<Map> results=[] //accumulate results  
		datasets.each { ds -> //iterate across each dataset
			List<Map> result=search(ds,query) //get search results for the dataset
			//println(ds+" has "+result.size())
			results+=result //append list to our overall result
		}
		return((results as JSON).toString()) //return the result as JSON
	}

	/**
	 * Perform a search against the desired dataset with the given query returning
	 * a List of Maps.
	 */
	private def List<Map> search(String dataset,String query) {
		int count=0 // count of results for far
		String apiToken=grailsApplication.config.openfdaapi.token //get api token from our config
		List<Map> results=[] //to accumulate results
		while(true) { //while we still have results
			String url=urlBase+dataset+".json?api_key="+apiToken+"&search="+URLEncoder.encode(query)+"&limit=100&skip="+count //construct url
			String result=null //variable for json result from url
			try {
				result=new URL(url).text //fetch the json back from the url
			} catch (Exception e) {
			  break
			}
			Map js=JSON.parse(result) //parse the json into a map
			int total=js.meta.results.total //get the total for the overall query
			println(dataset+" has "+total) //report (for now) how many total hits the dataset had
			results+=js.results //add the results
			count+=js.results.size() //update our count
			if (count>=total) break //if we're done with paging
			break //for now limit to 100 return results from any one dataset
		}
		//println("total in list="+results.size())
		return(results)
	}
}
