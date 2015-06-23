package com.sra.searchfda

import com.sra.searchfda.service.QueryService

class SearchController {

	//static allowedMethods = [results: 'POST']

	QueryService queryService

	def results(String q) {

		if (q) {
			Query searchQuery = queryService.saveSearchQuery(q, null, null)

			if (searchQuery.hasErrors()) {
				log.error("Search query '${searchQuery}' failed to save to db")
			}
		}

		[query: q]
	}
}
