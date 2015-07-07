package com.sra.searchfda.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes = 'authority')
@ToString(includeNames = true)
class Role implements Serializable {

    private static final long serialVersionUID = 1

	String authority

	static mapping = {
		cache true
	}

	static constraints = {
		authority nullable: false, unique: true
	}
}
