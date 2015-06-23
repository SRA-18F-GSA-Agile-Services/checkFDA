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

        String search = "ice cream"
        long lat = 34.5
        long lng = -12.6

        when:
        def query = service.saveSearchQuery(search, lat, lng)

        then:

        query
        query.search == search
        query.lat == lat
        query.lng == lng
    }
}
