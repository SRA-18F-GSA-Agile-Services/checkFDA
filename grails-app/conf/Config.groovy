// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.config.locations = ['file:../UserConfig.groovy', 'file:webapps/ROOT/Jenkins.groovy', 'file:conf/ServerConfig.groovy']

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
					  all:           '*/*', // 'all' maps to '*' or the first available format in withFormat
					  atom:          'application/atom+xml',
					  css:           'text/css',
					  csv:           'text/csv',
					  form:          'application/x-www-form-urlencoded',
					  html:          ['text/html','application/xhtml+xml'],
					  js:            'text/javascript',
					  json:          ['application/json', 'text/json'],
					  multipartForm: 'multipart/form-data',
					  rss:           'application/rss+xml',
					  text:          'text/plain',
					  hal:           ['application/hal+json','application/hal+xml'],
					  xml:           ['text/xml', 'application/xml']
]


// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// GSP settings
grails {
	views {
		gsp {
			encoding = 'UTF-8'
			htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
			codecs {
				expression = 'html' // escapes values inside ${}
				scriptlet = 'html' // escapes output from scriptlets in GSPs
				taglib = 'none' // escapes output from taglibs
				staticparts = 'none' // escapes output from static template parts
			}
		}
		// escapes all not-encoded output at final stage of outputting
		filteringCodecForContentType.'text/html' = 'html'
	}
}

grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

checkfda.admin.default_username = 'admin'
checkfda.admin.default_password = 'stbadmin2014'

environments {
    development {
		checkfda.localData = true
        grails.logging.jul.usebridge = true
		grails.serverURL = 'http://localhost:8080'
    }
    production {
		checkfda.localData = false
        grails.logging.jul.usebridge = false
		grails.serverURL = 'https://checkfda.srarad.com'
    }
}

log4j = {
	appenders {
		console name: 'stdout', threshold: org.apache.log4j.Level.ERROR
		rollingFile name: 'info', file: 'logs/info.log', layout: pattern(conversionPattern: '[%p] %d{yyyy-MM-dd HH:mm:ss} %c{2} - %m%n'), threshold: org.apache.log4j.Level.INFO
		rollingFile name: 'warn', file: 'logs/warn.log', layout: pattern(conversionPattern: '[%p] %d{yyyy-MM-dd HH:mm:ss} %c{2} - %m%n'), threshold: org.apache.log4j.Level.WARN
		rollingFile name: 'activity', file: 'logs/activity.log', layout:pattern(conversionPattern: '%d{dd-MM-yyyy HH:mm:ss} - %m%n'), threshold: org.apache.log4j.Level.INFO
	}

	warn 'warn': [
		'grails.app.controllers.com.sra.searchfda',
		'grails.app.services.com.sra.searchfda',
		'grails.app.conf.com.sra.searchfda',
		'grails.app.domain.com.sra.searchfda'
	]

	info 'info': [
		'grails.app.controllers.com.sra.searchfda',
		'grails.app.services.com.sra.searchfda',
		'grails.app.conf.com.sra.searchfda',
		'grails.app.domain.com.sra.searchfda'
	]

	info 'activity':  ['grails.app.filters.LoggingFilters']
	off 'org.grails.plugin.resource.ResourceMeta'
}

grails.app.context="/"

grails.plugin.springsecurity.providerNames = [
	'daoAuthenticationProvider',
	'anonymousAuthenticationProvider',
	'rememberMeAuthenticationProvider'
]

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.logout.postOnly = false
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.sra.searchfda.domain.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.sra.searchfda.domain.UserRole'
grails.plugin.springsecurity.authority.className = 'com.sra.searchfda.domain.Role'

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	'/**':								['permitAll'],
	'/searchable/**':					['ROLE_ADMIN'],
	'/query/**':						['ROLE_ADMIN'],
	'/user/**':							['ROLE_ADMIN'],
	'/role/**':							['ROLE_ADMIN'],
	'/registrationCode/**':				['ROLE_ADMIN'],
	'/securityInfo/**':					['ROLE_ADMIN'],
	'/monitoring/**':					['ROLE_ADMIN']
]
