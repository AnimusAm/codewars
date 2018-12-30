package hr.smilebacksmile.codewars.repaymentplan;

public class Finance {


    public static String amort(double rate, int bal, int term, int num_payments) {

        final RepaymentPlanParameters parameters = new RepaymentPlanParameters(term, rate, bal, num_payments);
        return amort(parameters);
    }

    private static String amort(final RepaymentPlanParameters parameters) {

        double monthlyQuota = Finance.getMonthlyQuota(parameters);
        final RepaymentPlanItem item = Finance.calculateItem(1, new RepaymentPlanItem(monthlyQuota, parameters.getAmount()), parameters);
        return item != null ? item.formatOutput() : "";

    }

    private static RepaymentPlanItem calculateItem(int ordinal, RepaymentPlanItem previousItem, final RepaymentPlanParameters parameters) {

        RepaymentPlanItem currentItem = new RepaymentPlanItem(previousItem.getQuota(), previousItem.getBalance());
        double monthlyPrincipalRepayment = Finance.getMontlyPrincipalRepayment(parameters.monthlyRate, previousItem.getQuota(), previousItem.getBalance());
        currentItem.setMontlyAmounts(ordinal, monthlyPrincipalRepayment);
        if (ordinal == parameters.getUpTo()) {
            return currentItem;
        } else {
            return calculateItem(++ordinal, currentItem, parameters);
        }
    }

    private static double calculateMonthlyRate(double nominalMonthlyRate, double amount) {
       return nominalMonthlyRate*amount;
    }

    private static double calculateD(double nominalMonthlyRate, int term) {
        return 1. - Math.pow(1.+nominalMonthlyRate, -term);
    }

    private static double getMonthlyQuota(final RepaymentPlanParameters parameters) {
        return calculateMonthlyRate(parameters.getMonthlyRate(), parameters.getAmount())/calculateD(parameters.getMonthlyRate(), parameters.getTerm());
    }

    private static double getMontlyPrincipalRepayment(double rate, double monthlyQuota, double balance) {
        return monthlyQuota - calculateMonthlyRate(rate, balance);
    }


    private static class RepaymentPlanParameters {
        private int term;
        private double monthlyRate;
        private double amount;
        private int upTo;

        public RepaymentPlanParameters(int term, double nominalRate, double amount, int upTo) {
            this.term = term;
            this.monthlyRate = nominalRate/(1200.); // percentage * 12 months
            this.amount = amount;
            this.upTo = upTo;
        }

        public int getTerm() {
            return term;
        }

        public double getMonthlyRate() {
            return monthlyRate;
        }

        public double getAmount() {
            return amount;
        }

        public int getUpTo() {
            return upTo;
        }
    }

    private static class RepaymentPlanItemBase {
        double quota;
        double balance;

        public RepaymentPlanItemBase(double quota, double balance) {
            this.quota = quota;
            this.balance = balance;
        }

        public double getQuota() {
            return this.quota;
        }

        public double getBalance() {
            return balance;
        }
    }

    private static class RepaymentPlanItem extends RepaymentPlanItemBase {
        int payment_number;
        double principal = 0.0;

        public RepaymentPlanItem(double quota, double balance) {
            super(quota, balance);
        }

        public void setMontlyAmounts(int payment_number, double principal, double balance) {
            this.payment_number = payment_number;
            this.principal = principal;
            this.balance = balance;
        }

        public void setMontlyAmounts(int payment_number, double principal) {
            this.payment_number = payment_number;
            this.principal = principal;
            this.balance = this.balance - this.principal;
        }

        public int getPaymentNumber() {
            return payment_number;
        }

        public double getPrincipal() {
            return principal;
        }

        public double getRate() {
            return this.quota-this.principal;
        }

        public String formatOutput() {
            return String.format("num_payment %d c %.0f princ %.0f int %.0f balance %.0f", this.getPaymentNumber(), this.getQuota(), this.getPrincipal(), this.getRate(), this.getBalance());
        }
    }


}
