package com.sra.searchfda.geb.page

import geb.Page

class SearchPage extends Page {
    static at = {
        // title == getMessage("default.layout.app")
        title == 'checkFDA'
    }

    static url = 'search'

    static content = {
        searchQuery { $("#query") }
        searchButton { $("#search") }
    }
}
