package com.sra.searchfda.geb

import com.sra.searchfda.geb.page.LoginPage
import com.sra.searchfda.geb.page.SearchPage
import org.junit.Test

class LoginTests extends GebTestBase {

    @Test
    void testLogin() {

        to LoginPage
        at LoginPage

        username = 'admin'
        password = 'stbadmin2014'

        loginButton.click()

        at SearchPage

        go "logout"

        at SearchPage

        to LoginPage
        at LoginPage
    }

}
