package THREAD;

import Beans.Coupon;
import DB.ConnectionPool;
import DBDAO.CouponDBDAO;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * this class will contain the thread of our program
 */
public class CouponExpirationDailyJob implements Runnable {

    private final CouponDBDAO couponDBDAO = new CouponDBDAO();

    /**
     * Default Con
     */
    public CouponExpirationDailyJob() {
    }

    /**
     * this thread run over a list of coupons and check if endDate was expired
     */
    public void run() {
        //set time of job
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 1);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        //every night at 1am you run your task
        Timer timer = new Timer();
        //period : 1 day
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    System.out.println("thread working********************************************");//check if thread work on screen
                    //Works once a day
                    //TimeUnit.DAYS.sleep(1);
                    Thread.sleep(1000*60);
                    List<Coupon> couponList = couponDBDAO.getAllCoupons();

                    for (Coupon item : couponList) {
                        java.util.Date currentDate = new Date(); //new Date(new java.util.Date().getTime())
                        //checks if the coupon is expired
                        if (currentDate.after(item.getEndDate())) {
                            //deletes the purchase history of the coupon in the sql table and deletes the coupon from the sql table
                            couponDBDAO.deleteCoupon(item.getId());

                            System.out.println("Coupon was expired and deleted from DB : " + item.getTitle());
                        }
                        TimeUnit.SECONDS.sleep(1);
                    }
                } catch (InterruptedException e) {
                    System.out.println(ConnectionPool.class.getName() + " " + ConnectionPool.class.getEnclosingMethod().getName() + ":\n" + e.getMessage());
                }
            }
        }, today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES));

        //Starts the daily job
        //CouponExpirationDailyJob couponExpirationDailyJob = new CouponExpirationDailyJob(false);
        //new Thread(couponExpirationDailyJob).start();
    }


}
