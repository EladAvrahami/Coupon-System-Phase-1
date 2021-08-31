package DBDAO;

import Beans.Company;

import Beans.Coupon;
import DB.ConnectionPool;
import DB.DBUtils;
import Dao.CompaniesDAO;
import Enums.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this class contain all the companies methods that will work with connection to DB .
 */
public class CompaniesDBDAO implements CompaniesDAO {

    /**
     * SQL methods of companies
     */
    private static Connection connection;
    private static final String ADD_COMPANY = "INSERT INTO `couponsSystem`.`companies` (`id`,`name`,`email`,`password`) VALUES (?,?,?,?) ;";
    private static final String UPDATE_COMPANY = "UPDATE `couponsSystem`.`companies` SET email=? , password=? WHERE `id` = ? and `name` = ? ";
    private final String DELETE_COMPANY = "DELETE FROM `couponsSystem`.`companies` WHERE `id` = ?;";
    private final String GET_ALL_COMPANIES = "SELECT * FROM `couponsSystem`.`companies`";
    private final String GET_ONE_COMPANY = "SELECT * FROM `couponsSystem`.`companies` WHERE `id` = ?;";
    private final String GET_ONE_COMPANY_BY_EMAIL_AND_PASS = "SELECT * FROM `couponsSystem`.`companies` WHERE `email` = ? and `password` = ?;";
    private final String GET_ONE_COMPANY_BY_ID_OR_EMAIL = "SELECT * FROM `couponsSystem`.`companies` WHERE `id` = ? or `email` = ? ;";
    private final String GET_ONE_COMPANY_BY_ID = "SELECT * FROM `couponsSystem`.`companies` WHERE `id` = ? ;";
    private final String GET_ONE_COMPANY_BY_NAME = "SELECT * FROM `couponsSystem`.`companies` WHERE `name` = ?;";
    private final String GET_ONE_COMPANY_BY_ID_AND_NAME = "SELECT * FROM `couponsSystem`.`companies` WHERE `id` = ? and `name` = ? ;";
    private final String CHECK_IF_COUPON_EXISTS_FOR_COMPANY = "Select * from `couponsSystem`.`coupons`  where  `company_id` = ? and `title` = ?";
    private final String GET_ALL_COUPONS_BY_COMPANY_ID = "Select * from `couponsSystem`.`coupons`  where `company_id` = ? ";
    private final String GET_ALL_COMPANY_COUPONS_BY_CATEGORY = "Select * from `couponsSystem`.`coupons`  where `category_id` = ? and `company_id` = ?";


