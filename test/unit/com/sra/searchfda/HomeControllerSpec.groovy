package com.sra.searchfda

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(HomeController)
class HomeControllerSpec extends Specification {

    void "test change password"() {
        when:
        controller.changePassword()

        then:
        view == '/home/chpass'
    }
}
