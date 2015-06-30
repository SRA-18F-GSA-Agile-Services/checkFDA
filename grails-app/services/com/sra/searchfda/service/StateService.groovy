package com.sra.searchfda.service

import grails.converters.JSON
import grails.transaction.Transactional
import org.codehaus.groovy.grails.commons.GrailsApplication

@Transactional
class StateService {
	
   Map states=null
   Map territories=null
   GrailsApplication grailsApplication
   List<String> allPhrases=["nationwide"]
	
   private void loadStates() {
	   states=JSON.parse(grailsApplication.parentContext.getResource("data/states.json").file.text)
	   territories=JSON.parse(grailsApplication.parentContext.getResource("data/territories.json").file.text)
   }

   List getStates(String str) {
	   if (states==null) {
		   loadStates()
	   }
	   Set<String> hits=[]
	   for(String all:allPhrases) {
		   if (str.contains(all)) {
			   states.each {k,v -> //add all the states
				   hits.add(k)
				   return(hits.sort().asType(List)) //return immediately
			   }
		   }
	   }
	   states.each {k,v ->
	     if (str.contains(k)) {
			 hits.add(k)
		 }
	     if (str.contains(v)) {
			 hits.add(k)
		 }
	   }
	   return(hits.sort().asType(List))
   }

}
