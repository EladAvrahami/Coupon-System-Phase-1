package DBDAO;


import Beans.Customer;
import Beans.Coupon;
import DB.ConnectionPool;
import DB.DBUtils;
import Dao.CostumersDAO;
import Enums.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this class contain all the customers methods that will work with connection to DB .
 */
public class CustomersDBDAO implements CostumersDAO {

    /**
     * SQL methods of customers
     */
    private final String ADD_CUSTOMER = "INSERT INTO `couponsSystem`.`customers` (`id`,`first_name`,`last_name`,`email`,`password`) VALUES (?,?,?,?,?);";
    private final String UPDATE_CUSTOMER = "UPDATE `couponsSystem`.`customers` SET first_name=?, last_name=? ,email=? , password=? WHERE id=?;";
    private final String DELETE_CUSTOMER = "DELETE FROM `couponsSystem`.`customers` WHERE `id` = ?;";
    private final String GET_ALL_CUSTOMERS = "SELECT * FROM `couponsSystem`.`customers`";
    private final String IS_CUSTOMER_EXISTS = "SELECT * FROM `couponsSystem`.`customers` where `email` = ? AND `password` = ?;";
    private final String IS_CUSTOMER_EXISTS_BY_EMAIL = "SELECT * FROM `couponsSystem`.`customers` WHERE `email` = ?;";
    private final String GET_ONE_CUSTOMER_BY_ID = "SELECT * FROM `couponsSystem`.`customers` WHERE `id` = ?;";
    private final String GET_ONE_CUSTOMER_BY_ID_AND_EMAIL = "SELECT * FROM `couponsSystem`.`customers` WHERE `id` = ? and `email` = ?;";
    private final String GET_ALL_COUPONS_FOR_COSTUMER = "SELECT * FROM `couponsSystem`.`coupons` INNER JOIN `couponsSystem`.`customers_vs_coupons` ON customers_vs_coupons.COUPON_ID = coupons.ID AND customers_vs_coupons.CUSTOMER_ID=?";
    private final String GET_COSTUMER_DETAILS = "SELECT * FROM `customers` WHERE customers.ID = ?;";
    private final String GET_ALL_COSTUMER_COUPONS_BY_MAX_PRICE = "SELECT * FROM `couponsSystem`.`coupons` INNER JOIN `couponsSystem`.`customers_vs_coupons` ON customers_vs_coupons.COUPON_ID = coupons.ID AND customers_vs_coupons.CUSTOMER_ID = ? AND coupons.PRICE <= ?;";


