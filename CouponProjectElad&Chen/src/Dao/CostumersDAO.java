package Dao;

import Beans.Customer;
import Exceptions.CouponAlreadyExists;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * customer interface
 */
public interface CostumersDAO {

    public boolean isCostumerExists (String email, String password) throws SQLException, InterruptedException, CouponAlreadyExists;
    public boolean addCostumer(Customer costumer) throws InterruptedException, SQLException;
    public boolean updateCostumer(Customer costumer) throws SQLException;
    public boolean deleteCostumer(int cotumerID);
    public ArrayList<Customer> getAllCostumers();
    public Customer getOneCostumer(int cotumerid);


}
