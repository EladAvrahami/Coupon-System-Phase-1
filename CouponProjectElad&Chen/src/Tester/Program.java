package Tester;

import Exceptions.CouponsException;
import THREAD.CouponExpirationDailyJob;

import java.sql.SQLException;

/**
 * the main of the program CouponProject
 */

/**
 * @authors Elad Avrahami & Chen Amos
 */
public class Program {
    public static void main(String[] args) throws SQLException, CouponsException, InterruptedException {
        CouponExpirationDailyJob couponExpirationDailyJob = new CouponExpirationDailyJob();
        new Thread(couponExpirationDailyJob).start();
        Test.admintest();
    }
}
