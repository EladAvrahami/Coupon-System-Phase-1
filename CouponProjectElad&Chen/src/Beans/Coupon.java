package Beans;


import Enums.Category;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 *this class contain all the basic parameters to create a Coupon Obj
 */
public class Coupon {
    private int id;
    private int companyID;
    private Category category;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;

            /**
             * Con that will get all parameters of Coupon for DB
             * @param id Coupon id
             * @param companyID Coupon companyID
             * @param category Coupon category
             * @param title Coupon title
             * @param description Coupon description
             * @param startDate Coupon startDate
             * @param endDate Coupon endDate
             * @param amount Coupon amount
             * @param price Coupon image
             * @param image Coupon image
             */
    public Coupon(int id, int companyID, Category category, String title, String description, Date startDate, Date endDate, int amount, double price, String image) {
        this.id = id;
        this.companyID = companyID;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

            /**
             * Con that will use to define Coupon parameters (cant define his id- auto increment)
             * @param companyID Coupon companyID
             * @param category  Coupon category
             * @param title  Coupon title
             * @param description Coupon description
             * @param startDate Coupon startDate
             * @param endDate Coupon endDate
             * @param amount amount of Coupon
             * @param price price of Coupon
             * @param image Coupon image
             */
    public Coupon(int companyID, Category category, String title, String description, Date startDate, Date endDate, int amount, double price, String image) {
        this.companyID = companyID;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    // GETTERS&SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

            /**
             * this method help us define dates
             * @param year date year
             * @param month date month
             * @param day date day
             * @return full date
             */
    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        Date returnDate = cal.getTime();
        return returnDate;
    }

            /**
             ToString print all parameters
             * @return print all parameters on the console
             */
    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", companyID=" + companyID +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}
