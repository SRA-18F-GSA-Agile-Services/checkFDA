package com.sra.searchfda.service

import grails.test.mixin.TestFor
import org.codehaus.groovy.grails.web.json.JSONArray
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(SearchService)
class SearchServiceSpec extends Specification {

    def openFDAService = Mock(OpenFDAService)
	def stateService = Mock(StateService)

    def setup() {
        service.openFDAService = openFDAService
		service.stateService = stateService
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
        String parsedQuery = "ice AND cream"
        String filterText = "#this filter file specifies the json attributes we do want delivered to the server\n" +
                "events.patient"
        service.grailsApplication = grailsApplication
        service.metaClass.getFilterText = { ->
            return filterText
        }
        service.metaClass.parseQueryTerms = { ->
            return parsedQuery
        }


        when:
        Map searchResults = service.parallelFederatedSearch(query)

        then:
        1 * openFDAService.query("device/event", parsedQuery, 100, 0) >> getClass().getResourceAsStream("OpenFDA-device-event.json").text
        1 * openFDAService.query("drug/label", parsedQuery, 100, 0) >> getClass().getResourceAsStream("OpenFDA-drug-label.json").text
        1 * openFDAService.query("food/enforcement", parsedQuery, 100, 0) >> getClass().getResourceAsStream("OpenFDA-food-enforcement.json").text
        1 * openFDAService.query("drug/event", parsedQuery, 100, 0) >> null
        1 * openFDAService.query("device/enforcement", parsedQuery, 100, 0) >> null
        1 * openFDAService.query("drug/enforcement", parsedQuery, 100, 0) >> null

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
        String parsedQuery = "ice AND cream"
        String filterText = "# comments should be ignored. \n" +
                "events.patient"

        service.grailsApplication = grailsApplication
        service.metaClass.getFilterText = { ->
            return filterText
        }
        service.metaClass.parseQueryTerms = { ->
            return parsedQuery
        }

        when:
        Map searchResults = service.federatedSearch(query)

        then:
        1 * openFDAService.query("device/event", parsedQuery, 100, 0) >> getClass().getResourceAsStream("OpenFDA-device-event.json").text
        1 * openFDAService.query("drug/label", parsedQuery, 100, 0) >> getClass().getResourceAsStream("OpenFDA-drug-label.json").text
        1 * openFDAService.query("food/enforcement", parsedQuery, 100, 0) >> getClass().getResourceAsStream("OpenFDA-food-enforcement.json").text
        1 * openFDAService.query("drug/event", parsedQuery, 100, 0) >> null
        1 * openFDAService.query("device/enforcement", parsedQuery, 100, 0) >> null
        1 * openFDAService.query("drug/enforcement", parsedQuery, 100, 0) >> null


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
        List<Map> searchResults = service.search(dataSet, query)

        then:
        1 * openFDAService.query(dataSet.path, query, 100, 0) >> getClass().getResourceAsStream(fileName).text
        searchResults

        where:
        fileName                        | dataSet
        "OpenFDA-device-event.json"     | [path: "device/event", group: "event"]
        "OpenFDA-drug-label.json"       | [path: "drug/label", group: "labels"]
        "OpenFDA-food-enforcement.json" | [path: "food/enforcement", group: "events"]
    }

    void "test search exception thrown"() {
        given:
        String query = "ice cream"

        when:
        List<Map> searchResults = service.search([path: "device/event", group: "event"], query)

        then:
        1 * openFDAService.query("device/event", query, 100, 0) >> { throw new FileNotFoundException() }
        thrown(FileNotFoundException)
        !searchResults
    }

    void "test loadFilters"() {
        given:
        String filterText = "#this filter file specifies the json attributes we do want delivered to the server\n" +
                "#they take the form <group>.att1.att2... where <group> is the group name which is\n" +
                "#either recalls, events, or labels\n" +
                "events.safetyreportid\n" +
                "events.report_number\n" +
                "#events.patient.sequence_number_outcome\n" +
                "events.patient\n" +
                "#events.patient\n" +
                "recalls.recall_number\n" +
                "labels.id"

        service.grailsApplication = grailsApplication
        service.metaClass.getFilterText = { ->
            return filterText
        }

        when:

        List<String> filtersList = service.loadFilters()

        then:
        filtersList
        filtersList.contains("events.patient")

        filterText.readLines().each { line ->
            if (!line.startsWith("#")) {
                assert filtersList.contains(line)
            }
        }
    }

    def "test filterResult"() {
        given:
        JSONArray jsonArray = new JSONArray([[x: 'x', y: 'y', z: 'z'], [x: 'x2', y: 'y2', z: 'z3'], [y: 'y3', z: 'z3']])
        def map = [a: [b: 'c', d: ['e': 'e1'], f: [g: 'h', 'i': ['a', 'b']]], w: jsonArray]
        Map result = [:]


        when:
        filters.each { filter ->
            service.filterResult(filter.split("\\."), result, map)
        }

        then:
        empty ? result.isEmpty() : !result.isEmpty()
        result == expected

        where:
        filters                   | expected                                                                        | empty
        ['z.a']                   | [:]                                                                             | 1
        []                        | [:]                                                                             | 1
        ['1']                     | [:]                                                                             | 1
        ["a.b"]                   | ["a": ["b": "c"]]                                                               | 0
        ["a.d"]                   | ["a": ["d": ["e": "e1"]]]                                                       | 0
        ["a.f"]                   | [a: [f: [g: 'h', i: ['a', 'b']]]]                                               | 0
        ["a.f.g"]                 | ['a': ['f': ['g': 'h']]]                                                        | 0
        ["a.d", "a.f.g", "a.f.i"] | ['a': ['f': ['g': 'h', 'i': ['a', 'b']], 'd': ['e': 'e1']]]                     | 0
        ["a.d", "w.x", "w.y"]     | ["a": ["d": ["e": "e1"]], w: [[x: 'x', y: 'y'], [x: 'x2', y: 'y2'], [y: 'y3']]] | 0
    }


    void "test parseQueryTerms"() {
        when:
        String parsedQueryTerms = service.parseQueryTerms(queryString)

        then:
        parsedQueryTerms == expected

        where:
        queryString                                                   | expected
        'ice cream'                                                   | 'ice AND cream'
        '"ice cream"'                                                 | '"ice cream"'
        '"ice cream, vanilla" brownies'                               | '"ice cream, vanilla" AND brownies'
        '"ice cream, vanilla" brownies syrup sprinkles'               | '"ice cream, vanilla" AND brownies AND syrup AND sprinkles'
        '"ice cream, vanilla" brownies "syrup sprinkles"'             | '"ice cream, vanilla" AND "syrup sprinkles" AND brownies'
        '"ice cream, vanilla" brownies chips pizza "syrup sprinkles"' | '"ice cream, vanilla" AND "syrup sprinkles" AND brownies AND chips AND pizza'
    }

}
