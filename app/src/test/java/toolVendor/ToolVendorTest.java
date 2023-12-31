package toolVendor;

import org.junit.jupiter.api.Test;

import toolVendor.data.agreement.RentalAgreement;
import toolVendor.exceptions.DiscountOutOfBoundsException;
import toolVendor.exceptions.InvalidRentalDayException;

import static org.junit.jupiter.api.Assertions.*;

import java.security.InvalidParameterException;

import org.junit.jupiter.api.BeforeAll;

class ToolVendorTest {
    // Global testing variables to set up prior to tests 
    private static ToolVendor testToolVendor = null;
    private static String[] testJuneDateFormatsUSA = {
        "6/12/23",
        "06/12/23",
        "06/12/2023"
    };
    private static String[] testSeptemberDateFormatsUSA = {
        "09/3/15",
        "9/3/2015"
    };

    /**
     * Before all set up script 
     */
    @BeforeAll 
    public static void setupTest(){
        testToolVendor = new ToolVendor();
    }

    /**
     * Test 1 for checkout
     * 
     * This also acts as a DiscountOutOfBoundsException test
     */
    @Test 
    public void checkoutTest1(){
        DiscountOutOfBoundsException exception = assertThrows(DiscountOutOfBoundsException.class, () ->{
            testToolVendor.checkoutTool("JAKR", "9/3/15", 5, 101);
        });

        String expectedMessage = "Discount of 101.0 is invalid. Please input a discount between 0 and 100 inclusively.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.equals(expectedMessage));
    }

    /**
     * Test 1 for checkout that's in bounds
     */
    @Test 
    public void checkoutTest1InBounds(){
        RentalAgreement testAgreement = testToolVendor.checkoutTool("JAKR", "9/3/15", 5, 0);

        assertNotNull(testAgreement);

        String expectedReceipt = "Tool Code: JAKR\n" + //
                "Tool Type: Jackhammer\n" + //
                "Tool Brand: Ridgid\n" + //
                "Rental days: 5\n" + //
                "Checkout date: 09/03/15\n" + //
                "Due date: 09/08/15\n" + //
                "Daily rental rate: $2.99\n" + //
                "Charge days: 2\n" + //
                "Pre-discount cost: $5.98\n" + //
                "Discount percent: 0.0%\n" + //
                "Discount amount: $0.00\n" + //
                "Final Charge: $5.98";
        String actualReceipt = testAgreement.toString();        
        assertTrue(actualReceipt.equals(expectedReceipt));
    }

    /**
     * Test 2 for checkout
     */
    @Test 
    public void checkoutTest2(){
        RentalAgreement testAgreement = testToolVendor.checkoutTool("LADW", "7/2/20", 3, 10);

        assertNotNull(testAgreement);

        String expectedReceipt = "Tool Code: LADW\n" + //
                "Tool Type: Ladder\n" + //
                "Tool Brand: Werner\n" + //
                "Rental days: 3\n" + //
                "Checkout date: 07/02/20\n" + //
                "Due date: 07/05/20\n" + //
                "Daily rental rate: $1.99\n" + //
                "Charge days: 2\n" + //
                "Pre-discount cost: $3.98\n" + //
                "Discount percent: 10.0%\n" + //
                "Discount amount: $0.40\n" + //
                "Final Charge: $3.58";
        String actualReceipt = testAgreement.toString();
        assertTrue(actualReceipt.equals(expectedReceipt));
    }

    /**
     * Test 3 for checkout
     */
    @Test 
    public void checkoutTest3(){
        RentalAgreement testAgreement = testToolVendor.checkoutTool("CHNS", "7/2/15", 5, 25);

        assertNotNull(testAgreement);

        String expectedReceipt = "Tool Code: CHNS\n" + //
                "Tool Type: Chainsaw\n" + //
                "Tool Brand: Stihl\n" + //
                "Rental days: 5\n" + //
                "Checkout date: 07/02/15\n" + //
                "Due date: 07/07/15\n" + //
                "Daily rental rate: $1.49\n" + //
                "Charge days: 3\n" + //
                "Pre-discount cost: $4.47\n" + //
                "Discount percent: 25.0%\n" + //
                "Discount amount: $1.12\n" + //
                "Final Charge: $3.35";
        String actualReceipt = testAgreement.toString();
        assertTrue(actualReceipt.equals(expectedReceipt));
    }

