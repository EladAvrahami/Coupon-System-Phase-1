package Dao;

import Beans.Company;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * company interface
 */
public interface CompaniesDAO {


    public boolean isCompanyExists (String email, String password);
    public boolean addCompany(Company company);
    public boolean updateCompany(Company Company) throws InterruptedException, SQLException;
    public boolean deleteCompany(int CompanyID);
    public ArrayList<Company> getAllCompanies();
    public Company getOneCompany(int companyId);


}
