package com.sra.searchfda.controller

import com.sra.searchfda.domain.DataSet
import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN"])
class DataSetController {
    def scaffold = DataSet
}
