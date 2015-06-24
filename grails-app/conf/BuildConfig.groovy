grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

// uncomment (and adjust settings) to fork the JVM to isolate classpaths
//grails.project.fork = [
//   run: [maxMemory:1024, minMemory:64, debug:false, maxPerm:256]
//]

grails.project.dependency.resolver = "maven"

def gebVersion = "0.10.0"
def seleniumVersion = "2.45.0"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    //legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherit false // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        grailsCentral()

        mavenLocal()
        mavenCentral()

		mavenRepo "http://repo.spring.io/milestone/"
		mavenRepo "http://download.java.net/maven/2/"
		mavenRepo "http://repo.grails.org/grails/core"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.
        runtime 'mysql:mysql-connector-java:5.1.22'

		compile 'org.codehaus.gpars:gpars:1.1.0'

        test("org.seleniumhq.selenium:selenium-firefox-driver:$seleniumVersion") {
            exclude "commons-logging"
        }

        test("org.seleniumhq.selenium:selenium-htmlunit-driver:$seleniumVersion") {
            exclude "commons-logging"
        }
        test "org.seleniumhq.selenium:selenium-support:$seleniumVersion"

        test "org.gebish:geb-junit4:$gebVersion"
    }

    plugins {
        compile ":hibernate:3.6.10.17"
        compile ":asset-pipeline:2.3.2"
		compile ":quartz:1.0.1"
		compile ":scaffolding:2.1.0"
		compile ":searchable:0.6.7"
		compile ":executor:0.3"
		compile ":markdown:1.1.1"

        build ':tomcat:7.0.54'
		compile ":spring-security-ui:1.0-RC2"
		compile ":spring-security-core:2.0-RC4"
		compile ":jquery-ui:1.10.4"

		test ":codenarc:0.22"
        test ":code-coverage:2.0.3-3"
        test ":geb:$gebVersion"
    }
}
codenarc.reports = {
    // uncomment this section if you want to generate xml report  
	MyXmlReport('xml') {                    // The report name "MyXmlReport" is user-defined; Report type is 'xml'
		outputFile = 'target/CodeNarcReport.xml'  // Set the 'outputFile' property of the (XML) Report
		title = 'Search FDA XML Report'             // Set the 'title' property of the (XML) Report
	}
	
	
	MyHtmlReport('html') {                  // Report type is 'html'
		outputFile = 'target/CodeNarcReport.html'
		title = 'Search FDA html Report'
	}
}
codenarc.ruleSetFiles="file:test/CodeNarcRules.groovy"
codenarc.processViews = true

//controls what happens when the maximum number of violations are exceeded
codenarc.systemExitOnBuildException = false

coverage {
    enabledByDefault = false
}