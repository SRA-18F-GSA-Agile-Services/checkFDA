package com.sra.searchfda.service

import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.util.WebUtils

@Transactional
class OpenFDAService {

    def grailsApplication
    String urlBase = "https://api.fda.gov/" //baseURL for Open FDA API

    def String query(String dataset, String query, int limit = 100, int skip = 0) {

        def queryMap = [search: query, limit: limit, skip: skip]

        String apiToken = grailsApplication.config.openfdaapi.token //get api token from our config

        if (apiToken) {
            queryMap[apiToken] = apiToken
        }

        String paramsString = encodeQueryParams(queryMap)

        String url = urlBase + dataset + ".json" + paramsString

        log.debug("Attempting call to FDA API with url '${url}'")

        String result = null //variable for json result from url

        try {
            result = new URL(url).text //fetch the json back from the url
        } catch (Exception e) {
            log.error "Error fetching data from ${url}", e
        }
        return (result)
    }

    String encodeQueryParams(Map<String, Object> params) {

        if (!params) {
            return ""
        }

        return WebUtils.toQueryString(params)
    }
}
