package com.sra.searchfda.service

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(SearchService)
class SearchServiceSpec extends Specification {

    def openFDAService = Mock(OpenFDAService)

    def setup() {
        service.openFDAService = openFDAService
    }

	void "test execute search local"() {
		given:
		Map result = [result: "My Test Value"]
		grailsApplication.config.checkfda.localData = true
		service.grailsApplication = grailsApplication
		service.metaClass.federatedSearchMock = { -> return result }

		when:
		Map searchResults = service.executeSearch("Test")

		then:
		searchResults == result
	}

	void "test execute search remote"() {
		given:
		Map result = [result: "My Test Value"]
		grailsApplication.config.checkfda.localData = false
		service.grailsApplication = grailsApplication
		service.metaClass.parallelFederatedSearch = { String query -> return result }

		when:
		Map searchResults = service.executeSearch("Test")

		then:
		searchResults == result
	}

    void "test parallel federated search"() {
        given:
        String query = "ice cream"

        when:
        Map searchResults = service.parallelFederatedSearch(query)

        then:
        1 * openFDAService.query("device/event", query, 100, 0) >> getClass().getResourceAsStream("OpenFDA-device-event.json").text
        1 * openFDAService.query("drug/label", query, 100, 0) >> getClass().getResourceAsStream("OpenFDA-drug-label.json").text
        1 * openFDAService.query("food/enforcement", query, 100, 0) >> getClass().getResourceAsStream("OpenFDA-food-enforcement.json").text
        1 * openFDAService.query("drug/event", query, 100, 0) >> null
        1 * openFDAService.query("device/enforcement", query, 100, 0) >> null
        1 * openFDAService.query("drug/enforcement", query, 100, 0) >> null

        searchResults.containsKey("labels")
        searchResults.containsKey("recalls")
        searchResults.containsKey("events")

        searchResults.labels
        searchResults.recalls
        searchResults.events
    }

    void "test serial federated search"() {
        given:
        String query = "ice cream"

        when:
        Map searchResults = service.federatedSearch(query)

        then:
        1 * openFDAService.query("device/event", query, 100, 0) >> getClass().getResourceAsStream("OpenFDA-device-event.json").text
        1 * openFDAService.query("drug/label", query, 100, 0) >> getClass().getResourceAsStream("OpenFDA-drug-label.json").text
        1 * openFDAService.query("food/enforcement", query, 100, 0) >> getClass().getResourceAsStream("OpenFDA-food-enforcement.json").text
        1 * openFDAService.query("drug/event", query, 100, 0) >> null
        1 * openFDAService.query("device/enforcement", query, 100, 0) >> null
        1 * openFDAService.query("drug/enforcement", query, 100, 0) >> null

        searchResults.containsKey("labels")
        searchResults.containsKey("recalls")
        searchResults.containsKey("events")

        searchResults.labels
        searchResults.recalls
        searchResults.events
    }

    void "test search"() {
        given:
        String query = "ice cream"

        when:
        String searchResults = service.search(dataSet, query)


        then:
        1 * openFDAService.query(dataSet, query, 100, 0) >> getClass().getResourceAsStream(fileName).text
        searchResults


        where:
        fileName                        | dataSet
        "OpenFDA-device-event.json"     | "device/event"
        "OpenFDA-drug-label.json"       | "drug/label"
        "OpenFDA-food-enforcement.json" | "food/enforcement"
    }

    void "test search exception thrown"() {
        given:
        String query = "ice cream"

        when:
        List<Map> searchResults = service.search("device/event", query)

        then:
        1 * openFDAService.query("device/event", query, 100, 0) >> { throw new FileNotFoundException() }
        thrown(FileNotFoundException)
        !searchResults
    }
}
