package coupon_system.entities;

import coupon_system.enums.CouponType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@Entity
public class Coupon implements Serializable, Comparable<Coupon> {

    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(nullable = false)
    private int amount;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponType couponType;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private String image;
    @ManyToOne
    private Company company;

    public Coupon() {
    }

    public Coupon(String title,
                  Date startDate,
                  Date endDate,
                  int amount,
                  CouponType couponType,
                  String message,
                  double price,
                  String image,
                  Company company) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.couponType = couponType;
        this.message = message;
        this.price = price;
        this.image = image;
        this.company = company;
    }

    public Coupon(String title,
                  Date startDate,
                  Date endDate,
                  int amount,
                  CouponType couponType,
                  String message,
                  double price,
                  String image) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.couponType = couponType;
        this.message = message;
        this.price = price;
        this.image = image;
    }

    public Coupon(int id,
                  String title,
                  Date startDate,
                  Date endDate,
                  int amount,
                  CouponType couponType,
                  String message,
                  double price,
                  String image,
                  Company company) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.couponType = couponType;
        this.message = message;
        this.price = price;
        this.image = image;
        this.company = company;
    }

    public Coupon(int id,
                  String title,
                  Date startDate,
                  Date endDate,
                  int amount,
                  CouponType couponType,
                  String message,
                  double price,
                  String image) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.couponType = couponType;
        this.message = message;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public CouponType getCouponType() {
        return couponType;
    }

    public void setCouponType(CouponType couponType) {
        this.couponType = couponType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getCompanyId() {
        return company.getId();
    }

    @Override
    public int compareTo(Coupon coupon) {
        return Long.compare(this.id, coupon.id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coupon)) {
            return false;
        }
        Coupon other = (Coupon) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return String.format("Coupon ID: %d, " +
                        "Title: %s, " +
                        "Amount: %d, " +
                        "Coupon type: %s, " +
                        "Message: %s, " +
                        "Price: %f, " +
                        "Image: %s, " +
                        "Start date: %s, " +
                        "End date: %s",
                id, title, amount, couponType, message, price, image, startDate, endDate);
    }

}
