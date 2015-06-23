package com.sra.searchfda

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(SearchController)
class SearchControllerSpec extends Specification {

    def "test results"() {
        when:
        def result = controller.results(query)

        then:
        expectedResult == result.query

        where:
        query       | expectedResult
        null        | null
        "ice cream" | "ice cream"
    }
}
