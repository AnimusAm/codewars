package hr.smilebacksmile.codewars.reverse

import java.util.regex.Matcher

class ReverseLetter {

    static String PATTERN = /[^a-zA-Z]+/

    static def reverseLetter(string) {
        def reversed = string.reverse()
        def result = ''<<''

        Matcher matcher = reversed =~ PATTERN

        int indStart = 0
        def length = reversed.length()
        int indEnd = length
        while (matcher.find()) {
            indEnd = matcher.start()
            result << reversed.substring(indStart, indEnd)
            indStart = matcher.end()
        }
        if (indStart == 0 && indEnd == length)
            (result << reversed).toString()
        else
            (result << reversed.substring(indStart, length)).toString()


        // return result.toString()
    }

}
