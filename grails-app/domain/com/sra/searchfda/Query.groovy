package com.sra.searchfda

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes = 'uuid')
@ToString(includeNames = true, includes = 'search')
class Query {

	String search
	Long lat
	Long lng

	String uuid = UUID.randomUUID()

	static constraints = {
		search nullable: false
		lat nullable: true
		lng nullable: true
	}
}
