package coupon_system.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
public class Customer implements Serializable, Comparable<Customer> {

    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Coupon> coupons;

    public Customer() {
    }

    public Customer(String name,
                    String password,
                    String email,
                    Collection<Coupon> coupons) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.coupons = coupons;
    }

    public Customer(int id,
                    String name,
                    String password,
                    String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public Customer(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String custName) {
        this.name = custName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(Collection<Coupon> coupons) {
        this.coupons = coupons;
    }

    public void purchaseCoupon(Coupon coupon) {
        this.coupons.add(coupon);
    }

    @Override
    public int compareTo(Customer customer) {
        return Long.compare(this.id, customer.id);
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
        if (!(obj instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return String.format("Customer ID: %d," +
                        " Name: %s," +
                        " Password: %s," +
                        " Email: %s",
                id, name, password, email);
    }
}
