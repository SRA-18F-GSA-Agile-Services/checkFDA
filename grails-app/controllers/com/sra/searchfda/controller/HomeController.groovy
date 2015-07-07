package com.sra.searchfda.controller

import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN","ROLE_USER"])
class HomeController {

    @SuppressWarnings("EmptyMethod")
	def index() { }
	
	def health() {
		render(status: 200)
	}
}

