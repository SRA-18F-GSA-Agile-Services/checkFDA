package com.sra.searchfda.service

import grails.transaction.Transactional
import org.codehaus.groovy.grails.commons.GrailsApplication

@Transactional
class OpenFDAService {
	
	GrailsApplication grailsApplication
	final String urlBase="https://api.fda.gov/" //baseURL for Open FDA API
	static long lastQuery=0 //when did our last query take place
	final long millis=1000*60/240 //how long we have to wait between queries
	Object lock=new Object() //force the threads to move through in single file

	def String 	query(String dataset,String query,int limit=100,int skip=0) {
		String apiToken=grailsApplication.config.openfdaapi.token //get api token from our config
		String url=urlBase+dataset+".json?api_key="+apiToken+"&search="+URLEncoder.encode(query)+"&limit="+limit+"&skip="+skip //construct url
		//println("url="+url)
		int retries=3
		String result=null //variable for json result from url
		synchronized(lock) {
			while(retries-->0) {
				try {
					//introducing this delay will undercut our parallel queries (but with a scaled up elasticsearch with no speed limiter we'd normally use the parallelism)
					long time=System.currentTimeMillis()
					if ((time-lastQuery)<millis) {
						sleep(millis-(time-lastQuery))
						//println("sleeping "+(millis-(time-lastQuery)))
					}
					lastQuery=time //reset last queried time
					result=new URL(url).text //fetch the json back from the url
					break //success
				} catch (FileNotFoundException e) {
					//this means no data
					break //no data
				} catch (IOException e) {
					//this might be a 429 for querying too fast
					log.warn(e.toString())
					//println(e.toString()) //for visibility while testing
				}
			}
		}
		return(result)
	}

}
