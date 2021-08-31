package DB;

import Configuration.Config;

import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * this class contain the commands we enter into a database
 */
public class DatabaseManager {
    //mysql:mysql-connector-java:8.0.23 : file->project structure->Libreries->+->maven-> link
    //conection string for connection to the mysql/mariadb server
    public static String url = "jdbc:mysql://localhost:3306?createDatabaseIfNotExist=FALSE&useTimezone=TRUE&serverTimezone=GMT"; //Asia/Jerusalem
    public static String username = "root";
    public static String password = "";

    private static String CREATE_DB = "CREATE DATABASE IF NOT EXISTS couponsSystem;";
    private static String DROP_DB = "DROP DATABASE IF EXISTS `couponssystem`;";


    /**
     * SQL query's
     */
    private static final String CREATE_TABLE_COMPANIES = "CREATE TABLE IF NOT EXISTS `couponsSystem`.`companies` ( `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT  , `name` VARCHAR(30) NOT NULL , `email` VARCHAR(50) NOT NULL , `password` VARCHAR(25) NOT NULL);";
    private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE IF NOT EXISTS `couponsSystem`.`customers` ( `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT  , `first_name` VARCHAR(30) NOT NULL , `last_name` VARCHAR(30) NOT NULL , `email` VARCHAR(35) NOT NULL , `password` varchar(12) not null);";
    //private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE IF NOT EXISTS `couponsSystem`.`categories` (`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT  , name ENUM ('attractions', 'clothes' , 'electricity', 'flights','food','movies', 'resorts' ,'vacations','restaurant'));";
    public static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE IF NOT EXISTS `couponsSystem`.`categories` (`ID` int(11) NOT NULL AUTO_INCREMENT,`NAME` varchar(50) NOT NULL, PRIMARY KEY (`ID`))";
    private static final String FILL_TABLE_CATEGORIES ="INSERT INTO `couponsSystem`.`categories` (`ID`, `NAME`) VALUES ('1','attractions'), ('2', 'clothes'), ('3', 'electricity'), ('4', 'flights'), ('5', 'food'), ('6', 'movies'), ('7', 'resorts'), ('8', 'restaurant'), ('9', 'vacation');";
    private static final String CREATE_TABLE_COUPONS = "CREATE TABLE IF NOT EXISTS `couponsSystem`.`coupons` (`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT  , `company_id` INT NOT NULL , `category_id` INT NOT NULL , `title` varchar(30) NOT NULL , `description` VARCHAR(150)  NOT NULL , `start_date` DATETIME NOT NULL , `end_date` DATETIME NOT NULL , `amount` int NOT NULL , `price` DOUBLE NOT NULL , `image` VARCHAR(30), FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE ON UPDATE CASCADE,FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE ON UPDATE CASCADE);";
    private static final String CREATE_TABLE_CUSTOMERS_VS_COUPONS = "CREATE TABLE IF NOT EXISTS `couponsSystem`.`CUSTOMERS_VS_COUPONS` (`customer_ID` INT NOT NULL , `coupon_ID` INT NOT NULL, PRIMARY KEY (`customer_ID` , `coupon_ID`) ,FOREIGN KEY (customer_ID) REFERENCES customers(id)  ON DELETE CASCADE ON UPDATE CASCADE,FOREIGN KEY (coupon_ID) REFERENCES coupons(id)  ON DELETE CASCADE ON UPDATE CASCADE);";
    private static final String DROP_TABLE_COMPANIES = "DROP TABLE `couponsSystem`.`companies`";
    private static final String DROP_TABLE_COSTUMERS = "DROP TABLE `couponsSystem`.`customers`";
    private static final String DROP_TABLE_COUPONS = "DROP TABLE `couponsSystem`.`coupons`";
    private static final String DROP_TABLE_CATEGORIES = "DROP TABLE `couponsSystem`.`categories`";
    private static final String DROP_TABLE_CUSTOMERS_VS_COUPONS = "DROP TABLE `couponsSystem`.`CUSTOMERS_VS_COUPONS`";
    //create table and schema


    /**
     * create new couponSystem DB  execute all SQL query's
     * @throws InterruptedException
     * @throws SQLException
     */
    public static void constructDB() throws InterruptedException, SQLException {
        try {
            DBUtils.runQuery(DROP_DB);
            DBUtils.runQuery(CREATE_DB);
            DBUtils.runQuery(CREATE_TABLE_COMPANIES);
            DBUtils.runQuery(CREATE_TABLE_CUSTOMERS);
            DBUtils.runQuery(CREATE_TABLE_CATEGORIES);
            DBUtils.runQuery(FILL_TABLE_CATEGORIES);
            DBUtils.runQuery(CREATE_TABLE_COUPONS);
            DBUtils.runQuery(CREATE_TABLE_CUSTOMERS_VS_COUPONS);

            System.out.println("the DB was contructed");
        } catch (Exception err) {
            System.out.println("error");
        }
    }


    /**
     * Perform the procedure for connecting to the database
     * @throws FileNotFoundException
     */
    public static void getConfiguration() throws FileNotFoundException {
        Config config = Config.readConfig();
        //"jdbc:mysql://localhost:3306?createDatabaseIfNotExist=FALSE&useTimezone=TRUE&serverTimezone=Asia/Jerusalem"
        url = "jdbc:mysql://"+config.getSqlConnectionString()+
                "?createDatabaseIfNotExist"+(config.isCreateIfNotExists()?"TRUE":"FALSE")+
                "&useTimezone="+(config.isUseTimeZone()?"TRUE":"FALSE")+
                "&serverTimezone="+config.getServerTimeZone();
        username = config.getUserName();
        password = config.getUserPassword();
        CREATE_DB = "CREATE DATABASE "+config.getDBname();
        DROP_DB = "DROP DATABASE "+config.getDBname();
    }

    public static void createDataBase() throws SQLException {
        DBUtils.runQuery(CREATE_DB);
    }

    public static void dropDataBase() throws SQLException {
        DBUtils.runQuery(DROP_DB);
    }

    public static void createTableCompanies() throws SQLException {
        DBUtils.runQuery(CREATE_TABLE_COMPANIES);
    }

    public static void CreateTableCostumers() throws SQLException {
        DBUtils.runQuery(CREATE_TABLE_CUSTOMERS);
    }
    public static void createTableCoupons() throws SQLException {
        DBUtils.runQuery(CREATE_TABLE_COUPONS);
    }

    public static void dropTableCompanies() throws SQLException {
        DBUtils.runQuery(DROP_TABLE_COMPANIES);
    }
    public static void dropTableCostumers() throws SQLException {
        DBUtils.runQuery(DROP_TABLE_COSTUMERS);
    }
    public static void dropTableCoupons() throws SQLException {
        DBUtils.runQuery(DROP_TABLE_COUPONS);

    }
    public static void createTableCategories() throws SQLException {
        DBUtils.runQuery(CREATE_TABLE_CATEGORIES);
    }

    public static void dropTableCategories() throws SQLException {
        DBUtils.runQuery(DROP_TABLE_CATEGORIES);

    }

}
