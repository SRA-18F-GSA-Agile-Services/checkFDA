package com.sra.searchfda.service

import com.sra.searchfda.domain.Query
import grails.transaction.Transactional

@Transactional
class QueryService {

    /**
     * Save a search query to database
     * @param query
     * @return result of save
     */
    Query saveSearchQuery(Query query) {
        return query.save(failOnError: true)
    }
}