    /**
     * Test 4 for checkout
     */
    @Test 
    public void checkoutTest4(){
        RentalAgreement testAgreement = testToolVendor.checkoutTool("JAKD", "9/3/15", 6, 0);

        assertNotNull(testAgreement);

        String expectedReceipt = "Tool Code: JAKD\n" + //
                "Tool Type: Jackhammer\n" + //
                "Tool Brand: DeWalt\n" + //
                "Rental days: 6\n" + //
                "Checkout date: 09/03/15\n" + //
                "Due date: 09/09/15\n" + //
                "Daily rental rate: $2.99\n" + //
                "Charge days: 3\n" + //
                "Pre-discount cost: $8.97\n" + //
                "Discount percent: 0.0%\n" + //
                "Discount amount: $0.00\n" + //
                "Final Charge: $8.97";
        String actualReceipt = testAgreement.toString();
        assertTrue(actualReceipt.equals(expectedReceipt));
    }

    /**
     * Test 5 for checkout
     */
    @Test 
    public void checkoutTest5(){
        RentalAgreement testAgreement = testToolVendor.checkoutTool("JAKR", "7/2/15", 9, 0);

        assertNotNull(testAgreement);

        String expectedReceipt = "Tool Code: JAKR\n" + //
                "Tool Type: Jackhammer\n" + //
                "Tool Brand: Ridgid\n" + //
                "Rental days: 9\n" + //
                "Checkout date: 07/02/15\n" + //
                "Due date: 07/11/15\n" + //
                "Daily rental rate: $2.99\n" + //
                "Charge days: 5\n" + //
                "Pre-discount cost: $14.95\n" + //
                "Discount percent: 0.0%\n" + //
                "Discount amount: $0.00\n" + //
                "Final Charge: $14.95";
        String actualReceipt = testAgreement.toString();
        assertTrue(actualReceipt.equals(expectedReceipt));
    }

    /**
     * Test 6 for checkout
     */
    @Test 
    public void checkoutTest6(){
        RentalAgreement testAgreement = testToolVendor.checkoutTool("JAKR", "7/2/20", 4, 50);

        assertNotNull(testAgreement);

        String expectedReceipt = "Tool Code: JAKR\n" + //
                "Tool Type: Jackhammer\n" + //
                "Tool Brand: Ridgid\n" + //
                "Rental days: 4\n" + //
                "Checkout date: 07/02/20\n" + //
                "Due date: 07/06/20\n" + //
                "Daily rental rate: $2.99\n" + //
                "Charge days: 1\n" + //
                "Pre-discount cost: $2.99\n" + //
                "Discount percent: 50.0%\n" + //
                "Discount amount: $1.50\n" + //
                "Final Charge: $1.49";
        String actualReceipt = testAgreement.toString();
        assertTrue(actualReceipt.equals(expectedReceipt));
    }

    /**
     * Test Invalid Rental Day exception
     */
    @Test
    public void testInvalidRentalDayException() {
        InvalidRentalDayException exception = assertThrows(InvalidRentalDayException.class, () ->{
            testToolVendor.checkoutTool("JAKR", "9/3/15", -1, 10);
        });

        String expectedMessage = "Rental day count of -1 is invalid. Please have at least one day for tool rental.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.equals(expectedMessage));
    }

    /**
     * Test invalid parameter exception scenarios
     */
    @Test
    public void testInvalidParameterExceptions(){

        assertThrows(InvalidParameterException.class, () -> {
            testToolVendor.checkoutTool(null, "11/7/23", 1, 0);
        });

        assertThrows(InvalidParameterException.class, () -> {
            testToolVendor.checkoutTool("JAKR", null, 1, 0);
        });
    }
    
    /**
     * Test Chainsaw checkout around 4th of july that's not on a Weekend.
     */
    @Test
    public void testChainSawFourthOfJulyHoliday(){
        RentalAgreement testAgreement = testToolVendor.checkoutTool("CHNS", "7/3/23", 4, 0);

        assertNotNull(testAgreement);

        String expectedReceipt = "Tool Code: CHNS\n" + //
                "Tool Type: Chainsaw\n" + //
                "Tool Brand: Stihl\n" + //
                "Rental days: 4\n" + //
                "Checkout date: 07/03/23\n" + //
                "Due date: 07/07/23\n" + //
                "Daily rental rate: $1.49\n" + //
                "Charge days: 4\n" + //
                "Pre-discount cost: $5.96\n" + //
                "Discount percent: 0.0%\n" + //
                "Discount amount: $0.00\n" + //
                "Final Charge: $5.96";
        String actualReceipt = testAgreement.toString();
        assertTrue(actualReceipt.equals(expectedReceipt));
    }
    
