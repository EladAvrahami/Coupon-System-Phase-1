package Exceptions;

public class CouponsException extends Throwable {
    /**
     * Exception that we will send as massage every time we get wrong values
     */
    public CouponsException()
    {
        super("\"no no no no  ! \n you need to enter the right details - email and password\"") ;
    }
}