    /**
     * this method check if there is customer with the entered email and password by execute SQL query "IS_CUSTOMER_EXISTS"
     * @param email client email
     * @param password client password
     * @return boolean answer if there is customer with the entered email and password or answer that there isn't.
     */
    @Override
    public boolean isCostumerExists(String email, String password) {
        List<Customer> costumers = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_CUSTOMER_EXISTS);
            statement.setString(1, email);
            statement.setString(2, password);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer costumer = new Customer(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
                costumers.add(costumer);
                if (costumers.iterator().hasNext()) {

                    System.out.println("the costumer exists");
                    return true;
                }
                System.out.println("the costumer does not exist");
            }


        } catch (InterruptedException | SQLException err) {
            System.out.println("CostumersDBDAO.isCostumerExists " + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }


    /**
     * this method check if there is already a customer with the same email on DB by execute the SQL query  "IS_CUSTOMER_EXISTS_BY_EMAIL"
     * @param email customers email
     * @return boolean answer true if there is a customer with the same email already on DB .
     */
    public boolean isCostumerExistsByEmail(String email) {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_CUSTOMER_EXISTS_BY_EMAIL);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("costumer exists ");
                return true;
            } else {
                System.out.println("costumer is not on the list");
            }

        } catch (Exception err) {
            System.out.println("CostumersDBDAO.isCostumerExistsByEmail " + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;

    }

    /**
     *  this method check if there is already a customer with the same id on DB by execute the SQL query  "GET_ONE_CUSTOMER_BY_ID"
     * @param costumerID id of Customer obj
     * @return boolean answer true if there is a customer with the id email already on DB .
     */
    public boolean isCostumerExistsByID(int costumerID) {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_CUSTOMER_BY_ID);
            statement.setInt(1, costumerID);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                //System.out.println("costumer exists");
                return true;
            } else {
                System.out.println("costumer is not on the list");
            }

        } catch (Exception err) {
            System.out.println("CostumersDBDAO.isCostumerExistsByID " + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;

    }

    /**
     * this method check if customer already exist by id&mail,execute the SQL query "GET_ONE_CUSTOMER_BY_ID_AND_EMAIL"
     * @param customerID id of a Customer Obj
     * @param email mail of a Customer Obj
     * @return boolean answer true if it found a customer with the same email and password
     */
    public boolean iscustomerExistsByIDAndEmail(int customerID, String email) {
        List<Customer> costumers = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_CUSTOMER_BY_ID_AND_EMAIL);
            statement.setInt(1, customerID);
            statement.setString(2, email);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer costumer = new Customer(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
                costumers.add(costumer);
                if (costumers.iterator().hasNext()) {

                    //System.out.println("the customer exists");
                    return true;
                }
                System.out.println("the customer does not exist");
            }


        } catch (InterruptedException | SQLException err) {
            System.out.println("CostumersDBDAO.iscustomerExistsByIDAndEmail" + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }


    /**
     * this method  create a new customer with the details she gets to DB,by execute the SQL query  "ADD_CUSTOMER"
     * @param costumer new Customer obj
     * @return boolean answer true and massage "costumer was added"  if its true
     * @throws SQLException
     */
    public boolean addCostumer(Customer costumer) throws SQLException {

        try {
            Map<Integer, Object> params = new HashMap<>();
            params.put(1, costumer.getId());
            params.put(2, costumer.getFirstName());
            params.put(3, costumer.getLastName());
            params.put(4, costumer.getEmail());
            params.put(5, costumer.getPassWord());
            DBUtils.runQuery(ADD_CUSTOMER, params);
            System.out.println("costumer was added successfully");
            return true;
        }catch (Exception err){
            System.out.println("CostumersDBDAO.addCostumer " + err.getMessage());
        }
        return false;
    }


    /**
     *this method update customers details by execute the SQL query  "UPDATE_CUSTOMER"
     * @param customer Customer Obj we want to update
     * @return boolean answer true if the customer update successfully
     */
    public boolean updateCostumer(Customer customer) {
        Connection connection = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_CUSTOMER);
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPassWord());
            statement.setInt(5, customer.getId());


            statement.execute();
            //System.out.println("customer was updated successfully");
        } catch (Exception err) {
            System.out.println("CostumersDBDAO.updateCostumer " + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }

    /**
     * this method execute SQL query "DELETE_CUSTOMER", and by customer id delete a customer from DB
     * @param costumerID id of the customer we want to delete
     * @return boolean answer true if the customer deleted from DB and print "deleted successfully "
     */
    @Override
    public boolean deleteCostumer(int costumerID) {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_CUSTOMER);
            statement.setInt(1, costumerID);
            statement.execute();
            System.out.println("the costomer number " + costumerID + " was deleted successfully and all the coupons he bought also deleted");
        } catch (Exception err) {
            System.out.println("CostumersDBDAO.deleteCostumer " + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }

    /**
     * this method return all customers details from DB by asking parameters from DB with SQL method "GET_ALL_CUSTOMERS"
     * @return all the customers and their details.
     */
    @Override
    public ArrayList<Customer> getAllCostumers() {
        ArrayList<Customer> costumers = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_CUSTOMERS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer costumer = new Customer(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
                costumers.add(costumer);
            }
            for (Customer item : costumers) {
                System.out.println(item);
            }
        } catch (InterruptedException | SQLException err) {
            System.out.println("CostumersDBDAO.getAllCostumers " + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return costumers;
    }

    /**
     * this boolean method check- if there is coupon with the same id on DB execute SQL query "GET_ONE_CUSTOMER_BY_ID".
     * @param costumerID  id of the customer
     * @return boolean answer true if there is customer with the same id on DB
     */
    @Override
    public Customer getOneCostumer(int costumerID) {
        Customer costumer = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_CUSTOMER_BY_ID);
            statement.setInt(1, costumerID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                costumer = new Customer(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
                System.out.println(costumer);
            }

        } catch (InterruptedException | SQLException err) {
            System.out.println("CostumersDBDAO.getOneCostumer " + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return costumer;

    }



        /**
         * this method get customer id and execute the SQL query "GET_ONE_CUSTOMER_BY_ID" to show all his details .
         */

    public Customer getCostumerDetails(int customerID) {
        Connection connection = null;
        Customer customer = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_CUSTOMER_BY_ID);
            statement.setInt(1,customerID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                 customer = new Customer(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
                System.out.println(customer);
                return customer;
            }

        } catch (InterruptedException | SQLException err) {
            System.out.println("CouponDBDAO.getCostumerDetails:" + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
         return customer;

    }


    /**
     * this method gets cusomer id and max price and execute the SQL query "GET_ALL_COSTUMER_COUPONS_BY_MAX_PRICE"
     * @param customerID customer id
     * @param maxPrice maximum price for showing coupons
     * @return a list of all the coupons details that the customer purchase below the price we enter
     */
    public ArrayList<Coupon> getAllCustomersCouponsByMaxPrice(int customerID, double maxPrice) {
        List<Coupon> coupons = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COSTUMER_COUPONS_BY_MAX_PRICE);
            statement.setInt(1, customerID);
            statement.setDouble(2, maxPrice);
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
            return (ArrayList<Coupon>) coupons;
        } catch (InterruptedException | SQLException err) {
            System.out.println("CouponDBDAO.getAllCustomersCouponsByMaxPrice:" + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return (ArrayList<Coupon>) coupons;
    }


}
