package com.sra.searchfda

import grails.converters.JSON

class SearchController {

	def searchService
	
	def index() { }

	def search(String query) {
		def mapResults = searchService.parallelFederatedSearch(query)
		render((mapResults as JSON).toString())
	}
	
	def serialSearch(String query) {
		def mapResults = searchService.federatedSearch(query)
		render((mapResults as JSON).toString())
	}

	def searchTime(String query) {
		def mapResults = searchService.timingComparison(query)
		render((mapResults as JSON).toString())
	}

	def results(String q) {
		[query: q]
	}
}
