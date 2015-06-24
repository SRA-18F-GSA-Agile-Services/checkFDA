package com.sra.searchfda.service

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class StateService {
	
   Map states=null
   Map territories=null
   def grailsApplication
	
   private void loadStates() {
	   states=JSON.parse(grailsApplication.parentContext.getResource("data/states.json").file.text)
	   territories=JSON.parse(grailsApplication.parentContext.getResource("data/territories.json").file.text)
   }
   
   List getStates(String str) {
     return(getStateList(str))
   }

   private List getStateList(String str) {
	   if (states==null) loadStates()
	   Set<String> hits=new HashSet()
	   states.each {k,v ->
	     if (str.contains(k)) hits.add(k)		
	     if (str.contains(v)) hits.add(k)	
	   }
	   return(hits.sort().asType(List))
   }

}
