package FACADE;

import Beans.Company;
import Beans.Customer;
import DBDAO.CompaniesDBDAO;
import DBDAO.CustomersDBDAO;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

/**
 * On this class will be all the admin methods
 */
public class AdminFacade extends ClientFacade {
    /**
     * password and email for login as admin
     */
    String password = "admin";
    String email = "admin@admin.com";

    /**
     * Default Con
     */
    public AdminFacade() {
    }


    /**
     * this method check if admin mail and password are correct before enter as client Admin
     * @param email of the admin
     * @param password of the admin
     * @return boolean answer /*true or false is the password and email correct to the ones we define manually.
     */
    //check if administrator enter detail's are correct
    @Override
    public boolean login(String email, String password) {

        if (this.email.equals(email) && this.password.equals(password)) {
            System.out.println("welcome dear administrator");
            return true;

        }
        System.out.println("oh oh you got it wrong please type your admin email and password");
        return false;
    }


    /**
     * this method add a new company to our coupons System if it not already exist
     * @param company is a new object that we going create and add to our list of companies
     * @return answer if the company already exists and add company if don't.
     * @throws SQLIntegrityConstraintViolationException
     */
    //check if company exist by search if id password and mail already on the DB on single row of company.
    public void addCompany(Company company) throws SQLIntegrityConstraintViolationException {
        CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
        if (!companiesDBDAO.isCompanyExistsByCompany(company)) {
            companiesDBDAO.addCompany(company);
        }

    }

    /**
     * this method update company details
     * @param company object of Company that we want to update
     * @throws SQLException
     * @throws InterruptedException
     */
    public void upDateCompany(Company company) throws SQLException, InterruptedException {
        CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
        //checks if the email was changed
        if (companiesDBDAO.isCompanyExistsById(company.getId())) {
                if (companiesDBDAO.isCompanyExistsByIDAndName(company.getId(), company.getName())) {
                    companiesDBDAO.updateCompany(company);
                } else {
                    System.out.println("cannot update company's name and ID only the email and password");
                }
           }

                //checks if the name was changed
        if (companiesDBDAO.isCompanyExistsByName(company.getName())) {
            if (companiesDBDAO.isCompanyExistsByIDAndName(company.getId(), company.getName())) {
                companiesDBDAO.updateCompany(company);
            } else {
                System.out.println("cannot update company's name and ID only the email and password");
            }
        }
    }



    /**
     *  this method delete company by id and show massage when it does.
     * @param companyID the id of specific company
     */
    public void deleteCompany(int companyID) {
        CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
        companiesDBDAO.deleteCompany(companyID);
        if (companiesDBDAO.deleteCompany(companyID)) {
            System.out.println("company number " + companyID + " was deleted and all the coupons it sold was deleted also");
        }
    }


    /**
     * this method show all the companies that on DB
     * @return all the companies and their details that there is on DB .
     */
    public ArrayList<Company> getAllCompanies() {
        CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
        companiesDBDAO.getAllCompanies();
        return null;
    }



    /**
     * this method get company by specific id.
     * @param companyID id of an the Company we want to return.
     * @return all the detail of a specific company.
     */
    public Company getOneCompany(int companyID) {
        Company company = null;
        CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
        companiesDBDAO.getOneCompany(companyID);

        return company;
    }


    /**
     * this method add a new customer after checking if mail exist on DB
     * @param customer an object of type Customer we want to return.
     * @return boolean answer true if the customer already exists,or false and add customer to the Customer Obj list .
     * @throws InterruptedException
     * @throws SQLException
     */
    public boolean addCostumer(Customer customer) throws InterruptedException, SQLException {
        CustomersDBDAO costumersDBDAO = new CustomersDBDAO();
        if (!costumersDBDAO.isCostumerExistsByEmail(customer.getEmail())) {
            costumersDBDAO.addCostumer(customer);
            return true;
        }else {
            System.out.println("cannot add a costumer with the same email");

        }
        return false;
    }



    /**
     * update specific Customer obj parameters after check if there is already identical one with the same email or id .
     * @param customer the obj that we want to update his parameters
     * @return the customer with the update parameters.
     * @throws SQLException
     */
    public Customer updateCostumer(Customer customer) throws SQLException {
        CustomersDBDAO costumersDBDAO = new CustomersDBDAO();
        if (costumersDBDAO.isCostumerExistsByEmail(customer.getEmail())) {
            if (costumersDBDAO.iscustomerExistsByIDAndEmail(customer.getId(), customer.getEmail())) {
                costumersDBDAO.updateCostumer(customer);
                System.out.println("the customer was updated successfully");
            } else {
                System.out.println("cannot update customer's  ID and Email only the name");

            }

        }if (costumersDBDAO.isCostumerExistsByID(customer.getId())) {
            if (costumersDBDAO.iscustomerExistsByIDAndEmail(customer.getId(), customer.getEmail())) {
                costumersDBDAO.updateCostumer(customer);
                //System.out.println("the customer was updated successfully");
            } else {
                System.out.println("cannot update customer's  ID and Email only the name");
            }
        }
        return customer;
    }


    /**
     * this method delete costumer by his id after checking if his id exist.
     * @param costumerID the id of specific customer.
     * @return boolean answer if the customer was deleted or not.
     */
    public boolean deleteCostumer(int costumerID) {
        CustomersDBDAO costumersDBDAO = new CustomersDBDAO();
        if (costumersDBDAO.isCostumerExistsByID(costumerID)) {
            costumersDBDAO.deleteCostumer(costumerID);
            return true;
        }
        return false;
    }


    /**
     * this method show all the customers that on DB
     * @return all the customers and their details from DB .
     */
    public ArrayList<Customer> getAllCostumers() {
        CustomersDBDAO costumersDBDAO = new CustomersDBDAO();
        costumersDBDAO.getAllCostumers();
        return null;
    }


    /**
     * this method show customer details by getting specific id.
     * @param costumerID id of specific customer
     * @return all the detail of a specific company.
     */
    public Customer getOneCostumer(int costumerID) {
        CustomersDBDAO costumersDBDAO = new CustomersDBDAO();
        costumersDBDAO.getOneCostumer(costumerID);
        Customer costumer = null;
        return costumer;
    }


}
