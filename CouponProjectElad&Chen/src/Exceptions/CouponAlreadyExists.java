package Exceptions;

public class CouponAlreadyExists extends Exception{

    /**
     * Exception that we will send as massage every time that the coupon already exists somewhere
     */
    public CouponAlreadyExists() {
        super("you cannot insert this coupon twice since it has already been purchased");

    }


}

