package com.sra.searchfda

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class SearchService {

	def grailsApplication
	def OpenFDAService
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
		List<Map> results=[] //to accumulate results
		while(true) { //while we still have results
			String result=OpenFDAService.query(dataset,query,100,count) //get a result from open fda
			if (result==null) break
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
