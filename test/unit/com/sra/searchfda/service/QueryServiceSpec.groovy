package com.sra.searchfda.service

import com.sra.searchfda.Query
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(QueryService)
@Mock(Query)
class QueryServiceSpec extends Specification {

    def "test save search query"() {
        given:
        Query query = new Query(search: "ice cream", lat: 34.5, lng: -12.6)

        when:
        def result = service.saveSearchQuery(query)

        then:
        result == query
        result.id
    }
}
