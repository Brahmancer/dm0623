package toolVendor.exceptions;

public class DiscountOutOfBoundsException extends RuntimeException {
    public DiscountOutOfBoundsException(String errorMsgString){
        super(errorMsgString);
    }   
}