package hr.smilebacksmile.codewars.reverse

class ReverseLetterTest extends GroovyTestCase {
    void testReverseLetter() {
        assert ReverseLetter.reverseLetter("krishan") == "nahsirk"
        assert ReverseLetter.reverseLetter("ultr53o?n") == "nortlu"
        assert ReverseLetter.reverseLetter("ab23c") == "cba"
        assert ReverseLetter.reverseLetter("krish21an") == "nahsirk"
    }
}
