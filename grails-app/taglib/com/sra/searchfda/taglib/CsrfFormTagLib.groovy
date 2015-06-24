package com.sra.searchfda.taglib

class CsrfFormTagLib {

    static namespace = "searchfda"

    //static defaultEncodeAs = [taglib:'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    Closure form = {attrs, body ->
        if (!attrs.containsKey('useToken')) {
            attrs.useToken = 'true'
        }

        out << g.form(attrs, body)
    }

}
