package coupon_system.entities;

import coupon_system.enums.ClientType;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Token {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClientType clientType;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date expDate;

    @Column(nullable = false)
    private String token;

    public Token() {
    }

    public Token(long userId,
                 ClientType clientType,
                 Date expDate,
                 String token) {
        this.userId = userId;
        this.clientType = clientType;
        this.expDate = expDate;
        this.token = token;
    }

    public Token(ClientType clientType,
                 Date expDate,
                 String token) {
        this.clientType = clientType;
        this.expDate = expDate;
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;
        Token token = (Token) o;
        return getId() == token.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", userId=" + userId +
                ", clientType=" + clientType +
                ", expDate=" + expDate +
                ", token='" + token + '\'' +
                '}';
    }
}
