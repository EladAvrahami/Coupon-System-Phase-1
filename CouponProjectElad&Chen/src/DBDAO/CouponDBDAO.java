package DBDAO;


import Beans.Coupon;
import DB.ConnectionPool;
import DB.DBUtils;
import Dao.CouponDAO;
import Enums.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * this class contain all the coupons methods that will work with connection to DB .
 */
public class CouponDBDAO implements CouponDAO {

    /**
     * SQL methods of coupons
     */
    private final String ADD_COUPON = "INSERT INTO `couponsSystem`.`coupons` (`company_id`,`category_id`,`title`,`description`,`start_date`, `end_date`, `amount`, `price`, `image`) VALUES (?,?,?,?,?,?,?,?,?);";
    private final String IS_COUPON_EXISTS_BY_ID = "SELECT * FROM `couponsSystem`.`coupons` WHERE `id` = ? ;";
    private final String IS_COUPON_EXISTS_BY_TITLE_AND_ID = "SELECT * FROM `couponsSystem`.`coupons` WHERE `title` = ? AND `id` = ? ;";
    private final String updateCoupon = "UPDATE `couponsSystem`.`coupons` SET company_id=?, category_id=? ,title=? , description=?, start_date=?, end_date=?, amount=?,price=?, image=?  WHERE id=?";
    private final String DELETE_COUPON = "DELETE FROM `couponsSystem`.`coupons` WHERE `id` = ?;";
    private final String GET_ALL_COUPONS = "SELECT * FROM `couponsSystem`.`coupons`;";
    private final String GET_ONE_COUPON = "SELECT * FROM `couponsSystem`.`coupons` WHERE `id` = ?;";
    private final String GET_ONE_COUPON_BY_ID_AND_COMPANY_ID = "SELECT * FROM `couponsSystem`.`coupons` WHERE `id` = ? AND `company_id` = ?;";
    private final String GET_ONE_COUPON_BY_TITLE_AND_COMPANYID = "SELECT * FROM `couponsSystem`.`coupons` WHERE `title` = ? AND `company_id` = ?;";

    private final String GET_COUPONS_FOR_COSTUMER = "SELECT COUPON_ID, COUNT(*) FROM `couponsSystem`.`customers_vs_coupons` WHERE CUSTOMER_ID = ?;";
    private final String IS_COUPON_EXISTS_FOR_COSTUMER = "SELECT * FROM `couponsSystem`.`customers_vs_coupons` WHERE `CUSTOMER_ID` = ? and `COUPON_ID` = ?;";
    private final String GET_COUPONS_FOR_COSTUMER_BY_ID = "SELECT * FROM `couponsSystem`.`coupons` INNER JOIN `couponsSystem`.`customers_vs_coupons` ON customers_vs_coupons.COUPON_ID = coupons.ID AND customers_vs_coupons.CUSTOMER_ID=?";
    private final String PURCHASE_COUPON = "INSERT INTO `couponsSystem`.`customers_vs_coupons`(`CUSTOMER_ID`, `COUPON_ID`) VALUES (?,?);";
    private final String UPDATE_AMOUNT = "UPDATE `couponsSystem`.`coupons` SET  amount= amount -1 WHERE id=?;";
    private final String GET_ALL_COUPON_VS_COSTUMERS = "SELECT * FROM `couponsSystem`.`customers_vs_coupons` WHERE `CUSTOMER_ID` = ?;";
    private final String DELETE_COSTUMER_PURCHASE = "DELETE FROM `couponsSystem`.`customers_vs_coupons` WHERE `CUSTOMER_ID` = ? and `COUPON_ID` = ?;";
    private final String GET_COUPONS_FOR_COSTUMER_BY_CATEGORY = "SELECT * FROM `couponsSystem`.`coupons` INNER JOIN `couponsSystem`.`customers_vs_coupons` ON customers_vs_coupons.COUPON_ID = coupons.ID and CATEGORY_ID = ? AND customers_vs_coupons.CUSTOMER_ID= ?;";
    private final String GET_ALL_COMPANY_COUPONS_BY_MAX_PRICE = "SELECT * FROM `couponsSystem`.`coupons` WHERE coupons.COMPANY_ID = ? AND coupons.PRICE <= ?;";



