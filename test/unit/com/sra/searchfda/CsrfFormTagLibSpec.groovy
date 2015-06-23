package com.sra.searchfda

import grails.test.mixin.TestFor
import org.codehaus.groovy.grails.web.servlet.mvc.SynchronizerTokensHolder
import spock.lang.Specification

@TestFor(CsrfFormTagLib)
class CsrfFormTagLibSpec extends Specification {

    void "test searchfda form uses tokens by default"() {
        given:

        request.forwardURI = '/home/passwordUpdated'

        UUID token = new UUID(3L, 1L)
        SynchronizerTokensHolder.metaClass.generateToken = { String url ->
            return token
        }

        def expected = '<form action="/home/updatePassword" method="post" ><input type="hidden" name="SYNCHRONIZER_TOKEN" value="' + token + '" id="SYNCHRONIZER_TOKEN" /><input type="hidden" name="SYNCHRONIZER_URI" value="/home/passwordUpdated" id="SYNCHRONIZER_URI" /></form>'

        when:
        def result = applyTemplate('<searchfda:form controller="home" action="updatePassword" method="POST"/>')


        then:
        TagLibTestHelper.cleanWhitespace(result) == expected
    }

    void "test searchfda form uses tokens when explicitly defined"() {
        given:

        request.forwardURI = '/home/passwordUpdated'

        UUID token = new UUID(3L, 1L)
        SynchronizerTokensHolder.metaClass.generateToken = { String url ->
            return token
        }

        def expected = '<form action="/home/updatePassword" method="post" ><input type="hidden" name="SYNCHRONIZER_TOKEN" value="' + token + '" id="SYNCHRONIZER_TOKEN" /><input type="hidden" name="SYNCHRONIZER_URI" value="/home/passwordUpdated" id="SYNCHRONIZER_URI" /></form>'

        when:
        def result = applyTemplate('<searchfda:form controller="home" action="updatePassword" method="POST" useToken="true"/>')


        then:
        TagLibTestHelper.cleanWhitespace(result) == expected
    }

    void "test setting tokens for searchfda form to false uses no tokens"() {
        def expected = '<form action="/home/updatePassword" method="post" >TESTING</form>'

        when:
        def result = applyTemplate('<searchfda:form controller="home" action="updatePassword" method="POST" useToken="false">TESTING</searchfda:form>')

        then:
        TagLibTestHelper.cleanWhitespace(result) == expected
    }
}
