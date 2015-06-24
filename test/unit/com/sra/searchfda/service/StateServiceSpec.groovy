package com.sra.searchfda.service

import grails.converters.JSON
import grails.test.mixin.*
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(StateService)
class StateServiceSpec extends Specification {
	
	def "test getStateList"() {
		given:
		//we want to test with the same file used in production, so some work here
		String s=getClass().getResource(".").toString() //find out where we are in testing mode
		int pos=s.indexOf("SearchFDA/")+10 //find the head of the string up to the project
		String target=s.substring(0,pos)+"web-app/data/states.json" //get absolute path to the states file
		target=target.substring(6) //trim the file: url head
		String jsonstr=new File(target).text //just get the contents of the same file used in production
		service.states=JSON.parse(jsonstr)
		
		when:
		def result=service.getStateList(str)

		then:
		result == expected 

		where:
		str															|   expected
		"the recall effects IA and Illinois"						|	["IA","IL"]
		"this is only for MI and OH"								|	["MI","OH"]
	}
}
