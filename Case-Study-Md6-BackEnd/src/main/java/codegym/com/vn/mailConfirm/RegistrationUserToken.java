package codegym.com.vn.mailConfirm;



import codegym.com.vn.model.User;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "registration")
public class RegistrationUserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User account;

    private Date expiryDate;

    public RegistrationUserToken(String token, User account) {
        this.token = token;
        this.account = account;

        //1h
        expiryDate = new Date(System.currentTimeMillis() + 360000);
    }

    public RegistrationUserToken() {
    }

    public RegistrationUserToken(String token, User account, Date expiryDate) {
        this.token = token;
        this.account = account;
        this.expiryDate = expiryDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getAccount() {
        return account;
    }

    public void setAccount(User account) {
        this.account = account;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
