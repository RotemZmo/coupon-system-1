package coupon_system.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
public class Company implements Serializable, Comparable<Company> {

    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Coupon> coupons;

    public Company() {
    }

    public Company(String name,
                   String password,
                   String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public Company(int id,
                   String name,
                   String password,
                   String email) {
        this.id = id;
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

    public void setName(String compName) {
        this.name = compName;
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

    @Override
    public int compareTo(Company company) {
        return Long.compare(this.id, company.id);
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
        if (!(obj instanceof Company)) {
            return false;
        }
        Company other = (Company) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return String.format("Company ID: %d," +
                        " Name: %s," +
                        " Password: %s," +
                        " Email: %s",
                id, name, password, email);
    }
}
