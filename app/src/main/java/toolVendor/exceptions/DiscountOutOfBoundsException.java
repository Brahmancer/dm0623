package toolVendor.exceptions;

/**
 * Custom Exception to reprsent the scenario when the Discount rate is 
 * not within the range of [0.0, 100.0]
 */
public class DiscountOutOfBoundsException extends RuntimeException {
    public DiscountOutOfBoundsException(String errorMsgString){
        super(errorMsgString);
    }   
}