package hr.smilebacksmile.codewars.repaymentplan.groovy

class FinanceGroovyTest extends GroovyTestCase {

    static void testingGroovy(double rate, int bal, int term, int num_payments, String expected) {
        assert expected == Finance.amort(rate, bal, term, num_payments)
    }

    void testAmort() {

        testingGroovy(7.4, 10215, 24, 20, 'num_payment 20 c 459 princ 445 int 14 balance 1809')
        testingGroovy(7.9, 107090, 48, 41, 'num_payment 41 c 2609 princ 2476 int 133 balance 17794')
        testingGroovy(6.8, 105097, 36, 4, 'num_payment 4 c 3235 princ 2685 int 550 balance 94447')
        testingGroovy(3.8, 48603, 24, 10, 'num_payment 10 c 2106 princ 2009 int 98 balance 28799')
    }
}
