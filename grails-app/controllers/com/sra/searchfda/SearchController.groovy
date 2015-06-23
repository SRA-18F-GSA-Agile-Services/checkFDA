package com.sra.searchfda

class SearchController {

	def index() { }

	def results(String q) {
		[query: q]
	}
}
