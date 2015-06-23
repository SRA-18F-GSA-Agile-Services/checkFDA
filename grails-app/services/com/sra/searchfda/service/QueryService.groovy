package com.sra.searchfda.service

import com.sra.searchfda.Query
import grails.transaction.Transactional

@Transactional
class QueryService {

    /**
     * Save a search query, lat and lng to database
     * @param search
     * @param lat
     * @param lng
     * @return search query object
     */
    Query saveSearchQuery(String search, Long lat, Long lng) {
        Query q = new Query(search: search, lat: lat, lng: lng).save()
        return q
    }
}
