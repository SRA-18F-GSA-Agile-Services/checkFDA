package com.sra.searchfda.service

import grails.transaction.Transactional

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.web.util.WebUtils


@Transactional
class OpenFDAService {
	
	GrailsApplication grailsApplication
	final String urlBase="https://api.fda.gov/" //baseURL for Open FDA API
	static long lastQuery=0 //when did our last query take place
	final long millis=1000*60/240 //how long we have to wait between queries
	Object lock=new Object() //force the threads to move through in single file

	def String 	query(String dataset,String query,int limit=100,int skip=0) {
		def queryMap = [search: query, limit: limit, skip: skip]

		String apiToken = grailsApplication.config.openfdaapi.token //get api token from our config

		if (apiToken) {
			queryMap['api_key'] = apiToken
		}

		String paramsString = encodeQueryParams(queryMap)

		String url = urlBase + dataset + ".json" + paramsString

		log.debug("Attempting call to FDA API with url '${url}'")

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
					log.debug "No data while querying FDA Server: ", e
					break //no data
				} catch (IOException e) {
					//this might be a 429 for querying too fast
					log.warn "IO Exception while querying FDA Server: ", e
					//println(e.toString()) //for visibility while testing
				}
			}
		}
		return(result)
	}

    String encodeQueryParams(Map<String, Object> params) {

        if (!params) {
            return ""
        }

        return WebUtils.toQueryString(params)
    }
}