    /**
     * this method check if customer already purchase a coupon
     * @param couponID coupon id
     * @param costumerID customer id
     * @return boolean answer true if there is a customer that already purchase a coupon
     */
    public boolean isCouponExistsForCostumer(int couponID, int costumerID) {
        List<Integer> coupons = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_COUPON_EXISTS_FOR_COSTUMER);
            statement.setInt(1, costumerID);
            statement.setInt(2, couponID);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                coupons.add(resultSet.getInt(1));
                coupons.add(resultSet.getInt(2));
                if (coupons.isEmpty()) {
                }
                System.out.println("the coupon exist for the costumer.");
                return true;
            }
            System.out.println("the coupon was not purchased by the customer");
            return false;


        } catch (InterruptedException | SQLException err) {
            System.out.println("CouponDBDAO.isCouponExistsForCostumer:" + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }

    /**
     * this method check if specific coupon exists in specific company by execute the SQL query "GET_ONE_COUPON_BY_ID_AND_COMPANY_ID"
     * @param couponID specific coupon id
     * @param companyId specific company id
     * @return boolean answer true if the specific coupon belongs to specific company on DB .
     */
    public boolean isCouponExistsByIDAndCompany_ID(int couponID, int companyId) {
        List<Coupon> coupons = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_COUPON_BY_ID_AND_COMPANY_ID);
            statement.setInt(1, couponID);
            statement.setInt(2, companyId);
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
                if (coupons.iterator().hasNext()) {

                    System.out.println("the coupon exists");
                    return true;
                }
                System.out.println("the coupon does not exist");
            }


        } catch (InterruptedException | SQLException e) {
            System.out.println("CompaniesDBDAO.isCouponExistsByIDAndCompany_ID" + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }

    /**
     * this method enter the coupons values to DB ,execute the SQL query "ADD_COUPON"
     * @param coupon the new coupon Obj
     * @return new coupon Obj with the entered parameters
     * @throws SQLException
     */
    public boolean addCoupon(Coupon coupon) throws SQLException {
        try {
            //create an empty map, Int as key, object as value
            Map<Integer, Object> params = new HashMap<>();
            //our params....
            params.put(1, coupon.getCompanyID());
            params.put(2, coupon.getCategory().ordinal() + 1);
            params.put(3, coupon.getTitle());
            params.put(4, coupon.getDescription());
            params.put(5, coupon.getStartDate());
            params.put(6, coupon.getEndDate());
            params.put(7, coupon.getAmount());
            params.put(8, coupon.getPrice());
            params.put(9, coupon.getImage());
            //run the query
            DBUtils.runQuery(ADD_COUPON, params);
            return true;
        } catch (SQLException err) {
            System.out.println("CouponDBDAO.addCoupon:" + err.getMessage());
            return false;
        }
    }


    /**
     * get input of new coupon value to update parameters of coupon Obj on DB, if there is such id - "UPDATE_COUPON".
     * @param coupon the Coupon Obj we want to update
     * @return boolean answer-if true update details. if false
     * @throws SQLException
     */
    @Override
    public boolean updateCoupon(Coupon coupon) throws SQLException {

        try {


            CouponDBDAO couponDBDAO = new CouponDBDAO();
            Map<Integer, Object> params = new HashMap<>();
            //our params....
            params.put(1, coupon.getCompanyID());
            params.put(2, coupon.getCategory().ordinal() + 1);
            params.put(3, coupon.getTitle());
            params.put(4, coupon.getDescription());
            params.put(5, coupon.getStartDate());
            params.put(6, coupon.getEndDate());
            params.put(7, coupon.getAmount());
            params.put(8, coupon.getPrice());
            params.put(9, coupon.getImage());
            params.put(10, (couponDBDAO.getOneCouponByCoupon(coupon)).getId());
            //run the query
            DBUtils.runQuery(updateCoupon, params);
            return true;
        }catch (Exception err){
            System.out.println("CouponDBDAO.updateCoupon:" + err.getMessage());
        }return false;

    }

    /**
     * delete coupon by his id , execute SQL query "DELETE_COUPON"
     * @param couponId specific coupon id
     * @return boolean answer if coupon was deleted from company and buyers or not
     */
    @Override
    public boolean deleteCoupon(int couponId) {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_COUPON);
            statement.setInt(1, couponId);
            statement.execute();
            System.out.println("coupon id " + couponId + " was deleted and whoever purchased it doesn't have it anymore");
        } catch (SQLException | InterruptedException err) {
            System.out.println("CouponDBDAO.deleteCoupon:" + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }


    /**
     * This gets back coupons details by coupon id ,execute the SQL query "GET_ONE_COUPON"
     * @param couponID
     * @return
     */
    @Override
    public Coupon getOneCoupon(int couponID) {
        Connection connection = null;
        Coupon coupon = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_COUPON);
            statement.setInt(1, couponID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                coupon = new Coupon(
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

            }

        } catch (InterruptedException | SQLException err) {
            System.out.println("CouponDBDAO.getOneCoupon:" + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return coupon;
    }


    /**
     * this method check if there is a coupon with the same title on a specific company ,by execute the SQL query "GET_ONE_COUPON_BY_TITLE_AND_COMPANYID"
     * @param coupon specific coupon Obj
     * @return boolean answer true if  there is a coupon with the same title on a specific company
     */
    public Coupon getOneCouponByCoupon(Coupon coupon) {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_COUPON_BY_TITLE_AND_COMPANYID);
            statement.setString(1, coupon.getTitle());
            statement.setInt(2, coupon.getCompanyID());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                coupon = new Coupon(
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
               System.out.println(coupon);

            }

        } catch (InterruptedException | SQLException err) {
            System.out.println("CouponDBDAO.getOneCoupon:" + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return coupon;
    }


    /**
     * check if the customer did not made the same purchase ,check if the coupon in stock,checks if a coupon is expired
     * if everything checks makes the purchase
     * remove one coupon from the coupon purchase amount
     * @param costumerID the customer that purchase the coupon
     * @param couponID coupon we would like to purchase
     */
    @Override
    public boolean addCouponPurchase(int costumerID, int couponID) {
        try {
            if (couponID <= 0)
                throw new Exception("there is no coupon id that answers this fields (title,description) please try different coupon");
            //gets all the customer purchases
            CouponDBDAO couponDBDAO = new CouponDBDAO();
            if (couponDBDAO.isCouponExistsForCostumer(couponID, costumerID)) {
                System.out.println("cannot purchase this coupon because customer already have this coupon");
                throw new Exception("cannot purchase this coupon because customer already have this coupon");

            }

            //checks if the coupon is in stock
            System.out.println("checking if the coupon is in stock");
            if ((couponDBDAO.getOneCoupon(couponID)).getAmount() <= 0) {
                System.out.println("cannot purchase this coupon because it has been out of stock");
                throw new Exception("cannot purchase this coupon because it has been out of stock ");
            }
            //checks if a coupon is expired
            System.out.println("checking if the coupon has not expired ");
            if (couponDBDAO.getOneCoupon(couponID).getEndDate().before(new Date())) {
                System.out.println("cannot purchase this coupon because it was expired! ");
                throw new Exception("cannot purchase this coupon because it was expired! ");
            }
            //if everything is ok purchase the coupon
            couponDBDAO.purchaseCoupon(costumerID, couponID);
            System.out.println("thank you for your purchase");
            //remove one coupon from the coupon purchase amount
            couponDBDAO.changeCouponAmountAfterPurchase(couponID);
            return true;


        } catch (Exception err) {
            System.out.println("CouponDBDAO.addCouponPurchase:" + err.getMessage());
        }
        return false;
    }


    /**
     * this method makes the actual purchase and adds the coupon to the related customer_vs_coupons
     * @param costumerID the id of the customer
     * @param couponId id of coupon
     * @return boolean answer true if coupon purchased successfully
     */
    public boolean purchaseCoupon(int costumerID, int couponId) {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(PURCHASE_COUPON);
            statement.setInt(1, costumerID);
            statement.setInt(2, couponId);
            statement.execute();
        } catch (SQLException | InterruptedException err) {
            System.out.println("CouponDBDAO.purchaseCoupon:" + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }


    /**
     * this method delete specific coupon purchase for of specific customer by ask for costumerID&couponID and execute the SQL query "DELETE_COSTUMER_PURCHASE"
     * @param costumerID costumer ID
     * @param couponID coupon ID
     * @return boolean answer true if there is specific coupon to specific customer
     */
    @Override
    public boolean deleteCouponPurchase(int costumerID, int couponID) {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_COSTUMER_PURCHASE);
            statement.setInt(1, costumerID);
            statement.setInt(2,couponID);
            statement.execute();
            return  true;
        } catch (SQLException | InterruptedException err) {
            System.out.println("CouponDBDAO.purchaseCoupon:" + err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }


    /**
     * this method let the Thread all the coupons Obj form DB by execute the SQL method "GET_ALL_COUPONS"
     * @return all the coupons on DB
     */
    public ArrayList<Coupon> getAllCoupons() {
        ArrayList<Coupon> coupons = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPONS);
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
        } catch (InterruptedException | SQLException e) {
            System.out.println("CouponDBDAO.getAllCoupons:" + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return coupons;
    }


    /**
     * this method get all customer coupons details by send costumerID to execute by the SQL query "GET_COUPONS_FOR_COSTUMER_BY_ID"
     * @param costumerID customer id
     * @return all the coupons of specific customer
     */
    public ArrayList<Coupon> getCostumersCouponsById(int costumerID) {
        ArrayList<Coupon> coupons = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_COUPONS_FOR_COSTUMER_BY_ID);
            statement.setInt(1,costumerID);
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
            System.out.println("the coupons that belongs to costumer  id number " + costumerID + " are: ");
            for (Coupon item : coupons) {
                System.out.println(item);
            }

        } catch (InterruptedException | SQLException e) {
            System.out.println("CouponDBDAO.getAllCoupons:" + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }

        return coupons;

    }


    /**
     * this method get category num and customer id and execute the SQL query "GET_COUPONS_FOR_COSTUMER_BY_CATEGORY"
     * @param category category num
     * @param customerID customer id
     * @return all the coupons that the customer have from the specific category
     */
    public ArrayList<Coupon> getCostumersCouponsByCategory(Category category, int customerID) {
        ArrayList<Coupon> coupons = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_COUPONS_FOR_COSTUMER_BY_CATEGORY);
            statement.setInt(1, category.ordinal()+1);
            statement.setInt(2,customerID);
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
            }for (Coupon item : coupons) {
                System.out.println(item);
            }
        } catch (InterruptedException | SQLException e) {
            System.out.println("CouponDBDAO.getAllCoupons:" + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }

        return coupons;

    }


    /**
     * this method subtraction one coupon from the total amount of coupon we want to purchase
     * @param couponID the coupon id we want to purchase
     * @return
     */
    public boolean changeCouponAmountAfterPurchase(int couponID) {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_AMOUNT);
            statement.setInt(1, couponID);
            statement.execute();
        } catch (Exception e) {
            e.getMessage();
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return false;
    }



    /**
     * this method send SQL query "GET_ALL_COMPANY_COUPONS_BY_MAX_PRICE" to DB .
     * @param company_id specific company id.
     * @param maxPrice specific coupons maximum price.
     * @return all the coupon that their price under maxPrice from DB.
     */
    public ArrayList<Coupon> getAllCouponsByMaxPrice(int company_id, double maxPrice) {
        List<Coupon> coupons = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COMPANY_COUPONS_BY_MAX_PRICE);
            statement.setInt(1, company_id);
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
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return (ArrayList<Coupon>) coupons;
    }
}






