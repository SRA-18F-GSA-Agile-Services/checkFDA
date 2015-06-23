package com.sra.searchfda

import com.sra.searchfda.service.QueryService

class SearchController {

	//static allowedMethods = [results: 'POST']

	QueryService queryService

	def results(String q, Long lat, Long lng ) {
		
		if (q) {
			Query searchQuery = queryService.saveSearchQuery(q, lat, lng)

			if (searchQuery.hasErrors()) {
				log.error("Search query '${searchQuery}' failed to save to db")
			}
		}

		[query: q]
	}
}
