package Tester;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import DB.DatabaseManager;
import DBDAO.CompaniesDBDAO;
import DBDAO.CouponDBDAO;
import Enums.Category;
import Enums.ClientType;
import Exceptions.CouponsException;
import FACADE.AdminFacade;
import FACADE.CompanyFacade;
import FACADE.CustomerFacade;
import FACADE.LoginManager;
import util.MyUtil;

import java.awt.dnd.InvalidDnDOperationException;
import java.sql.SQLException;

/**
 * on this class we will test all our methods that there is on our program and their exceptions.
 */
public class Test {
    // /**
    //* @param args
    //* @throws CouponsException
    //*/


    //var cp = ConnectionPool.getInstance();
    public static void admintest() throws SQLException, InterruptedException, CouponsException {
        System.out.println("***********************ADMIN FACADE TEST *******************************");
        //ADMIN FACADE TEST *******************************

        DatabaseManager.constructDB();//create new couponSystem DB

        AdminFacade adminFacade;
        //DUMMY  OBJECTS (COMPANY, CUSTOMER AND A COUPON)
        Company company = new Company(3, "Elad and Chen LTD", "E&C@gmail.com", "ungeussable");
        Coupon coupon = new Coupon(3, Category.movies, "the best coupon", "you can find anything here", Coupon.getDate(2022, 12, 1), Coupon.getDate(2022, 12, 2), 3, 4, "perfect");
        Coupon coupon1 = new Coupon(3, Category.clothes, "the very best", "just try it", Coupon.getDate(2022, 12, 1), Coupon.getDate(2022, 12, 4), 6, 127, "image");
        Coupon coupon2 = new Coupon(3, Category.flights, "i am the best coupon", "example for thread", Coupon.getDate(2020, 12, 1), Coupon.getDate(2020, 12, 4), 8, 56.0, "image33");
        Customer costumer = new Customer(25, "elad", "avrahami", "elad@gmail.com", "cantguess");
        Customer customer1 = new Customer(27, "chen", "amos", "chen@gmail.com", "amos123");

        //LOGIN SUCCESSFUL
        MyUtil.printRow();
        System.out.println("this is a demonstration of a successful login of an admin");
        adminFacade = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.administrator);
        MyUtil.printRow();

        //LOGIN FAILED
        System.out.println("this is a demonstration of a non! successful login of an admin");
        try {
            adminFacade = (AdminFacade) LoginManager.getInstance().login("admin@afdmin.com", "fadmin", ClientType.administrator);
        } catch (InvalidDnDOperationException err) {
            System.out.println("as you can see zeev the login has failed");
        }
        MyUtil.printRow();

        //ADD NEW COMPANY SUCCESSFUL
        System.out.println("this is a demonstration of an admin adding new company");
        //i deleted the company in case you run the program twice it cleans the
        CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
        companiesDBDAO.deleteCompany(company.getId());
        adminFacade.addCompany(company);
        MyUtil.printRow();

        //ADD COMPANY - FAILED (IDENTICAL COMPANY NAME)
        System.out.println("this is a demonstration of an admin failed to add a new company because a company with this name already exists");
        adminFacade.addCompany(company);
        MyUtil.printRow();

        //ADD COMPANY - FAILED (IDENTICAL EMAIL)
        System.out.println("this is a demonstration of an admin failed to add a new company because a company with this email already exists");
        company.setName("new test name");
        adminFacade.addCompany(company);
        //setting back the name
        company.setName("Elad and Chen LTD");
        MyUtil.printRow();

        // COMPANY UPDATE - SUCCESSFUL
        System.out.println(" this is a demonstration of an admin updating a company's email and password - successfully");
        company.setEmail("test@walla.com");
        company.setPassword("test new password");
        adminFacade.upDateCompany(company);
        MyUtil.printRow();

        // COMPANY UPDATE -  NOT SUCCESSFUL (BY ID)
        System.out.println(" this is a demonstration of an admin trying to update a company's id -  not successfully");
        company.setId(6);
        adminFacade.upDateCompany(company);
        company.setId(3);
        MyUtil.printRow();
        System.out.println(" this is a demonstration of an admin trying to update a company's name -  not successfully");
        // COMPANY UPDATE -  NOT SUCCESSFUL (BY NAME)
        company.setName("name test");
        adminFacade.upDateCompany(company);
        MyUtil.printRow();

