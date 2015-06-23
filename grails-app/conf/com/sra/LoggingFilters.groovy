package com.sra

import org.codehaus.groovy.grails.web.metaclass.WithFormMethod
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.security.core.context.SecurityContextHolder

class LoggingFilters {

    def filters = {
        all(controller:'*', action:'*') {
            before = {
				log.info 'User: ' + SecurityContextHolder.getContext().getAuthentication().getName() + ', Controller: ' + controllerName + ', Action: ' + actionName
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
        invalidToken(controller: '*', action: '*') {
            before = {
                log.info("SiteFilter.invalidToken Before filter fired for: $controllerName/$actionName")
                if (request.post) {
                    WithFormMethod method = new WithFormMethod()
                    GrailsWebRequest grailsWebRequest = GrailsWebRequest.lookup(request)

                    method.withForm(grailsWebRequest) {

                    }.invalidToken {
                        flash.error = applicationContext.getMessage('token.invalid', [] as Object[], LocaleContextHolder.locale)
                        redirect(controller: 'home', action: 'index')
                        return false
                    }
                }
            }
        }
    }
}
