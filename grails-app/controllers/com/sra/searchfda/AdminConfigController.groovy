package com.sra.searchfda

import com.sra.searchfda.service.AdminConfigService
import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN"])
class AdminConfigController {

    AdminConfigService adminConfigService

    static allowedMethods = [save: "POST"]

    @SuppressWarnings("EmptyMethod")
    def index() {}

    def manage() {
        return [
                adminConfigPropertyInstance: new AdminConfigProperty(),
                adminConfigPropertyKeys    : AdminConfigPropertyKey.values()
        ]
    }

    def save(AdminConfigProperty adminConfigProperty) {
        if(!adminConfigProperty.validate()){
            render(view: "manage", model: [
                    applicationPropertyInstance: adminConfigProperty,
                    adminConfigPropertyKeys: AdminConfigPropertyKey.values()],
                    beanWithErrors: adminConfigProperty
            )

        }

        if(adminConfigService.addOrUpdateAdminConfigPropertyValue(adminConfigProperty)){
            flash.message = message(code: 'adminConfigProperty.save.successful', args: [adminConfigProperty.key, adminConfigProperty.value])
        }

        render(view: "manage", model: [applicationPropertyInstance: adminConfigProperty, adminConfigPropertyKeys: AdminConfigPropertyKey.values()])
    }

}