        //GET ALL COMPANIES - SUCCESSFUL
        System.out.println(" this is a demonstration of an admin presenting all of the companies successfully: ");
        adminFacade.getAllCompanies();
        MyUtil.printRow();

        //GET ONE COMPANY BY COMPANYID
        System.out.println(" this is a demonstration of an admin presenting one of the companies successfully: ");
        adminFacade.getOneCompany(company.getId());
        MyUtil.printRow();

        //DELETE COMPANY - SUCCESSFUL
        System.out.println(" this is a demonstration of an admin deleting a company successfully: ");
        adminFacade.deleteCompany(company.getId());
        MyUtil.printRow();

        //ADD ONE CUSTOMER - SUCCESSFUL
        System.out.println(" this is a demonstration of an admin adding a customer successfully: ");
        adminFacade.deleteCostumer(costumer.getId());
        adminFacade.addCostumer(costumer);
        adminFacade.addCostumer(customer1);
        MyUtil.printRow();

        //ADD ONE CUSTOMER -  NOT SUCCESSFUL (BY EMAIL)
        System.out.println(" this is a demonstration of an admin adding a customer with the same email not successfully: ");
        costumer.setId(11);
        adminFacade.addCostumer(costumer);
        costumer.setId(25);
        MyUtil.printRow();

        //UPDATE EXIST CUSTOMER - SUCCESSFUL
        System.out.println(" this is a demonstration of an admin updating a customer successfully: ");
        costumer.setFirstName("avishay");
        costumer.setLastName("cohen");
        adminFacade.updateCostumer(costumer);

        MyUtil.printRow();

        //UPDATE EXIST CUSTOMER - NOT SUCCESSFUL
        System.out.println(" this is a demonstration of an admin updating a customer  not successfully: ");
        costumer.setId(11);
        adminFacade.updateCostumer(costumer);
        costumer.setId(25);
        MyUtil.printRow();

        //GET ONE CUSTOMER (BY ID)
        System.out.println(" this is a demonstration of an admin presenting one customer successfully: ");
        adminFacade.getOneCostumer(costumer.getId());
        MyUtil.printRow();

        //GET ALL CUSTOMERS
        System.out.println(" this is a demonstration of an admin presenting all customers successfully: ");
        adminFacade.getAllCostumers();
        MyUtil.printRow();

        //DELETE ONE CUSTOMER - SUCCESSFUL
        System.out.println(" this is a demonstration of an admin deleting a customer  successfully: ");
        adminFacade.deleteCostumer(costumer.getId());
        MyUtil.printRow();


        //***********************************************************************************************//


        //COMPANY FACADE TEST------
        System.out.println("***********************COMPANY FACADE TEST *******************************");
        //LOGIN SUCCESSFUL
        adminFacade.addCompany(company);
        CompanyFacade companyFacade;
        System.out.println("this is a demonstration of a successful login of a company");
        System.out.println(company);
        companyFacade = (CompanyFacade) LoginManager.getInstance().login("test@walla.com", "test new password", ClientType.company);

        MyUtil.printRow();

        //CompanyFacade companyFacade = (CompanyFacade) LoginManager.getInstance().login(company.getEmail(), company.getPassword(), ClientType.company);

        //COMPANY LOGIN FAILED (BY PASSWORD)
        System.out.println("this is a demonstration of a  not successful login of an company - by password");
        try {
            companyFacade = (CompanyFacade) LoginManager.getInstance().login("UPDATED@gmail.com", "dfgfggvd", ClientType.company);
        } catch (InvalidDnDOperationException | CouponsException err) {
            System.out.println("you got it wrong - not correct email or password");
        }
        MyUtil.printRow();

        //COMPANY LOGIN FAILED (BY EMAIL)
        System.out.println("this is a demonstration of a  not successful login of an company - by email");
        try {
            companyFacade = (CompanyFacade) LoginManager.getInstance().login("wrong@gmail.com", "dfgvd", ClientType.company);
        } catch (InvalidDnDOperationException | CouponsException err) {
            System.out.println("you got it wrong - not correct email or password");
        }
        MyUtil.printRow();

