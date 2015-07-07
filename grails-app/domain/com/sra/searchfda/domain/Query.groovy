package com.sra.searchfda.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes = 'uuid')
@ToString(includeNames = true, includes = 'search')
class Query {

	String search
	Double lat
	Double lng

	static final int SEARCH_MAX_SIZE = 255

	String uuid = UUID.randomUUID()

	static constraints = {
		search nullable: false, maxSize: SEARCH_MAX_SIZE
		lat nullable: true
		lng nullable: true
	}
}
