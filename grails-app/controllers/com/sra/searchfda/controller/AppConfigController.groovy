package com.sra.searchfda.controller

import com.sra.searchfda.AppConfig
import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN"])
class AppConfigController {
    def scaffold = AppConfig
}
