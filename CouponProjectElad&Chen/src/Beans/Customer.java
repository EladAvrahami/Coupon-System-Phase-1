package Beans;

import java.util.ArrayList;
import java.util.List;

/**
 *this class contain all the basic parameters to create a Customer Obj
 */
public class Customer {
    public int id;
    public String first_Name;
    public String last_Name;
    public String email;
    public String passWord;
    public List<Coupon> coupons= new ArrayList<>();


    /**
     * Con that will get all parameters of Customer for DB
     * @param id
     * @param first_Name
     * @param last_Name
     * @param email
     * @param passWord
     */
    public Customer(int id, String first_Name, String last_Name, String email, String passWord) {
        this.id = id;
        this.first_Name = first_Name;
        this.last_Name = last_Name;
        this.email = email;
        this.passWord = passWord;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return first_Name;
    }

    public void setFirstName(String firstName) {
        this.first_Name = firstName;
    }

    public String getLastName() {
        return last_Name;
    }

    public void setLastName(String lastName) {
        this.last_Name = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    /**
     ToString print all parameters
     * @return print all parameters on the console
     */
    @Override
    public String toString() {
        return "Costumer{" +
                "id=" + id +
                ", firstName='" + first_Name + '\'' +
                ", lastName='" + last_Name + '\'' +
                ", email='" + email + '\'' +
                ", passWord='" + passWord + '\'' +
                ", coupons=" + coupons +
                '}';
    }
}
