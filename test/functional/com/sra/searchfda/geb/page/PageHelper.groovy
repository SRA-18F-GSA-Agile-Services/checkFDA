package com.sra.searchfda.geb.page

import grails.util.Holders
import org.springframework.context.ApplicationContext
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

class PageHelper {

    // This does not yet work -- we have not successfully accessed the application context.
    static String getMessage(String messageCode, Object... args = [] as Object[]) {
        ApplicationContext context = Holders.applicationContext
        MessageSource messageSource = context.getBean('messageSource')
        String messageWithoutTags = cleanStringOfHtmlTags(messageSource.getMessage(messageCode, args, LocaleContextHolder.locale))
        String messageWithoutDoubleSpaces = messageWithoutTags.replaceAll("  ", " ")
        return messageWithoutDoubleSpaces
    }

    static String cleanStringOfHtmlTags(String inValue) {
        return inValue.replaceAll("<(.|\n)*?>", '').trim()
    }
}
