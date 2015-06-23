package com.sra.searchfda.service

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class SearchService {

	def grailsApplication
	def openFDAService
	List<Map> datasets=[ //dataset names
		[path:"food/enforcement",group:"recalls"],
		[path:"drug/label",group:"labels"],
		[path:"drug/event",group:"events"],
		[path:"drug/enforcement",group:"recalls"],
		[path:"device/event",group:"events"],
		[path:"device/enforcement",group:"recalls"]
	]

	/**
	 * Perform a federated search across the 6 Open FDA datasets using
	 * a string-based natural language query.  Returns a JSON object with a map
	 * from group names to list of objects for those groups.
	 */
	def Map federatedSearch(String query) {
		Map<List<Map>> results=new HashMap<List<Map>>()
		datasets.each { ds -> //iterate across each dataset
			List<Map> result=search(ds.path,query) //get search results for the dataset
			String group=ds.group
			if (results[group]==null) results[group]=[]
			//println(ds+" has "+result.size())
			results[group]+=result
		}
		return(results) //return the result as JSON
	}

	/**
	 * Perform a search against the desired dataset with the given query returning
	 * a List of Maps.
	 */
	private def List<Map> search(String dataset,String query) {
		int count=0 // count of results for far
		List<Map> results=[] //to accumulate results
		while(true) { //while we still have results
			String result=openFDAService.query(dataset,query,100,count) //get a result from open fda
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
