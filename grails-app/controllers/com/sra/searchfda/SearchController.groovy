package com.sra.searchfda

import com.sra.searchfda.service.QueryService
import com.sra.searchfda.service.SearchService
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

class SearchController {

    //static allowedMethods = [results: 'POST']

    QueryService queryService
    SearchService searchService

    @SuppressWarnings("EmptyMethod")
    def index() {}

    def search(String query) {
        def mapResults = searchService.parallelFederatedSearch(query)
        render((mapResults as JSON).toString())
    }

    @Secured(["ROLE_ADMIN"])
    def serialSearch(String query) {
        def mapResults = searchService.federatedSearch(query)
        render((mapResults as JSON).toString())
    }

    @Secured(["ROLE_ADMIN"])
    def testFilters() {
        searchService.testFilters()
    }

    @Secured(["ROLE_ADMIN"])
    def searchTime(String query) {
        def mapResults = searchService.timingComparison(query)
        render((mapResults as JSON).toString())
    }

    def results(String q, Long lat, Long lng) {

        if (!q || !q.trim()) {
            return [query: "", results: null]
        }

        if (q.length() > Query.SEARCH_MAX_SIZE) {
            flash.message = g.message(code: "query.max.length.exceeded", args: [Query.SEARCH_MAX_SIZE])
        }

        def truncatedQuery = q.take(Query.SEARCH_MAX_SIZE)
        Query searchQuery = new Query(search: truncatedQuery, lat: lat, lng: lng)

        if (!searchQuery.validate()) {
            return [beanWithErrors: searchQuery, query: truncatedQuery, results: null]
        }

        queryService.saveSearchQuery(searchQuery)

        [query: searchQuery.search, results: searchService.executeSearch(searchQuery.search)]
    }
}
