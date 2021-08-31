package Configuration;//"couponprojectchen.config"

import java.io.*;

/**
 * //configuration we will use this class to do the connections to the DB
 */
public class Config implements Serializable {
    //we will neeed serialVersionUID = to indicate a specific class serialization
    //link to data : https://stackoverflow.com/questions/10378855/java-io-invalidclassexception-local-class-incompatible
    private static final long serialVersionUID = 6529685098267757690L;
    String sqlConnectionString;
    boolean createIfNotExists;
    boolean useTimeZone;
    String serverTimeZone;
    String userName;
    String userPassword;
    String DBname;

    /**
     * Default Con
     */
    public Config(){}

    /**
     * Con that get all the arguments
     * @param sqlConnectionString url that we wort on DBManager
     * @param createIfNotExists boolean that check if the file already exists
     * @param useTimeZone our current time
     * @param serverTimeZone sever current time
     * @param userName user name
     * @param userPassword user password
     * @param DBname Data base name.
     */

    public Config(String sqlConnectionString, boolean createIfNotExists, boolean useTimeZone, String serverTimeZone, String userName, String userPassword, String DBname) {
        this.sqlConnectionString = sqlConnectionString;
        this.createIfNotExists = createIfNotExists;
        this.useTimeZone = useTimeZone;
        this.serverTimeZone = serverTimeZone;
        this.userName = userName;
        this.userPassword = userPassword;
        this.DBname = DBname;
    }


    /**
     *We will save our settings into a file in the form of saving a zip file-Serialization
     * @return boolean answer true in case the stream of the data was send to file currently
     */

    public boolean saveConfig(){
        try {
            //we declare the file itself, where we are going to write data....
            FileOutputStream fileOut = new FileOutputStream("couponprojectchen.config");

            //we will write an object , since we don't know which class type we will be using
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(this);
            out.close();
            fileOut.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
        catch (IOException e) {
            System.out.println( "config.saveConfig"+ e.getMessage());
        }
        return false;
    }


    /**
     *We will read our settings from the "zip" file we created-Deserializing
     * @return boolean answer true in case the stream of the data was send to file currently
     */

    public static Config readConfig(){
        Config returnResult = new Config();
        //choose the file that we will read from
        try {
            //point to the file that we will be reading from
            FileInputStream fileIn = new FileInputStream("couponprojectchen.config");
            //create an object from the file
            ObjectInputStream in = new ObjectInputStream(fileIn);
            //get the data as config file (by the fields that we used)
            returnResult = (Config)in.readObject();
            //close inputStream
            in.close();
            //close the file
            fileIn.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println( "config.readConfig"+ e.getMessage());
        } finally {
            return returnResult;
        }

    }



    public String getSqlConnectionString() {
        return sqlConnectionString;
    }

    public void setSqlConnectionString(String sqlConnectionString) {
        this.sqlConnectionString = sqlConnectionString;
    }

    public boolean isCreateIfNotExists() {
        return createIfNotExists;
    }

    public void setCreateIfNotExists(boolean createIfNotExists) {
        this.createIfNotExists = createIfNotExists;
    }

    public boolean isUseTimeZone() {
        return useTimeZone;
    }

    public void setUseTimeZone(boolean useTimeZone) {
        this.useTimeZone = useTimeZone;
    }

    public String getServerTimeZone() {
        return serverTimeZone;
    }

    public void setServerTimeZone(String serverTimeZone) {
        this.serverTimeZone = serverTimeZone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getDBname() {
        return DBname;
    }

    public void setDBname(String DBname) {
        this.DBname = DBname;
    }


    /**
     * * ToString print all parameters
     * @return all parameters to the console
     */
    @Override
    public String toString() {
        return "Config{" +
                "sqlConnectionString='" + sqlConnectionString + '\'' +
                ", createIfNotExists=" + createIfNotExists +
                ", useTimeZone=" + useTimeZone +
                ", serverTimeZone='" + serverTimeZone + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", DBname='" + DBname + '\'' +
                '}';
    }
}
