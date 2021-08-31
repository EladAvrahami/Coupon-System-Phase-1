package Dao;

import Beans.Coupon;
import Exceptions.CouponAlreadyExists;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * coupon interface
 */
public interface CouponDAO {

    public boolean addCoupon(Coupon coupon) throws SQLException;
    public boolean updateCoupon(Coupon coupon) throws SQLException;
    public boolean deleteCoupon(int couponId);
    public ArrayList<Coupon> getAllCoupons();
    public Coupon getOneCoupon(int couponID);
    public boolean addCouponPurchase(int costumerID, int couponID);
    public boolean deleteCouponPurchase(int costumerID, int couponID);

}
