package FACADE;

import Dao.CompaniesDAO;
import Dao.CostumersDAO;
import Dao.CouponDAO;
import Exceptions.CouponsException;


/**
 * abstract class that implement all the clients the login method
 */
public abstract class ClientFacade {

    protected CompaniesDAO companiesDAO;
    protected CostumersDAO costumersDAO;
    protected CouponDAO couponDAO;


    /**
     * login is method that we extend to each client
     * @param email client email
     * @param password client password
     * @return boolean answer if the login succeeded
     * @throws CouponsException
     */
    public boolean login(String email, String password) throws CouponsException {
    //to write factory
        
        return false;
    }

}

