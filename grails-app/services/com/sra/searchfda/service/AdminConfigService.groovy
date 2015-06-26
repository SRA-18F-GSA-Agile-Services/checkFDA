package com.sra.searchfda.service

import com.sra.searchfda.AdminConfigProperty
import com.sra.searchfda.AdminConfigPropertyKey
import grails.transaction.Transactional

@Transactional
class AdminConfigService {

    AdminConfigProperty addOrUpdateAdminConfigPropertyValue(AdminConfigProperty adminConfigProperty) {
        if (adminConfigProperty && !AdminConfigPropertyKey.values().contains(adminConfigProperty.value)) {
            throw IllegalStateException("Attempted creation of update of configuration property with invalid key $adminConfigProperty.key")
        }
        AdminConfigProperty existingConfigValueAdminConfigProperty = AdminConfigProperty.findByKey(adminConfigProperty.key)
        if (existingConfigValueAdminConfigProperty) {
            existingConfigValueAdminConfigProperty.value = adminConfigProperty.value
            return existingConfigValueAdminConfigProperty.save(failOnError: true, flush: true)
        }
        return adminConfigProperty.save(failOnError: true, flush: true)

    }

    List<AdminConfigProperty> listAdminConfigProperties() {
        return AdminConfigProperty.list()
    }
}
