import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver

driver = {
    // The Firefox Driver is on by default
    def driverInstance = new FirefoxDriver()
    driverInstance.manage().window().maximize()
    return driverInstance
}

environments {

    // run as grails -Dgeb.env=htmlunit test-app functional:
    // Note that HtmlUnit's javascript functionality does not work well so this is not on by default.
    htmlunit {
        def driverInstance = new HtmlUnitDriver()

        driverInstance.javascriptEnabled = true
        driverInstance.manage().window().maximize()

        return driverInstance
    }
}