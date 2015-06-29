package com.sra.searchfda.service

import grails.transaction.Transactional

import java.text.SimpleDateFormat

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
	
	Map numberWords=[one:1,two:2,three:3,four:4,five:5,six:6,seven:7,eight:8,nine:9,ten:10,eleven:11,twelve:12]
	
	def dateFormat=new SimpleDateFormat("yyyyMMdd")
	def timeWords = /(after|before|since|prior to|since last)/
	def days = /(monday|tuesday|wednesday|thursday|friday|saturday|sunday)/
	def timeexpr = /(.+) $timeWords $days/
	def digit = /([0-9]|one|two|three|four|five|six|seven|eight|nine|ten|eleven|twelve)/
	def units = /(months|days|years)/
	def since = /(.+) in the last $digit $units/
	
	def now() {
		return(dateFormat.format(new Date()))
	}
	
	def Integer parseNumber(String num) {
		Integer result=-1
		try {
			result=Integer.parseInt(num)	
		} catch (Exception e) {}
		if (result>-1) return(result)
		if (numberWords[num]!=null) return(numberWords[num])
		//TBD: use other strategies later
		return(null)	
	}
	
	def String since(String number,String units) {
		int num=parseNumber(number)
		Calendar cal=Calendar.getInstance()
		println("starting from "+dateFormat.format(cal.getTime()))
		switch(units) {
			case "days":
				cal.add(Calendar.DATE,-1*num)
				break;
			case "months":
				cal.add(Calendar.MONTH,-1*num)
				break;
			case "years":
				cal.add(Calendar.YEAR,-1*num)
				break;
			default:
				cal=null
		}
		if (cal==null) return(null)
		println("getting "+dateFormat.format(cal.getTime()))
		return("%date:>"+dateFormat.format(cal.getTime()))
	}
	
	def rules=[
		[timeexpr,{query,timeword,day->"$query [prep=$timeword day=$day]"}],
		[since,{query,digit,unit->"$query [digit=$digit unit=$unit, since=${since(digit,unit)}]"}]
    ]
}
