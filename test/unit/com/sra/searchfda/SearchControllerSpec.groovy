package com.sra.searchfda

import com.sra.searchfda.service.QueryService
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(SearchController)
class SearchControllerSpec extends Specification {

    def queryService = Mock(QueryService)

    def setup() {
        controller.queryService = queryService
    }

    def "test results with no search query"() {
        given:

        when:

        def result = controller.results(searchQuery)

        then:
        expectedResult == result.query

        where:
        searchQuery | expectedResult
        null        | null
        ""          | ""

    }

    def "test results with search query"() {
        given:

        when:

        def result = controller.results(searchQuery)

        then:
        1 * queryService.saveSearchQuery(searchQuery, null, null) >> new Query(search: searchQuery, lat: null, lng: null)

        expectedResult == result.query

        where:
        searchQuery | expectedResult
        "ice cream" | "ice cream"
    }
}
