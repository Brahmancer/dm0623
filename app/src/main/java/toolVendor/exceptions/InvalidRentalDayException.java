package toolVendor.exceptions;

/**
 * Custom Exception to reprsent the scenario when the Rental Days passed in is <= 0
 */
public class InvalidRentalDayException extends RuntimeException {
    public InvalidRentalDayException(String errorMsgString){
        super(errorMsgString);
    }   
}
