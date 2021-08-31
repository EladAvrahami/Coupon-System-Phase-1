package FACADE;

import Beans.Company;
import Beans.Coupon;
import DBDAO.CompaniesDBDAO;
import DBDAO.CouponDBDAO;
import Enums.Category;
import Exceptions.CouponsException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyFacade extends ClientFacade {

    /**
     * Company SQL queries
     */
    private int id;
    private final String isCouponExists = "SELECT * FROM couponsSystem`.`coupons` WHERE `company_id` = ?;";
    private final String CHECK_IF_COUPON_EXISTS_FOR_COMPANY = "Select * from `couponsSystem`.`coupons`  where `title` = ? and `company_id` = ? ";
    private final String GET_COMPANY_COUPONS = "Select * from `couponsSystem`.`coupons`  where `title` = ? and `company_id` = ? ;";
    private static final String UPDATE_COMPANY = "UPDATE `couponsSystem`.`companies` SET id=?, name=? ,email=? , password=?";
    private final String GET_COMPANY_COUPONS_BY_MAX_PRICE = "Select * from `couponsSystem`.`coupons`  where `company_id` = ? and `price` < ? ;";

    public int getId() {
        return id;
    }

    /**
     * Con to get Company id to our methods
     * @param id Company id
     */
    public CompanyFacade(int id) {
        this.id = id;
    }



    /**
     * default Con
     */
    public CompanyFacade() {

    }


    /**
     * this method check if company mail and password are correct before enter as client Company
     * @param email company email
     * @param password company password
     * @return boolean answer if company password and email are correct to any row of client type company on DB
     * @throws CouponsException
     */
    @Override
    public boolean login(String email, String password) throws CouponsException {
        CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
        if (companiesDBDAO.isCompanyExists(email, password)) {
            System.out.println("welcome dear company you are logged in");
            ClientFacade companyFacade = new CompanyFacade();

            return true;
        }
        throw new CouponsException();
    }


    /**
     * This method add new coupon to specific company- after check if there isn't already same coupon with title and id on DB
     * @param coupon new Coupon Obj that we going to create and add to our coupons list.
     * @throws SQLException
     */
    public void addCoupon(Coupon coupon) throws SQLException {

        try {

            CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
            if (!companiesDBDAO.getOneCouponByTitle(coupon.getCompanyID(), coupon.getTitle())) {
                CouponDBDAO couponDBDAO = new CouponDBDAO();
                couponDBDAO.addCoupon(coupon);
                System.out.println("the coupon was added successfully");
            }
        } catch (SQLException e) {
            System.out.println("CompanyFacade.addCoupon " + e.getMessage());
        }
    }


    /**
     * update coupon by coupon id
     * @param coupon the specific coupon we want to update
     * @throws SQLException
     */
    public void updateCoupon(Coupon coupon) throws SQLException {
        CouponDBDAO couponDBDAO = new CouponDBDAO();

        if (couponDBDAO.isCouponExistsByIDAndCompany_ID((couponDBDAO.getOneCouponByCoupon(coupon)).getId(), coupon.getCompanyID())) {
            if (couponDBDAO.getOneCouponByCoupon(coupon).getCompanyID() == coupon.getCompanyID()) {
                //if ((couponDBDAO.getOneCouponByCoupon(coupon)).getId() == coupon.getId()) {
                    couponDBDAO.updateCoupon(coupon);
                    System.out.println("the coupon was updated successfully");
                } else {
                    System.out.println("the coupon was updated successfully only if the code of the coupon is correct please check");
                }
            } else {
                System.out.println("cannot update coupon's  ID and COMPANY_ID ");
            }
        //}

    }


    /**
     * delete coupon by his id .
     * @param couponId specific coupon id.
     */
    public void deleteCoupon(int couponId) {
        CouponDBDAO couponDBDAO = new CouponDBDAO();
        couponDBDAO.deleteCoupon(couponId);
    }


    /**
     * getting all coupons by specific company id
     * @param companyID id of Company Obj
     */
    public void getCompanyCoupons(int companyID) {
        CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
        companiesDBDAO.getAllCoupons(companyID);


    }


    /**
     * this method show us all the coupons of specific category that company have on DB
     * @param companyID the id of the company we want to check it coupons by category
     * @param category category number according to the list of categories on Class Category
     */
    public void getCompanyCouponsByCategory(int companyID, Category category) {
        CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
        companiesDBDAO.getAllCouponsByCategory(companyID, category);

    }


    /**
     * this method gets back all the coupon that their price is lower from the one was set on specific company
     * @param companyID specific company id
     * @param maxPrice the maximum price of coupon
     * @return all coupons that their price under maxPrice.
     */
    public ArrayList<Coupon> getCompanyCouponsByMaxPrice(int companyID, int maxPrice) {
        List<Coupon> coupons = new ArrayList<>();
        CouponDBDAO couponDBDAO = new CouponDBDAO();
        couponDBDAO.getAllCouponsByMaxPrice(companyID, maxPrice);

        return (ArrayList<Coupon>) coupons;
    }

    /**
     *this method get specific id of company and back all company details.
     * @param companyID  specific id of company Obj.
     * @return specific Obj of company and his parameters.
     */
    public Company getCompanyDetails(int companyID) {
        CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
        companiesDBDAO.getOneCompany(companyID);
        Company company = null;

        return company;
    }


}
