package hr.smilebacksmile.codewars.morse

const val WORD_DELIMITER_REGEX = """\s{3}"""

const val LETTER_DELIMITER_REGEX = """\s{1}"""

val MorseCode :Map<String, String> = mapOf(
        Pair("-..", "D"),
        Pair(".", "E"),
        Pair("....", "H"),
        Pair(".---", "J"),

        Pair("..-", "U"),
        Pair("-.--", "Y")
)

fun main(args : Array<String>) {
    val result = decodeMorse(" .... . -.--   .--- ..- -.. .     ")
    println("$result")

}

fun decodeMorse(code: String): String {

    val input :String = code.trim()
    val words = input.split(WORD_DELIMITER_REGEX.toRegex())

    val result: String = words.joinToString(separator = " ") {
        it.split(LETTER_DELIMITER_REGEX.toRegex()).joinToString(separator = "") { MorseCode.getOrDefault(it, "") }
    }
    return result
}

