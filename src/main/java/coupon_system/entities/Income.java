package coupon_system.entities;

import coupon_system.enums.IncomeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Income implements Serializable, Comparable<Income> {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Company company;

    @ManyToOne
    private Customer customer;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IncomeType description;

    @Column(nullable = false)
    private double amount;

    public Income(Company company,
                  Date date,
                  IncomeType description,
                  double amount) {
        this.company = company;
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public Income(Customer customer,
                  Date date,
                  IncomeType description,
                  double amount) {
        this.customer = customer;
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public Income() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public IncomeType getDescription() {
        return description;
    }

    public void setDescription(IncomeType description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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
                ", amount=" + amount +
                '}';
    }
}
