package com.sra.searchfda

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes = 'key')
@ToString(includeNames = true, includes = 'search')
class AdminConfigProperty {

    AdminConfigPropertyKey key
    String value

    static constraints = {
        key nullable: false, unique: true
        key value: false, maxSize: 255, unique: true
    }
}
