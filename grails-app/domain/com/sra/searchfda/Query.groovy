package com.sra.searchfda

class Query {

	String search
	long lat
	long lng

	static constraints = {
		search blank: false
		lat nullable: true
		lng nullable: true
	}
}
