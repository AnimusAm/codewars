package hr.smilebacksmile.codewars.sumoddnumbers.domain;

public class RowSumOddNumbers {
    public static int rowSumOddNumbers(int n) {
        return sumRowUpTo(n, n);
    }

    public static int sumRowUpTo(int n, int i) {
        if (i == 1) {
            return getOddNumberByIndex(n, 1);
        } else {
            return getOddNumberByIndex(n, i) + sumRowUpTo(n, i-1);
        }
    }

    public static int getOddNumberByIndex(int n, int i) {
        if (i < 1) {
            return 0;
        } else if (i > n) {
            return 0;
        } else {
            // return getOddNumber((n*n-n)/2+i);

            return (n-1)*n+2*i-1;
        }
    }

    public static int getOddNumber(int i) {
        return i*2-1;
    }
}