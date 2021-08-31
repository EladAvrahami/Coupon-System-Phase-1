package FACADE;

import Beans.Customer;
import Beans.Coupon;
import DBDAO.CustomersDBDAO;
import DBDAO.CouponDBDAO;
import Enums.Category;
import Exceptions.CouponsException;

import java.util.ArrayList;

/**
 * This class contain all the methods client Customer can do
 */
public class CustomerFacade extends ClientFacade {
    private int customerID;


    /**
     * Con to define customer id
     * @param customerID id of customer
     */
    public CustomerFacade(int customerID) {
        this.customerID = customerID;
    }

    /**
     * get the customer id of the customer that logged in.
     * @return customer id
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Default Con
     */
    public CustomerFacade() {

    }


    /**
     * login method check if there is customer with the entered email and password
     * @param email client email
     * @param password client password
     * @return boolean answer if there is customer with the entered email and password or not
     * @throws CouponsException
     */
    @Override
    public  boolean login(String email, String password) throws CouponsException {
        CustomersDBDAO CustomersDBDAO = new CustomersDBDAO();
        if (CustomersDBDAO.isCostumerExists(email,password)) {
            System.out.println("welcome dear customer you are logged in");
            return true;
        }
        throw new CouponsException();
    }


    /**
     * This method add coupon to customer coupon list ,print if coupon added successfully
     * @param couponID the coupon the customer purchase
     * @param costumerID customer that purchase the coupon
     */
    public void purchaseCoupon (int couponID, int costumerID){
        CouponDBDAO couponDBDAO = new CouponDBDAO();
        if(couponDBDAO.addCouponPurchase(costumerID, couponID) == true){
        System.out.println("the coupon " + couponID + " was purchased for costumer number " + costumerID);
            }else
        System.out.println("therefore the coupon was not purchased");
    }


    /**
     * this method delete specific coupon purchase for a specific customer
     * @param couponID specific coupon id
     * @param customerID specific customer id
     */
    public void deletePurchaseCoupon (int couponID, int customerID){
        try {
            CouponDBDAO couponDBDAO = new CouponDBDAO();
                if(couponDBDAO.deleteCouponPurchase(customerID,couponID)) {
                System.out.println("coupon number " + couponID + " was sold and deleted for customer " + customerID);
            }
        }catch (Exception err){
            System.out.println("CustomerFacade.deletePurchaseCoupon" + err.getMessage());
        }
    }


    /**
     * get all customer coupons by his id
     * @param costumerID id of specific customer
     * @return all customer coupons
     */
    public ArrayList<Coupon> getCostumerCoupons (int costumerID){
        CouponDBDAO couponDBDAO = new CouponDBDAO();
        couponDBDAO.getCostumersCouponsById(costumerID);
        return null;
    }


    /**
     * @param category Enum of coupons category's
     * @param costumerID id of specific customer
     * @return all the coupons that the customer have in specific category
     */
    public ArrayList<Coupon> getCostumerCouponsByCategory(Category category, int costumerID){
        CouponDBDAO couponDBDAO = new CouponDBDAO();
        couponDBDAO.getCostumersCouponsByCategory(category, costumerID);
        return null;}


    /**
     * get all the coupons that the customer purchase below the price we enter
     * @param customerID customer id
     * @param maxPrice maximum price for coupon
     * @return all the coupons that the customer purchase below the price we enter
     */
    public ArrayList<Coupon> getCostumerCouponsByMaxPrice(int customerID, double maxPrice){
        CustomersDBDAO CustomersDBDAO = new CustomersDBDAO();
        CustomersDBDAO.getAllCustomersCouponsByMaxPrice(customerID, maxPrice);
        return null;}

    /**

     * @param costumer the customer logged in
     * @return  all details about client from DB
     */
    public Customer getCostumerDetails(Customer costumer){
        CustomersDBDAO CustomersDBDAO = new CustomersDBDAO();
        CustomersDBDAO.getCostumerDetails(costumer.getId());
        return costumer;}


}

