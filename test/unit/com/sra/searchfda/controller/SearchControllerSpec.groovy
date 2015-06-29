package com.sra.searchfda.controller

import com.sra.searchfda.domain.Query
import com.sra.searchfda.service.QueryService
import com.sra.searchfda.service.SearchService
import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(SearchController)
@Mock(Query)
@Unroll
class SearchControllerSpec extends Specification {

    def searchService = Mock(SearchService)
    def queryService = Mock(QueryService)

    def setup() {
        controller.searchService = searchService
        controller.queryService = queryService
    }

    def "test results with no search query"() {
        when:

        def result = controller.results(searchQuery, lat, lng)

        then:
        expectedQueryResult == result.query

        where:
        searchQuery | lat  | lng  | expectedQueryResult
        null        | null | null | ""
        ""          | null | null | ""

    }

    def "test results with search query"() {
        given:
        def expectedSearchResults = [events: [], recalls: [], labels: []]

        when:
        def result = controller.results(searchQuery, lat, lng)

        then:
        1 * searchService.executeSearch(truncatedSearchQuery) >> expectedSearchResults
        1 * queryService.saveSearchQuery(_ as Query)

        truncatedSearchQuery == result.query
        expectedSearchResults == result.results
        expectedFlash == flash.message

        where:
        searchQuery | lat  | lng  | truncatedSearchQuery | expectedFlash
        "ice cream" | null | null | "ice cream"          |  null
        "x" * 256   | null | null | "x" * 255            |  'query.max.length.exceeded'
    }

	def "test render card with recall"() {
		given:
		Map recalls = JSON.parse(getClass().getResourceAsStream("../service/OpenFDA-food-enforcement.json").text)
		String recall = recalls.results[0] as JSON

		when:
		def result = controller.renderCard(recall, 'recalls')

		then:
		controller.response.text.size() != 0
		controller.response.text.contains('div')
	}

	def "test render card with label"() {
		given:
		Map labels = JSON.parse(getClass().getResourceAsStream("../service/OpenFDA-drug-label.json").text)
		String label = labels.results[0] as JSON

		when:
		def result = controller.renderCard(label, 'labels')

		then:
		controller.response.text.size() != 0
		controller.response.text.contains('div')
	}
}
