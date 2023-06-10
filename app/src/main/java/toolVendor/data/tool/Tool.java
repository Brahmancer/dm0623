package toolVendor.data.tool;

import java.util.Locale;

/**
 * Model Class for Tool objects
 */
public class Tool {
    /**
     * Member variables
     */

    // Character code denoting the tool to checkout 
    private String theToolCode = "";

    // Enumerations to check the Tool's type and brand
    private ToolType theToolType = ToolType.NONE; 
    private ToolBrand theToolBrand = ToolBrand.NONE;
    
    // The tool's charge rate in USD.
    private double theDailyChargeRate = 0.0;

    // Flags indicating when to apply the charge.
    // Set the weekday charge flag to true by default
    // because all tools, at least for now, have a rate during the weekday. 
    private boolean theApplyWeekdayChargeFlag = true;
    private boolean theApplyWeekendChargeFlag = false;
    private boolean theApplyHolidayChargeFlag = false;

    /**
     * Tool Constructor 
     */
    public Tool(String code) { 

        // Sanity check input
        if (code != null) {
            // Set the code
            theToolCode = code.toUpperCase(Locale.getDefault());    
        }
        // else leave the code as empty string.

        // Fill out remaining fields based on the tool code.
        switch (theToolCode) {
            case "CHNS":
                theToolType = ToolType.CHAINSAW;
                theToolBrand = ToolBrand.STIHL;
                theDailyChargeRate = 1.49;
                // Chainsaws currently charge on Weekdays and Holidays
                theApplyHolidayChargeFlag = true;
                break;
            case "LADW":
                theToolType = ToolType.LADDER;
                theToolBrand = ToolBrand.WERNER;
                theDailyChargeRate = 1.99;
                // Ladders currently charge on Weekdays and Weekends
                theApplyWeekdayChargeFlag = true;
                break;
            case "JAKD":
                theToolType = ToolType.JACKHAMMER;
                theToolBrand = ToolBrand.DEWALT;
                theDailyChargeRate = 2.99;
                // Jackhammers don't have weekend or holiday charge rates
                break;
            case "JAKR":
                theToolType = ToolType.JACKHAMMER;
                theToolBrand = ToolBrand.RIDGID;
                theDailyChargeRate = 2.99;
                // Jackhammers don't have weekend or holiday charge rates
                break;
            default:
                // Do nothing
                break;
        }
    }

    /**
     * Copy constructor
     */
    public Tool(Tool toolToCopy) {

        // Verify non-null parameter.
        if (toolToCopy != null) {
            // Copy over the fields
            this.theToolCode = toolToCopy.theToolCode;
            this.theToolType = toolToCopy.theToolType;
            this.theToolBrand = toolToCopy.theToolBrand;
            this.theDailyChargeRate = toolToCopy.theDailyChargeRate;
            this.theApplyHolidayChargeFlag = toolToCopy.theApplyHolidayChargeFlag;
            this.theApplyWeekdayChargeFlag = toolToCopy.theApplyWeekdayChargeFlag;
            this.theApplyWeekendChargeFlag = toolToCopy.theApplyWeekendChargeFlag;
        }
    }

    /**
     * Getter functions 
     */
    public String getToolCode() { return theToolCode; }
    public ToolType getToolType() { return theToolType; }
    public ToolBrand getToolBrand() { return theToolBrand; }
    public double getDailyChargeRate() { return theDailyChargeRate; }
    public boolean doWeekdayRatesApply() { return theApplyWeekdayChargeFlag; }
    public boolean doWeekendRatesApply() { return theApplyWeekendChargeFlag; }
    public boolean doHolidayRatesApply() { return theApplyHolidayChargeFlag; }
}
