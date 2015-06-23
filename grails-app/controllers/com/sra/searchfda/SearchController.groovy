package com.sra.searchfda

import grails.converters.JSON

class SearchController {

	def SearchService

	def search(String query) {
		def mapResults = SearchService.federatedSearch(query)
		render((mapResults as JSON).toString())
	}

	def results(String q) {
		[query: q]
	}
}