        //ADD NEW COUPON SUCCESSFUL
        System.out.println("this is a demonstration of an company adding a new coupon");
        adminFacade.addCostumer(costumer);
        adminFacade.addCompany(company);
        companyFacade.addCoupon(coupon);
        companyFacade.addCoupon(coupon1);
        companyFacade.addCoupon(coupon2);
        MyUtil.printRow();

        //COUPON UPDATE SUCCESSFULLY
        System.out.println("this is a demonstration of an company updating a coupon successfully");
        coupon.setAmount(8);
        coupon.setPrice(55);
        companyFacade.updateCoupon(coupon);
        MyUtil.printRow();

        //COUPON UPDATE FAILED (COUPON_ID)
        System.out.println("this is a demonstration of an company updating a coupon id not successfully");
        CouponDBDAO couponDBDAO = new CouponDBDAO();
        coupon.setId(16);
        companyFacade.updateCoupon(coupon);
        coupon.setId(3);
        MyUtil.printRow();


        //COUPON UPDATE FAILED (COMPANY_ID)
        System.out.println("this is a demonstration of an company updating a coupon companyID not successfully");
        coupon.setCompanyID(5);
        companyFacade.updateCoupon(coupon);
        coupon.setCompanyID(3);
        MyUtil.printRow();

        //GET ALL COMPANY'S COUPONS
        System.out.println("this is a demonstration of an company presenting all company's coupons");
        companyFacade.getCompanyCoupons(companyFacade.getId());
        MyUtil.printRow();

        //GET ALL COMPANIES COUPONS BY CATEGORY
        System.out.println("this is a demonstration of an company presenting all company's coupons by category");
        System.out.println("these are the coupons for movies:");
        companyFacade.getCompanyCouponsByCategory(companyFacade.getId(), Category.movies);
        System.out.println("these are the coupons for clothes:");
        companyFacade.getCompanyCouponsByCategory(companyFacade.getId(), Category.clothes);
        System.out.println("these are the coupons for flights:");
        companyFacade.getCompanyCouponsByCategory(companyFacade.getId(), Category.flights);
        MyUtil.printRow();

        //GET ALL COMPANIES COUPONS BELOW MAX PRICE
        System.out.println("this is a demonstration of an company presenting all company's coupons below max price");
        System.out.println("these are the coupons that costs below 100nis");
        companyFacade.getCompanyCouponsByMaxPrice(companyFacade.getId(), 100);
        System.out.println("these are the coupons that costs below 150nis");
        companyFacade.getCompanyCouponsByMaxPrice(companyFacade.getId(), 150);
        MyUtil.printRow();

        //GET ALL COMPANY'S DETAILS BY ID
        System.out.println("this is a demonstration of a company details show");
        companyFacade.getCompanyDetails(companyFacade.getId());
        MyUtil.printRow();

        //DELETE COUPON (AND ALL OF HIS PURCHASES)
        System.out.println("this is a demonstration of an customer deleting a coupon successfully");
        System.out.println((couponDBDAO.getOneCouponByCoupon(coupon)));
        companyFacade.deleteCoupon((couponDBDAO.getOneCouponByCoupon(coupon)).getId());
        companyFacade.deleteCoupon((couponDBDAO.getOneCouponByCoupon(coupon1)).getId());

        MyUtil.printRow();


        //***********************************************************************************************//
        //CUSTOMER FACADE TEST --------
        System.out.println("***********************CUSTOMER FACADE TEST *******************************");

        //LOGIN SUCCESSFUL
        System.out.println("this is a demonstration of a successful login of a customer");
        System.out.println(costumer);
        CustomerFacade costumerFacade = (CustomerFacade) LoginManager.getInstance().login("elad@gmail.com", "cantguess", ClientType.costumer);
        MyUtil.printRow();

        //LOGIN  NOT SUCCESSFUL
        System.out.println("this is a demonstration of a not successful login of a customer");
        try {
            costumerFacade = (CustomerFacade) LoginManager.getInstance().login("wrong@gmail.com", "dfgvd", ClientType.costumer);
        } catch (InvalidDnDOperationException | CouponsException err) {
            System.out.println("you got it wrong - not correct email or password");
        }
        MyUtil.printRow();

