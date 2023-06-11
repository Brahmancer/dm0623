package toolVendor.data.agreement;

import java.math.BigDecimal;

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
        theAmountOffFromDiscount = Math.round((theInitialPrice *  (theDiscountRate / 100.0))* 100) / 100.0;

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
        StringBuilder builder = new StringBuilder("");
        String formatString = "";

        // Tool code, type, and brand
        builder.append("Tool Code: " + theToolToRent.getToolCode() + "\n");
        builder.append("Tool Type: " + theToolToRent.getToolTypeString() + "\n");
        builder.append("Tool Brand: " + theToolToRent.getToolBrandString() + "\n");
        
        // Checkout date, due date, and rental day count 
        builder.append("Rental days: " + theRentalDayCount + "\n");
        builder.append("Checkout date: " + theCheckOutDateString + "\n");
        builder.append("Due date: " + theReturnDateString + "\n");
        formatString = String.format("%.2f", theToolToRent.getDailyChargeRate());
        builder.append("Daily rental rate: $" + formatString + "\n");
        builder.append("Charge days: " + theChargableDayCount + "\n");        
        
        // The cost itemization

        // Pre-discount cost
        formatString = String.format("%.2f", theInitialPrice);
        builder.append("Pre-discount cost: $" + formatString + "\n");
        // Discount rate 
        formatString = String.format("%.1f", theDiscountRate);
        builder.append("Discount percent: " + formatString + "%\n");
        // Money off from Discount 
        formatString = String.format("%.2f", theAmountOffFromDiscount);
        builder.append("Discount amount: $" + formatString + "\n");
        // Total cost
        formatString = String.format("%.2f", theTotalPrice);
        builder.append("Final Charge: $" + formatString);

        return builder.toString();
    }
}
