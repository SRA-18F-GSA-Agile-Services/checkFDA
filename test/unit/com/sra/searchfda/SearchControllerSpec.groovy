package com.sra.searchfda

import com.sra.searchfda.service.QueryService
import grails.test.mixin.TestFor
import spock.lang.Unroll
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(SearchController)
@Unroll
class SearchControllerSpec extends Specification {

    def queryService = Mock(QueryService)

    def setup() {
        controller.queryService = queryService
    }

    def "test results with no search query"() {
        when:

        def result = controller.results(searchQuery, lat,lng)

        then:
        expectedResult == result.query

        where:
        searchQuery | lat  | lng  | expectedResult
        null        | null | null | null
        ""          | null | null | ""

    }

    def "test results with search query"() {
        given:

        when:

        def result = controller.results(searchQuery, lat , lng)

        then:
        1 * queryService.saveSearchQuery(searchQuery, lat, lng) >> new Query(search: searchQuery, lat: lat, lng: lng)

        expectedResult == result.query

        where:
        searchQuery | lat  | lng  | expectedResult
        "ice cream" | null | null | "ice cream"
    }
}
