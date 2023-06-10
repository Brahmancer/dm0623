package toolVender.data.agreement;

import toolVender.data.tool.Tool;

/**
 * Model for a rental agreement for tools
 */
public class RentalAgreement {
    // Member variables
    private Tool theToolToRent = null;
    private int theRentalDayCount = 0;
    // TODO: Unsure what to do with the date right now. 
    private String theCheckOutDate = "";
    private double theDiscountRate = 0.0;
    
    /**
     * Getters 
     */
    public Tool getTool() { return theToolToRent; }
    public int getRentalDayCount() { return theRentalDayCount; }
    public String getCheckoutDate() { return theCheckOutDate; }
    public double getDiscountRate() { return theDiscountRate; }

    /*
    TODO: Need to have the toString() method format the RentalAgreement into something that looks like a contract/receipt.
    @Override
    public String toString(){
        String returnString = "";

        return returnString;
    }
     */
}
