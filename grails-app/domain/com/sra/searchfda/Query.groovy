package com.sra.searchfda

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes = 'uuid')
@ToString(includeNames = true, includes = 'search')
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
