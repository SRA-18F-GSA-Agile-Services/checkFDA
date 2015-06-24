package com.sra.searchfda

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes = 'uuid')
@ToString(includeNames = true, includes = 'search')
class Query {

	String search
	Long lat
	Long lng

	static final int SEARCH_MAX_SIZE = 255

	String uuid = UUID.randomUUID()

	static constraints = {
		search nullable: false, maxSize: SEARCH_MAX_SIZE
		lat nullable: true
		lng nullable: true
	}
}
