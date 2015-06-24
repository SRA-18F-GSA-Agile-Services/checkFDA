package com.sra.searchfda.service

import grails.transaction.Transactional

@Transactional
class SearchPreprocessingService {

    def preprocessQuery(String query) {
	  for(List rule:rules) {
		  println("rule="+rule)
		  def matcher=(query =~ rule[0])
		  if (matcher.size()>0) {
			  println("matched with "+matcher[0])
			  return(rule[1].call(*matcher[0].tail()))
		  }
	  }
	  return(query)
    }
	
	def timeWords = /(after|before|since|prior to)/
	def days = /(monday|tuesday|wednesday|thursday|friday|saturday|sunday)/
	def timeexpr = /(.+) $timeWords $days/
	
	def rules=[
		[timeexpr,{query,timeword,day->"$query [prep=$timeword day=$day]"}]
    ]
}
