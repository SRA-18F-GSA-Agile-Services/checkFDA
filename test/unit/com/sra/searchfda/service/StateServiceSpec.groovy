package com.sra.searchfda.service

import grails.converters.JSON
import grails.test.mixin.*
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(StateService)
class StateServiceSpec extends Specification {
	
	def "test getStates"() {
		given:
		//we want to test with the same file used in production, so some work here
		String s=getClass().getResource(".").toString() //find out where we are in testing mode
		int pos=s.indexOf("target/") //find the head of the string up to the project
		String target=s.substring(0,pos)+"web-app/data/states.json" //get absolute path to the states file
		String jsonstr=new URL(target).text
		service.states=JSON.parse(jsonstr)
		
		when:
		def result=service.getStates(str)

		then:
		result == expected 

		where:
		str															|   expected
		"the recall effects IA and Illinois"						|	["IA","IL"]
		"this is only for MI and OH"								|	["MI","OH"]
		"this recall is for all 50 states"							|	[]
		"the recall is for Iowa, Virginia, and New York"			|	["IA","NY","VA"]
	}
}
