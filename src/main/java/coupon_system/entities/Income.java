package coupon_system.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import coupon_system.enums.IncomeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Income implements Serializable, Comparable<Income> {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Company company;

    @ManyToOne
    private Customer customer;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date expDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IncomeType description;

    public Income(Date date,
                  Date expDate,
                  IncomeType description) {
        this.date = date;
        this.description = description;
        this.expDate = expDate;
    }

    public Income(Company company,
                  Date date,
                  Date expDate,
                  IncomeType description) {
        this(date, expDate, description);
        this.company = company;
    }

    public Income(Customer customer,
                  Date date,
                  Date expDate,
                  IncomeType description) {
        this(date, expDate, description);
        this.customer = customer;
    }

    public Income() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public IncomeType getDescription() {
        return description;
    }

    public void setDescription(IncomeType description) {
        this.description = description;
    }

    public String getCompanyName() {
        if (company != null) {
            return company.getName();
        } else {
            return null;
        }
    }

    public String getCustomerName() {
        if (customer != null) {
            return customer.getName();
        } else {
            return null;
        }
    }

    @Override
    public int compareTo(Income company) {
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
        if (!(obj instanceof Income)) {
            return false;
        }
        Income other = (Income) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Income{" +
                "id=" + id +
                ", company=" + company +
                ", customer=" + customer +
                ", date=" + date +
                ", description=" + description +
                '}';
    }
}
