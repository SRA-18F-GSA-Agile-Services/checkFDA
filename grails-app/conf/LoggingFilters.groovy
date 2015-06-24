import org.codehaus.groovy.grails.web.metaclass.WithFormMethod
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.security.core.context.SecurityContextHolder

class LoggingFilters implements Serializable {

    def filters = {
        all(controller:'*', action:'*') {
            before = {
				log.info 'User: ' + SecurityContextHolder.getContext().getAuthentication().getName() + ', Controller: ' + controllerName + ', Action: ' + actionName
            }
        }
        checkCsrfToken(controller:'*', action:'*') {
            before = {
                if (request.post) {
                    log.debug "Checking form on '${controllerName}/${actionName}' for invalid token"
                    WithFormMethod method = new WithFormMethod()
                    GrailsWebRequest grailsWebRequest = GrailsWebRequest.lookup(request)

                    method.withForm(grailsWebRequest) {

                    }.invalidToken {
                        // TODO: The search index page does not yet handle flash error, but will.
                        flash.error = applicationContext.getMessage('token.invalid', [] as Object[], LocaleContextHolder.locale)
                        redirect(controller: 'search', action: 'index')
                        return false
                    }
                }
            }
        }
    }
}
