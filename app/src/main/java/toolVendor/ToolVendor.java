package toolVendor;

import java.security.InvalidParameterException;

import toolVendor.data.agreement.RentalAgreement;
import toolVendor.data.tool.Tool;
import toolVendor.exceptions.DiscountOutOfBoundsException;
import toolVendor.exceptions.InvalidRentalDayException;

/**
 * Class acting as a Vendor application to generate rental agreements for tools.
 */
public class ToolVendor {   
   
    /**
     * Method to checkout tools
     * 
     * @param toolCode: A code matching to a tool to checkout 
     * @param checkoutDate: A string formatted like "mm/dd/yy" to describe the checkout date
     * @param rentalDayCount: The # of days to rent the tool.
     * @param discount: A discount percentage rate
     * 
     * @return: A rental Aggreement that matches the parameters or null if invalid.
     * 
     * @throws InvalidRentalDayException
     * @throws DiscountOutOfBoundsException
     */
    public RentalAgreement checkoutTool(String toolCode, String checkoutDate, int rentalDayCount, double discount) throws InvalidRentalDayException, DiscountOutOfBoundsException{
        // Check Parameters

        // Check valid parameters
        if (toolCode != null && checkoutDate != null && rentalDayCount > 0 && discount >= 0.0 && discount <= 100.0){
            // Initial valid paramter check passed! 

            // Create a new tool 
            Tool toolToRent = new Tool(toolCode);

            // Create the Rental Agreement
            RentalAgreement toolRentalAgreement = new RentalAgreement(toolToRent, checkoutDate, rentalDayCount, discount);

            // Generate the agreement
            toolRentalAgreement.generateAgreement();

            // return the new agreement. 
            return toolRentalAgreement;
        }
        // else some parameters are off. 
        // Check for null 
        else if (toolCode == null)
        {
            throw new InvalidParameterException("Tool code was empty. Please provide a tool code.");
        }
        else if (checkoutDate == null)
        {
            throw new InvalidParameterException("Checkout date was empty. Please provide a valid checkout date.");
        }
        else if (rentalDayCount <= 0)
        {
            String errorString = String.format("Rental day count of %d is invalid. Please have at least one day for tool rental.", rentalDayCount);
            throw new InvalidRentalDayException(errorString);
        }
        else if (discount < 0.0 || discount > 100.0)
        {
            String errorString = String.format("Discount of %.1f is invalid. Please input a discount between 0 and 100 inclusively.", discount);
            throw new DiscountOutOfBoundsException(errorString);
        }

        // If nothing else hits, return null.
        return null;
    }
}
