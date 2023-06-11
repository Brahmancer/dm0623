package toolVendor.data.agreement;

import toolVendor.data.tool.Tool;
import toolVendor.util.CalendarUtility;

/**
 * Model for a rental agreement for tools
 */
public class RentalAgreement {
    // Member variables
    
    // Input members
    private Tool theToolToRent = null;
    private int theRentalDayCount = 0;
    private String theCheckOutDateString = "";
    // The discount rate is a whole number e.g. 20 == 20%
    private double theDiscountRate = 0.0;
    
    // Output members
    private int theChargableDayCount = 0;
    private double theInitialPrice = 0.0;
    private String theReturnDateString = "";
    private double theAmountOffFromDiscount = 0.0;
    private double theTotalPrice = 0.0;
    
    /**
     * Constructor
     * 
     * @param tool: Tool to rent out 
     * @param checkoutDateString: A string representation of the checkout out date
     * @param rentalDayCount: The # of days the tool is being rented. 
     * @param discountRate: Rate of discount on the tool. 
     */
    public RentalAgreement(Tool tool, String checkoutDateString, int rentalDayCount, double discountRate)
    {
        // Set parameters
        theToolToRent = tool;
        theRentalDayCount = rentalDayCount;
        theDiscountRate = discountRate;

        // Put the input in a friendly format
        theCheckOutDateString = CalendarUtility.getStartDateString(checkoutDateString);
    }

    /**
     * Generates a rental agreement. 
     */
    public void generateAgreement()
    {
        // Generate the end date and the chargable days
        theReturnDateString = CalendarUtility.getEndDateString(theCheckOutDateString, theRentalDayCount);
        theChargableDayCount = CalendarUtility.getChargableDays(theCheckOutDateString, theRentalDayCount, theToolToRent);

        // Generate the initial price
        theInitialPrice = theChargableDayCount * theToolToRent.getDailyChargeRate();

        // Calculate dollars off. 
        // The Discount rate is a whole number so in order to get the actual percentage, divide by 100.
        theAmountOffFromDiscount = theInitialPrice *  (theDiscountRate / 100.0);

        // Generate the total price
        theTotalPrice = theInitialPrice - theAmountOffFromDiscount;
    }

    /**
     * Getters 
     */
    public Tool getTool() { return theToolToRent; }
    public int getRentalDayCount() { return theRentalDayCount; }
    public String getCheckoutDateString() { return theCheckOutDateString; }
    public double getDiscountRate() { return theDiscountRate; }

    /**
     * Generates a string formatted to look like a reciept.
     */
    @Override
    public String toString(){
        String returnString = String.format(
            "Tool Code: %s\nTool Type: %s\nTool Brand: %s\nRental days: %d\nCheck out date: %s\nDue date: %s\nDaily rental rate: %.2f\nCharge Days: %d\n" + 
            "Pre-discount charge: %.2f\nDiscount percent: %.1f\nAmount saved from discount: %.2f\nTotal Cost: %.2f",
             theToolToRent.getToolCode(),
             theToolToRent.getToolTypeString(),
             theToolToRent.getToolBrandString(),
             theRentalDayCount,
             theCheckOutDateString,
             theReturnDateString, 
             theToolToRent.getDailyChargeRate(),
             theChargableDayCount, 
             theInitialPrice,
             theDiscountRate,
             theAmountOffFromDiscount,
             theTotalPrice);

        return returnString;
    }
}
