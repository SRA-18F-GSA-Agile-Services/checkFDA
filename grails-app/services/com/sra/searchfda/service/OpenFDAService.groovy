package com.sra.searchfda.service

import grails.transaction.Transactional
import org.codehaus.groovy.grails.commons.GrailsApplication

@Transactional
class OpenFDAService {

    GrailsApplication grailsApplication
    final String urlBase = "https://api.fda.gov/" //baseURL for Open FDA API

    String query(String dataset, String query, int limit = 100, int skip = 0) {
        String apiToken = grailsApplication.config.openfdaapi.token //get api token from our config

        String url = urlBase + dataset + ".json?api_key=" + apiToken + "&search=" + URLEncoder.encode(query) + "&limit=" + limit + "&skip=" + skip
        //construct url
        String result = null //variable for json result from url
        try {
            result = new URL(url).text //fetch the json back from the url
        } catch (Exception e) {
			log.error 'Query failed: ' + e.getMessage()
        }
        return (result)
    }
}