        System.out.println("this is a demonstration of a customer purchasing a coupon");
        //COUPON PURCHASE - SUCCESSFUL
        companyFacade.addCoupon(coupon);
        companyFacade.addCoupon(coupon1);
        costumerFacade.purchaseCoupon((couponDBDAO.getOneCouponByCoupon(coupon)).getId(), costumerFacade.getCustomerID());
       // costumerFacade.purchaseCoupon((couponDBDAO.getOneCouponByCoupon(coupon1)).getId(), costumerFacade.getCustomerID());

        MyUtil.printRow();

        //COUPON PURCHASE -  NOT SUCCESSFUL (COUPON WAS ALREADY PURCHASED BY CUSTOMER)
        System.out.println("this is a demonstration of a customer not succeeding to purchase a coupon - already has the coupon");
        costumerFacade.purchaseCoupon((couponDBDAO.getOneCouponByCoupon(coupon)).getId(), costumerFacade.getCustomerID());
        MyUtil.printRow();

        //COUPON PURCHASE -  NOT SUCCESSFUL (NOT ENOUGH IN STOCK)
        System.out.println("this is a demonstration of a customer not succeeding to purchase a coupon - out of stock");
        coupon1.setAmount(0);
        companyFacade.updateCoupon(coupon1);
        costumerFacade.purchaseCoupon((couponDBDAO.getOneCouponByCoupon(coupon1)).getId(), costumerFacade.getCustomerID());
        MyUtil.printRow();

        //COUPON PURCHASE -  NOT SUCCESSFUL (THE COUPON HAS EXPIRED)
        System.out.println("this is a demonstration of a customer not succeeding to purchase a coupon - coupon has expired");
        //companyFacade.deleteCoupon((couponDBDAO.getOneCouponByCoupon(coupon)).getId());
        coupon1.setAmount(4);
        coupon1.setEndDate(Coupon.getDate(2020, 12, 4));
        companyFacade.updateCoupon(coupon1);
        costumerFacade.purchaseCoupon((couponDBDAO.getOneCouponByCoupon(coupon1)).getId(), costumerFacade.getCustomerID());

        MyUtil.printRow();


        //GET ALL THE COUPONS FOR THE CUSTOMER
        System.out.println("this is a demonstration of a customer's coupon list");
        coupon1.setEndDate(Coupon.getDate(2022, 12, 4));
        companyFacade.updateCoupon(coupon1);
        costumerFacade.purchaseCoupon((couponDBDAO.getOneCouponByCoupon(coupon1)).getId(), costumerFacade.getCustomerID());
        costumerFacade.getCostumerCoupons(costumerFacade.getCustomerID());
        MyUtil.printRow();

        //GET ALL THE COUPONS FOR THE CUSTOMER BY CATEGORY

        System.out.println("this is a demonstration of a customer's coupon list by category");
        System.out.println("BY CLOTHES:");
        costumerFacade.getCostumerCouponsByCategory(Category.clothes,costumerFacade.getCustomerID());
        System.out.println("BY MOVIES");
        costumerFacade.getCostumerCouponsByCategory(Category.movies, costumerFacade.getCustomerID());
        MyUtil.printRow();

        //GET ALL THE COUPONS FOR THE CUSTOMER BY MAX PRICE
        System.out.println("these are the coupons that costs below 100nis");
        costumerFacade.getCostumerCouponsByMaxPrice(costumerFacade.getCustomerID(), 100);
        System.out.println("these are the coupons that costs below 150nis");
        costumerFacade.getCostumerCouponsByMaxPrice(costumerFacade.getCustomerID(), 150);
        MyUtil.printRow();

        //GET DETAILS FOR COSTUMER
        System.out.println("this is a demonstration of a getting the customer's details");
        costumerFacade.getCostumerDetails(costumer);
        MyUtil.printRow();

        //DELETE COUPON PURCHASE
        System.out.println("this is a demonstration of a customer deleting a coupon purchase");
        costumerFacade.deletePurchaseCoupon((couponDBDAO.getOneCouponByCoupon(coupon)).getId(), costumerFacade.getCustomerID());
        costumerFacade.deletePurchaseCoupon((couponDBDAO.getOneCouponByCoupon(coupon1)).getId(), costumerFacade.getCustomerID());
        MyUtil.printRow();

        System.out.println(couponDBDAO.getOneCouponByCoupon(coupon1).getTitle());
    }
}





















