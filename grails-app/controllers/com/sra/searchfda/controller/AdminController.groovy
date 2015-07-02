package com.sra.searchfda.controller

import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN"])
class AdminController {

    def index() {}
}
