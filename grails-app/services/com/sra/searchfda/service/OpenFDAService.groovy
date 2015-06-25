package com.sra.searchfda.service

import grails.transaction.Transactional

@Transactional
class OpenFDAService {

	def grailsApplication
	String urlBase="https://api.fda.gov/" //baseURL for Open FDA API
	static long lastQuery=0 //when did our last query take place
	long millis=1000*60/240 //how long we have to wait between queries
	Object lock=new Object() //force the threads to move through in single file

	def String 	query(String dataset,String query,int limit=100,int skip=0) {
		String apiToken=grailsApplication.config.openfdaapi.token //get api token from our config
		String url=urlBase+dataset+".json?api_key="+apiToken+"&search="+URLEncoder.encode(query)+"&limit="+limit+"&skip="+skip //construct url
		//println("url="+url)
		String result=null //variable for json result from url
		try {
			//introducing this delay will undercut our parallel queries (but with a scaled up elasticsearch it wouldn't normally be a problem)
			synchronized(lock) {
				long time=System.currentTimeMillis()
				if ((time-lastQuery)<millis) {
					sleep(millis-(time-lastQuery))
					//println("sleeping "+(millis-(time-lastQuery)))
				}
				result=new URL(url).text //fetch the json back from the url
				lastQuery=time //reset last queried time
			}
		} catch (FileNotFoundException e) {
			//this means no data
		} catch (IOException e) {
			//this might be a 429 for querying too fast
			println(e.toString())
		}
		return(result)
	}
}
