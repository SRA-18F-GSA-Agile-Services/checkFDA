package com.sra.searchfda.geb.page

import geb.Page

class LoginPage extends Page {

    static at = {
        //title == getMessage("springSecurity.login.title")
        title == 'Login'
    }

    static url = "login/auth"

    static content = {
        username  { $("[name='j_username']")}
        password  { $("[name='j_password']")}
        loginButton { $(".button")}
    }
}
