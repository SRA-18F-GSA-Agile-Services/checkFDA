package com.sra.searchfda.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes = 'name')
@ToString(includeNames = true)
class DataSet {

    String name
    String url
    String description
    String groupName
    String path     // We should deprecate path.

    static constraints = {
        name nullable: false, unique: true, maxSize: 255
        url nullable: false, maxSize: 255
        description nullable: false, maxSize: 255
        groupName nullable: false, maxSize: 255
        path nullable: false, maxSize: 255
    }
}
