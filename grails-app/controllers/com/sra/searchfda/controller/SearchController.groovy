package com.sra.searchfda.controller

import com.sra.searchfda.domain.Query
import com.sra.searchfda.service.QueryService
import com.sra.searchfda.service.SearchService
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.geocode.GeocodingService
import grails.plugin.geocode.Point

class SearchController {

    //static allowedMethods = [results: 'POST']

    QueryService queryService
    SearchService searchService
	GeocodingService geocodingService

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

    def results(String q, String lat, String lng) {
		Double dlat = null
		Double dlng = null
		String state = ""
			
        if (!q || !q.trim()) {
            return [query: "", results: null]
        }
		
		if( lat != null && lng != null){
			try {
				dlat = lat.toDouble() 
				dlng = lng.toDouble()
				state = reverseGeocode (dlat,  dlng)
			} catch (NumberFormatException ex) {
				log.debug "Search convert lat/lng coordinates to double:" + ex
				state = null
			}
		}
		
        if (q.length() > Query.SEARCH_MAX_SIZE) {
            flash.message = g.message(code: "query.max.length.exceeded", args: [Query.SEARCH_MAX_SIZE])
        }

        def truncatedQuery = q.take(Query.SEARCH_MAX_SIZE)
        Query searchQuery = new Query(search: truncatedQuery, lat: dlat, lng: dlng)

        if (!searchQuery.validate()) {
            return [beanWithErrors: searchQuery, query: truncatedQuery, results: null]
        }

        queryService.saveSearchQuery(searchQuery)
        [query: searchQuery.search, results: searchService.executeSearch(searchQuery.search) << [state:[name:state, latitude : dlat , longitude: dlng]] ]
    }

	def renderCard(String json, String type, String query) {
		Map element = JSON.parse(json)
		if (type == 'recalls') {
			render(template: '/layouts/cards/recall-alert', model: [recall: element, query: query])
		} else if (type == 'labels') {
			render(template: '/layouts/cards/drug-label', model: [label: element, query: query])
		} else if (type == 'drugevents') {
			render(template: '/layouts/cards/drug-event', model: [event: element, query: query])
		} else if (type == 'deviceevents') {
			render(template: '/layouts/cards/device-event', model: [event: element, query: query])
		}
	}
	/**
	*  call locationgGeocoding service to convert latitude longitude coordinates to an address
	* @param params
	* @return
	*/
	String reverseGeocode (Double lat, Double lng){
		String state = null
		Point location = new Point(latitude: lat, longitude: lng) 
		List results = geocodingService.getAddresses(location)
		if(results != null && results.size() >0 ){
			results[0].addressComponents.each {
				if(it.types[0]=='administrative_area_level_1'){			
					state = it.shortName
				}
			}
		}
		return state
	}
}
