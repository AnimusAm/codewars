package hr.smilebacksmile.codewars.repaymentplan.groovy

import org.apache.tools.ant.taskdefs.XSLTProcess


class Finance {

    static String amort(double rate, int bal, int term, int num_payments) {

        def instance = new Finance()
        def parameters = new Parameters(instance, term, rate/1200d, bal as double, num_payments)
        return amort(parameters, instance)
    }

    static def amort(Parameters parameters, Finance instance) {

        double monthlyQuota = getMonthlyQuota(parameters)
        def prevItem = [instance, [quota: monthlyQuota, balance: parameters.getAmount()]] as Item
        def item = calculateItem(1, prevItem, parameters, instance)
        return item != null ? item.formatOutput() : ""

    }

    static Item calculateItem(ordinal, previousItem, parameters, Finance instance) {

        Item currentItem = [instance, [quota: previousItem.getQuota(), balance: previousItem.getBalance()]]
        def monthlyPrincipalRepayment = getMonthlyPrincipalRepayment(parameters.getMonthlyRate(), previousItem.getQuota(), previousItem.getBalance())

        // example of method call without using parenthesis
        currentItem.setMonthlyAmounts ordinal, monthlyPrincipalRepayment

        if (ordinal == parameters.getUpTo()) {
            return currentItem
        } else {
            return calculateItem(++ordinal, currentItem, parameters, instance)
        }
    }

    static double power(d, n) { d**n }

    static def calculateMonthlyRate(nominalMonthlyRate, amount) {
        nominalMonthlyRate*amount as double
    }

    static def calculateD(nominalMonthlyRate, term) {
        1d - power(1d+nominalMonthlyRate, -term)
    }

    def static getMonthlyQuota = {
        parameters ->
        calculateMonthlyRate(parameters.getMonthlyRate(), parameters.getAmount())/calculateD(parameters.getMonthlyRate(), parameters.getTerm())
    }

    def static getMonthlyPrincipalRepayment = { rate, monthlyQuota, balance ->
        monthlyQuota - calculateMonthlyRate(rate, balance)
    }

    private class Parameters {
        int term
        double monthlyRate
        double amount
        int upTo

        Parameters(term, monthlyRate, amount, upTo) {
            this.term = term
            this.monthlyRate = monthlyRate
            this.amount = amount
            this.upTo = upTo
        }
    }

    private class Item {

        private double quota
        private double balance
        private int payment_number
        private double principal
        private double rate

        Item(Map attrs) {
            quota = attrs.quota
            balance = attrs.balance
        }

        def setMonthlyAmounts(payment_number, principal = 0.0d) {
            this.payment_number = payment_number
            this.principal = principal
            balance = this.balance - this.principal
            rate = quota-this.principal
        }

        double getQuota() {
            return quota
        }

        double getBalance() {
            return balance
        }

        def formatOutput() {
            return sprintf("num_payment $payment_number c "
                        + '%.0f princ %.0f int %.0f balance %.0f', [quota, principal, rate, balance])
         }
    }

}
