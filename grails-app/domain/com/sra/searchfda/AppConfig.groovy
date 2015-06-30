package com.sra.searchfda

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes = 'key')
@ToString(includeNames = true)
class AppConfig {

    AppConfigKey key
    String value

    static constraints = {
        key nullable: false, unique: true
        value value: false, maxSize: 255
    }
}
