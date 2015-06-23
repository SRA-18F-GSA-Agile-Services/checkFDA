package com.sra.searchfda

class CsrfFormTagLib {

    static namespace = "searchfda"

    //static defaultEncodeAs = [taglib:'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    @SuppressWarnings("UseCsrfSafeFormOnlyInTaglibs") // This is the only place we should be using the g.form tag.
    Closure form = {attrs, body ->
        if (!attrs.containsKey('useToken')) {
            attrs.useToken = 'true'
        }

        out << g.form(attrs, body)
    }

}
