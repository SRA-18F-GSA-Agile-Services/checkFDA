package com.sra.searchfda

import grails.converters.JSON
import grails.transaction.Transactional

import org.codehaus.groovy.grails.web.json.JSONElement

@Transactional
class SearchService {

	def grailsApplication
	String urlBase="https://api.fda.gov/"
	String apiKey=""
	List<String> datasets=[
		"food/enforcement",
		"drug/label",
		"drug/event",
		"drug/enforcement",
		"device/event",
		"device/enforcement"
	]

	def federatedSearch(String query) {
		int count=0
		List<Map> results=[]
		datasets.each { ds ->
			List<Map> result=search(ds,query)
			//println(ds+" has "+result.size())
			count+=result.size()
			results+=result
		}
		println("count="+count)
		return(results as JSON)
	}

	def List<Map> search(String dataset,String query) {
		int count=0
		String apiToken=grailsApplication.config.openfdaapi.token

		List<Map> results=[]
		while(true) {
			String url=urlBase+dataset+".json?api_key="+apiToken+"&search="+URLEncoder.encode(query)+"&limit=100&skip="+count
			String result=null
			try {
				result=new URL(url).text
			} catch (Exception e) {
			  break
			}
			JSONElement js=JSON.parse(result)
			int total=js.meta.results.total
			println(dataset+" has "+total)
			results+=js.results
			count+=js.results.size()
			//println("count="+count+" of "+total)
			if (count>=total) break
			break
		}
		//println("total in list="+results.size())
		return(results)
	}
}
