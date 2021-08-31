package FACADE;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import DB.ConnectionPool;
import Enums.Category;
import Enums.ClientType;
import Exceptions.CouponsException;

import java.awt.dnd.InvalidDnDOperationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class allow all the three types of client to login into the system
 */
public class LoginManager {

    /**
     * SQL queries
     */
    private final String EMAIL_AND_PASS_TO_COMPANYID = "SELECT * FROM `couponsSystem`.`companies` WHERE `email` = ? AND `password` = ?;";
    private final String EMAIL_AND_PASS_TO_CUSTOMERID = "SELECT * FROM `couponsSystem`.`customers` WHERE `email` = ? AND `password` = ?;";


    private static LoginManager instance = null;

    /**
     * Default Con
     */
    private LoginManager() {

    }


    /**
     * this method synchronized the instances
     *
     * @return instance to the login manager
     */
    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    synchronized (LoginManager.class) {
                        instance = new LoginManager();
                    }
                }
            }

        }
        return instance;
    }


    /**
     * this method get client type and reference to login method that checks if password &email correct for the relevant client type .
     *
     * @param email      client email
     * @param password   client password
     * @param clientType Enum that represent the client type
     * @return Obj Facade of a client, or null if the login details are wrong.
     * @throws InvalidDnDOperationException
     * @throws CouponsException
     */
    public ClientFacade login(String email, String password, ClientType clientType) throws InvalidDnDOperationException, CouponsException {
        String failedLoginMsg = "the email and password do not match any client";
        String loginSuccess = "you are now logged in as ";
        switch (clientType) {
            case administrator:
                ClientFacade adminFacade = new AdminFacade();
                if (!adminFacade.login(email, password)) {
                    throw new InvalidDnDOperationException(failedLoginMsg);
                }
                System.out.printf(loginSuccess + "'%s'\n", email);
                return adminFacade;


            case company:
                ClientFacade companyFacade = new CompanyFacade();
                if (!companyFacade.login(email, password)) {
                    throw new InvalidDnDOperationException(failedLoginMsg);
                }
                System.out.printf(loginSuccess + "'%s'\n", email);
                return new CompanyFacade(emailAndPassToID(email,password));

                 case costumer:
                ClientFacade costumerFacade = new CustomerFacade();
                if (!costumerFacade.login(email, password)) {
                    throw new InvalidDnDOperationException(failedLoginMsg);
                }
                System.out.printf(loginSuccess + "'%s'\n", email);
                return new CustomerFacade(emailAndPassToCustomerID(email,password));
        }
        return instance.login(email, password, clientType);
    }

    /**
     * this method gets id from the company with the same email and password on DB execute the SQL query "EMAIL_AND_PASS_TO_COMPANYID"
     * @param email company's email
     * @param password company's password
     * @return company id
     */
    public int emailAndPassToID(String email, String password) {
        Connection connection = null;
        int companyID = 0;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(EMAIL_AND_PASS_TO_COMPANYID);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Company company = new Company(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
                companyID = company.getId();
                return companyID;
            }

        } catch (InterruptedException | SQLException err) {
            System.out.println("CouponDBDAO.getOneCoupon:" + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return companyID;

    }


    /**
     *  this method gets id from the customer with the same email and password on DB execute the SQL query "EMAIL_AND_PASS_TO_CUSTOMERID"
     * @param email customer email
     * @param password customer password
     * @return customerID
     */
    public int emailAndPassToCustomerID(String email, String password) {
        Connection connection = null;
        int customerID = 0;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(EMAIL_AND_PASS_TO_CUSTOMERID);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
                customerID = customer.getId();

            }

        } catch (InterruptedException | SQLException err) {
            System.out.println("CouponDBDAO.getOneCoupon:" + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return customerID;

    }


}
