package com.sra.searchfda

import grails.test.mixin.TestFor
import spock.lang.Specification
import com.sra.searchfda.service.SearchService

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(SearchController)
class SearchControllerSpec extends Specification {

    def searchService = Mock(SearchService)

    def setup() {
        controller.searchService = searchService
    }

    def "test results"() {
        when:
        def result = controller.results(query)

        then:
        1 * searchService.federatedSearchMock() >> [events: [], recalls: [], labels: []]
        expectedResult == result.query

        where:
        query       | expectedResult
        null        | null
        "ice cream" | "ice cream"
    }
}