    /**
     * this method check if there is company with the same password and email that we get from login method
     * on DB by execute SQL method "GET_ONE_COMPANY_BY_EMAIL_AND_PASS"
     * @param email the email of the company we wnat to login as
     * @param password the password of the company we wnat to login as
     * @return boolean answer if there is a company
     */
    public boolean isCompanyExists(String email, String password) {
        List<Company> companies = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_COMPANY_BY_EMAIL_AND_PASS);
            statement.setString(1, email);
            statement.setString(2, password);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Company company = new Company(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
                companies.add(company);
                if (companies.iterator().hasNext()) {

                    System.out.println("the company exists");
                    return true;
                }
                System.out.println("the company does not exist");
            }


        } catch (InterruptedException | SQLException err) {
            System.out.println("CompaniesDBDAO.isCompanyExists" + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }

    /**
     * this method check if there is a company with the same id and name by execute the SQL method "GET_ONE_COMPANY_BY_ID_AND_NAME"
     * @param companyId the id of the company Obj
     * @param name the name of the company Obj
     * @return boolean answer true if there is a company with the same id and name
     */
    public boolean isCompanyExistsByIDAndName(int companyId, String name) {
        List<Company> companies = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_COMPANY_BY_ID_AND_NAME);
            statement.setInt(1, companyId);
            statement.setString(2, name);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Company company = new Company(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
                companies.add(company);
                if (companies.iterator().hasNext()) {

                    //System.out.println("the company exists");
                    return true;
                }
                System.out.println("the company does not exist");
            }
            return false;


        } catch (InterruptedException | SQLException err) {
            System.out.println("CompaniesDBDAO.isCompanyExistsByIDAndName" + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }


    /**
     * this method check if there is a company on DB by id, execute the SQL query "GET_ONE_COMPANY_BY_ID"
     * @param companyID id of the company Obj
     * @return boolean answer true if there is the same value of company-ID on DB already.
     */
    public boolean isCompanyExistsById(int companyID) {
        List<Company> companies = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_COMPANY_BY_ID);
            statement.setInt(1, companyID);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Company company = new Company(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
                companies.add(company);
                if (companies.iterator().hasNext()) {

                    //System.out.println("the company exists");
                    return true;
                }
                System.out.println("the company does not exist");
            }


        } catch (InterruptedException | SQLException err) {
            System.out.println("CompaniesDBDAO.isCompanyExistsById" + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }

    /**
     * this method check if there is a company on DB by searching after equals email or id .
     * @param company the new company we want to add to the DB .
     * @return boolean answer if there is a company with the mail or password on DB by execute the SQL query "GET_ONE_COMPANY_BY_ID_OR_EMAIL"
     */
    public boolean isCompanyExistsByCompany(Company company) {
        List<Company> companies = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_COMPANY_BY_ID_OR_EMAIL);
            statement.setInt(1, company.getId());
            statement.setString(2, company.getEmail());
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Company company1 = new Company(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
                companies.add(company1);
                if (companies.isEmpty()) {
                    System.out.println("the company does not exist");
                    return false;
                }
                System.out.println("the company already exists you cannot add it one more time");
                return true;
            }
        } catch (InterruptedException | SQLException err) {
            System.out.println("CompaniesDBDAO.isCompanyExistsByCompany" + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }

    /**
     * this method check if there is company with the same name on DB by execute the SQL query "GET_ONE_COMPANY_BY_NAME"
     * @param companyName
     * @return boolean answer if there is company with the same name on DB
     */
    public boolean isCompanyExistsByName(String companyName) {
        List<Company> companies = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_COMPANY_BY_NAME);
            statement.setString(1, companyName);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Company company = new Company(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
                companies.add(company);
                if (companies.iterator().hasNext()) {

                    System.out.println("the company exists");
                    return true;
                }
                System.out.println("the company does not exist");
            }


        } catch (InterruptedException | SQLException err) {
            System.out.println("CompaniesDBDAO.isCompanyExistsByName" + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }


    /**
     * this method enter the company values to DB ,execute the SQL query "ADD_COMPANY"
     * @param company  new company Obj
     * @return new company Obj with the entered parameters
     * @throws SQLException
     */
    @Override
    public boolean addCompany(Company company) {
        try {
            //create an empty map, Int as key, object as value
            Map<Integer, Object> params = new HashMap<>();
            //our params....
            params.put(1, company.getId());
            params.put(2, company.getName());
            params.put(3, company.getEmail());
            params.put(4, company.getPassword());

            //run the query
            DBUtils.runQuery(ADD_COMPANY, params);
            System.out.println("the company was added successfully - thank you and enjoy!");
            return true;
        } catch (SQLException err) {
            System.out.println("CompaniesDBDAO.addCompany" + err.getMessage());
            return false;
        }
    }

    /**
     * this method update specific company parameters on DB by execute the SQL query "UPDATE_COMPANY"
     * @param company company we want to update its details
     * @return boolean answer true if the company with its new details updated.
     */
    @Override
    public boolean updateCompany(Company company) {
        try {

            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_COMPANY);
            statement.setString(1, company.getEmail());
            statement.setString(2, company.getPassword());
            statement.setInt(3, company.getId());
            statement.setString(4, company.getName());
            statement.execute();
            return true;

        } catch (Exception err) {
            System.out.println("CompaniesDBDAO.updateCompany" + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }


    /**
     * this method execute SQL method "DELETE_COMPANY", and by company id deletes a company from DB
     * @param companyID id of a company that we want to delete
     * @return boolean answer if the company deleted from DB
     */
    public boolean deleteCompany(int companyID) {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_COMPANY);
            statement.setInt(1, companyID);
            statement.execute();
            return true;

        } catch (Exception err) {
            System.out.println("CompaniesDBDAO.deleteCompany" + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }


    /**
     * this method return all companies details from DB by asking parameters from DB with SQL method "GET_ALL_COMPANIES"
     * @return all the companies and their details.
     */
    @Override
    public ArrayList<Company> getAllCompanies() {
        ArrayList<Company> companies = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COMPANIES);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Company company = new Company(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
                companies.add(company);
            }
            for (Company item : companies) {
                System.out.println(item);
            }
            System.out.println(companies);
        } catch (InterruptedException | SQLException err ){
            System.out.println("CompaniesDBDAO.getAllCompanies" + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return companies;

    }



    /**
     *this method return specific company details from DB by asking for companyId parameter end execute SQL query "GET_ONE_COMPANY"
     * @param companyId of a specific company that we want to show her details
     * @return specific company details.
     */
    @Override
    public Company getOneCompany(int companyId) {
        Company company = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_COMPANY);
            statement.setInt(1, companyId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                company = new Company(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
                System.out.println(company);
            }


        } catch (InterruptedException | SQLException e) {
            System.out.println("CompaniesDBDAO.getOneCompany " + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }

        return company;
    }


    /**
     * this boolean method check- if there is coupon with the same title&id on DB execute SQL query "CHECK_IF_COUPON_EXISTS_FOR_COMPANY".
     * @param company_id company id is the company that we want to add the coupon to its coupon list
     * @param title title is the name of the coupon.
     * @return boolean answer true if there is coupon with the same title&id on DB
     */
    public boolean getOneCouponByTitle(int company_id, String title) {
        List<Coupon> coupons = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(CHECK_IF_COUPON_EXISTS_FOR_COMPANY);
            statement.setInt(1, company_id);
            statement.setString(2, title);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = new Coupon(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        (Category) resultSet.getObject(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getDate(6),
                        resultSet.getDate(7),
                        resultSet.getInt(8),
                        resultSet.getDouble(9),
                        resultSet.getString(10)
                );
                coupons.add(coupon);
                if (resultSet.next()) {

                    System.out.println(coupon);
                    return true;
                }

            }
            System.out.println("there is no such coupon");


        } catch (InterruptedException | SQLException err) {
            System.out.println("CompaniesDBDAO.getOneCouponByTitle" + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }


    /**
     *this method gets all the coupons and of a specific company by execute SQL method "GET_ALL_COUPONS_BY_COMPANY_ID"
     * @param company_id the specific id of company
     * @return list all company coupons  and their parameters from DB .
     */
    public ArrayList<Coupon> getAllCoupons(int company_id) {
        ArrayList<Coupon> coupons = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPONS_BY_COMPANY_ID);
            statement.setInt(1, company_id);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = new Coupon(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        Category.getCategoryStringFromInt(resultSet.getInt(3)),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getDate(6),
                        resultSet.getDate(7),
                        resultSet.getInt(8),
                        resultSet.getDouble(9),
                        resultSet.getString(10)
                );
                coupons.add(coupon);
            }
            for (Coupon item : coupons) {
                System.out.println(item);
            }

        } catch (InterruptedException | SQLException err) {
            System.out.println("CompaniesDBDAO.getOneCompanyByNameAndEmail" + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return coupons;
    }

    /**
     * this method gets all company coupons by send specific category number and company id to DB query "GET_ALL_COMPANY_COUPONS_BY_CATEGORY".
     * @param company_id id fo the company we want to choose coupons from.
     * @param category the category num of coupons we want to see.
     * @return all coupons that have the same category on specific company and their details from DB .
     */
    public ArrayList<Coupon> getAllCouponsByCategory(int company_id, Category category) {
        ArrayList<Coupon> coupons = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COMPANY_COUPONS_BY_CATEGORY);
            statement.setInt(1, category.ordinal() + 1);
            statement.setInt(2, company_id);
            {

                statement.execute();
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Coupon coupon = new Coupon(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            Category.getCategoryStringFromInt(resultSet.getInt(3)),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getDate(6),
                            resultSet.getDate(7),
                            resultSet.getInt(8),
                            resultSet.getDouble(9),
                            resultSet.getString(10)
                    );
                    coupons.add(coupon);

                }
                for (Coupon item : coupons) {
                    System.out.println(item);
                }
            }
        } catch (InterruptedException | SQLException err) {
            System.out.println("CompaniesDBDAO.getAllCouponsByCategory " + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return coupons;
    }


}


