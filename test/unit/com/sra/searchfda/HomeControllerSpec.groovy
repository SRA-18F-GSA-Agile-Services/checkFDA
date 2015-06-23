package com.sra.searchfda

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(HomeController)
class HomeControllerSpec extends Specification {

    def "test health page"() {
        when:
        controller.health()

        then:
        response.status == 200
    }
}
