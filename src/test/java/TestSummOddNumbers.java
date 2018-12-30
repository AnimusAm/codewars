import org.junit.Test;
import hr.smilebacksmile.codewars.sumoddnumbers.domain.*;

import static org.junit.Assert.assertEquals;

public class TestSummOddNumbers {

    /*
    Given the triangle of consecutive odd numbers:

            1
            3     5
            7     9    11
            13    15    17    19
            21    23    25    27    29
            ...
    Calculate the row sums of this triangle from the row index (starting at index 1) e.g.:

    rowSumOddNumbers(1); // 1
    rowSumOddNumbers(2); // 3 + 5 = 8
    */

    @Test
    public void TestFetchingOddNumber() {
        assertEquals(1, RowSumOddNumbers.getOddNumber(1));
        assertEquals(5, RowSumOddNumbers.getOddNumber(3));
        assertEquals(19, RowSumOddNumbers.getOddNumber(10));
        assertEquals(29, RowSumOddNumbers.getOddNumber(15));
    }


    @Test
    public void TestFetchingOddNumberInRow() {
        // assertEquals(3, RowSumOddNumbers.getOddNumberByIndex(2, 1));
        // assertEquals(5, RowSumOddNumbers.getOddNumberByIndex(2, 2));

        assertEquals(11, RowSumOddNumbers.getOddNumberByIndex(3, 3));

    }

    @Test
    public void TestGetSumInRow() {
        assertEquals(27, RowSumOddNumbers.rowSumOddNumbers(3));
    }
}
