package com.sra.searchfda

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

class SearchController {

	def searchService
	
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
	def searchTime(String query) {
		def mapResults = searchService.timingComparison(query)
		render((mapResults as JSON).toString())
	}

	def results(String q) {
		[query: q, results: SearchService.federatedSearchMock()]
	}
}
