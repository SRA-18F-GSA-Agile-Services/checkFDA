package com.sra.searchfda


class TagLibTestHelper {

    static String cleanWhitespace(String tagOutput) {
        String removeReturns = tagOutput.replaceAll('\r\n', '').replaceAll('\n', '')
        String removeExtraSpaces = removeReturns.replaceAll("( )\\1+", " ")
        return removeExtraSpaces
    }
}
