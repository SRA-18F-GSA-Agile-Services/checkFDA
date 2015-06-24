package com.sra.searchfda

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import com.sra.searchfda.service.QueryService
import com.sra.searchfda.service.SearchPreprocessingService
import com.sra.searchfda.service.SearchService

class SearchController {

	//static allowedMethods = [results: 'POST']

	QueryService queryService
	SearchService searchService
	SearchPreprocessingService searchPreprocessingService

	@SuppressWarnings("EmptyMethod")
	def index() { }

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
	
	def testPreproc(String query) {
		render(searchPreprocessingService.preprocessQuery(query))
	}

	def results(String q, Long lat, Long lng ) {
		
		if (q) {
			Query searchQuery = queryService.saveSearchQuery(q, lat, lng)

			if (searchQuery.hasErrors()) {
				log.error("Search query '${searchQuery}' failed to save to db")
			}
		}

		[query: q, results: searchService.executeSearch(q)]
	}
}
