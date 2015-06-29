package com.sra.searchfda.service

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(OpenFDAService)
class OpenFDAServiceSpec extends Specification {

    @Unroll("testing params '#params' and expecting result '#expectedResult'")
    void "test encode query params"() {
        when:

        String result = service.encodeQueryParams(params)
        then:

        result == expectedResult

        where:
        params                  | expectedResult
        null                    | ""
        [:]                     | ""
        ["": ""]                | "?="
        [a: 'b']                | "?a=b"
        [a: "", b: "b"]         | "?a=&b=b"
        [a: 'b', b: 2, c: '5&'] | "?a=b&b=2&c=5%26"
    }
}