    /**
     * Test Ladder checkout on a normal weekend.
     */
    @Test
    public void testLadderWeekend(){
        RentalAgreement testAgreement = testToolVendor.checkoutTool("LADW", "5/5/23", 4, 10);

        assertNotNull(testAgreement);

        String expectedReceipt = "Tool Code: LADW\n" + //
                "Tool Type: Ladder\n" + //
                "Tool Brand: Werner\n" + //
                "Rental days: 4\n" + //
                "Checkout date: 05/05/23\n" + //
                "Due date: 05/09/23\n" + //
                "Daily rental rate: $1.99\n" + //
                "Charge days: 4\n" + //
                "Pre-discount cost: $7.96\n" + //
                "Discount percent: 10.0%\n" + //
                "Discount amount: $0.80\n" + //
                "Final Charge: $7.16";
        String actualReceipt = testAgreement.toString();
        assertTrue(actualReceipt.equals(expectedReceipt));
    }

    /**
     * Checkout a Jackhammer during the work week
     */
    @Test 
    public void testGenericJackhammer(){
        RentalAgreement testAgreement = testToolVendor.checkoutTool("JAKD", "6/12/23", 4, 15);

        assertNotNull(testAgreement);

        String expectedReceipt = "Tool Code: JAKD\n" + //
                "Tool Type: Jackhammer\n" + //
                "Tool Brand: DeWalt\n" + //
                "Rental days: 4\n" + //
                "Checkout date: 06/12/23\n" + //
                "Due date: 06/16/23\n" + //
                "Daily rental rate: $2.99\n" + //
                "Charge days: 4\n" + //
                "Pre-discount cost: $11.96\n" + //
                "Discount percent: 15.0%\n" + //
                "Discount amount: $1.79\n" + //
                "Final Charge: $10.17";
        String actualReceipt = testAgreement.toString();
        assertTrue(actualReceipt.equals(expectedReceipt));
    }

    /**
     * Checkout a Chainsaw on Labor day
     */
    @Test
    public void testChainSawLaborDay(){
        RentalAgreement testAgreement = testToolVendor.checkoutTool("CHNS", "9/3/20", 7, 50);

        assertNotNull(testAgreement);

        String expectedReceipt = "Tool Code: CHNS\n" + //
                "Tool Type: Chainsaw\n" + //
                "Tool Brand: Stihl\n" + //
                "Rental days: 7\n" + //
                "Checkout date: 09/03/20\n" + //
                "Due date: 09/10/20\n" + //
                "Daily rental rate: $1.49\n" + //
                "Charge days: 5\n" + //
                "Pre-discount cost: $7.45\n" + //
                "Discount percent: 50.0%\n" + //
                "Discount amount: $3.73\n" + //
                "Final Charge: $3.72";
        String actualReceipt = testAgreement.toString();
        assertTrue(actualReceipt.equals(expectedReceipt));
    }

    /**
     * Test some checkouts with different USA aligned date formats (i.e. month/day/year)
     */
    @Test
    public void testMultipleDateFormatsUSA(){
        // For each date in the test date format in June
        for (String date : testJuneDateFormatsUSA) {
            RentalAgreement testAgreement = testToolVendor.checkoutTool("JAKD", date, 4, 15);

            assertNotNull(testAgreement);

            String expectedReceipt = "Tool Code: JAKD\n" + //
                    "Tool Type: Jackhammer\n" + //
                    "Tool Brand: DeWalt\n" + //
                    "Rental days: 4\n" + //
                    "Checkout date: 06/12/23\n" + //
                    "Due date: 06/16/23\n" + //
                    "Daily rental rate: $2.99\n" + //
                    "Charge days: 4\n" + //
                    "Pre-discount cost: $11.96\n" + //
                    "Discount percent: 15.0%\n" + //
                    "Discount amount: $1.79\n" + //
                    "Final Charge: $10.17";
            String actualReceipt = testAgreement.toString();
            assertTrue(actualReceipt.equals(expectedReceipt));
        }
    
        // For each date in test date format in September
        for (String date : testSeptemberDateFormatsUSA) {
            RentalAgreement testAgreement = testToolVendor.checkoutTool("JAKR", date, 5, 0);

            assertNotNull(testAgreement);

            String expectedReceipt = "Tool Code: JAKR\n" + //
                    "Tool Type: Jackhammer\n" + //
                    "Tool Brand: Ridgid\n" + //
                    "Rental days: 5\n" + //
                    "Checkout date: 09/03/15\n" + //
                    "Due date: 09/08/15\n" + //
                    "Daily rental rate: $2.99\n" + //
                    "Charge days: 2\n" + //
                    "Pre-discount cost: $5.98\n" + //
                    "Discount percent: 0.0%\n" + //
                    "Discount amount: $0.00\n" + //
                    "Final Charge: $5.98";
            String actualReceipt = testAgreement.toString();
            assertTrue(actualReceipt.equals(expectedReceipt));
        }
    }
}
