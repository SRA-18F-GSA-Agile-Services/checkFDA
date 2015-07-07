import com.sra.searchfda.domain.DataSet
import com.sra.searchfda.domain.Role
import com.sra.searchfda.domain.User
import com.sra.searchfda.domain.UserRole
import org.codehaus.groovy.grails.commons.GrailsApplication

class BootStrap {
	GrailsApplication grailsApplication

	def createUser(user,pass,role) {
		def theUser = new User(username:user,password:pass)
		theUser.save(flush:true)
		UserRole.create theUser, role, true
	}

	def init = { servletContext ->
		log.info 'Boostrapping'
		TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"))
		try {
			if (UserRole.list().size() == 0) { //only load on empty DB
				def adminRole = new Role(authority:'ROLE_ADMIN').save(flush:true)
				new Role(authority:'ROLE_USER').save(flush:true)

				// Create an administrative user that can manage the configuration.
				createUser(grailsApplication.config.checkfda.admin.default_username,
						grailsApplication.config.checkfda.admin.default_password,
						adminRole)
			}

			if (DataSet.list().size() == 0) {
				initDataSets()
			}
		} catch (Exception e) {
			log.error("Exception during bootstrap init", e)
		}
	}
	def destroy = {
	}

	void initDataSets() {
		new DataSet(name: "FDA_DRUG_EVENT_ENDPOINT", url: "https://api.fda.gov/drug/event.json", path:"drug/event", groupName:"events", description: "FDA api endpoint url for adverse drug event reports since 2004").save(flush: true, failOnError: true)
		new DataSet(name: "FDA_DRUG_LABEL_ENDPOINT", url: "https://api.fda.gov/drug/label.json", path:"drug/label", groupName:"labels", description: "FDA api endpoint url for Prescription and over-the-counter (OTC) drug labeling").save(flush: true, failOnError: true)
		new DataSet(name: "FDA_DRUG_ENFORCEMENT_ENDPOINT", url: "https://api.fda.gov/drug/enforcement.json", path:"drug/enforcement", groupName:"recalls", description: "FDA api endpoint url for Drug recall enforcement reports since 2004").save(flush: true, failOnError: true)

		new DataSet(name: "FDA_DEVICE_ENFORCEMENT_ENDPOINT", url: "https://api.fda.gov/device/enforcement.json", path:"device/enforcement", groupName:"recalls", description: "FDA api endpoint url for Device recall enforcement reports since 2004").save(flush: true, failOnError: true)
		new DataSet(name: "FDA_DEVICE_EVENT_ENDPOINT", url: "https://api.fda.gov/device/event.json", path:"device/event", groupName:"events", description: "FDA api endpoint url for Device adverse event reports over time").save(flush: true, failOnError: true)

		new DataSet(name: "FOOD_ENFORCEMENT_ENDPOINT", url: "https://api.fda.gov/food/enforcement.json", path:"food/enforcement", groupName:"recalls", description: "FDA api endpoint url for Food recall enforcement reports since 2004").save(flush: true, failOnError: true)

	}
}
