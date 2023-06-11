package toolVendor.exceptions;

public class InvalidRentalDayException extends RuntimeException {
    public InvalidRentalDayException(String errorMsgString){
        super(errorMsgString);
    }   
}
