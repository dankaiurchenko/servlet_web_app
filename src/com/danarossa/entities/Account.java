package com.danarossa.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Account extends Entity<Long> implements Serializable {
    private String email;
    private String password;
    private String accountType;
    private User user;
    private Date lastTimeActive = new Date();

    public Account(String email, String password, String accountType) {
        this.email = email;
        this.password = password;
        this.accountType = accountType;
        this.user = new User(0L);
    }

    public Account(String email, String password, String accountType, User user) {
        this.email = email;
        this.password = password;
        this.accountType = accountType;
        this.user = user;
    }

    public Account(Long id, String email, String password, String accountType, User user) {
        super(id);
        this.email = email;
        this.password = password;
        this.accountType = accountType;
        this.user = user;
    }

    public Account(Long id, String email, String password, String accountType, User user, Date lastTimeActive) {
        super(id);
        this.email = email;
        this.password = password;
        this.accountType = accountType;
        this.user = user;
        this.lastTimeActive = lastTimeActive;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAccountType() {
        return accountType;
    }

    public User getUser() {
        return user;
    }

    public Date getLastTimeActive() {
        return lastTimeActive;
    }

    public Long getUserId() {
        return user.getId();
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLastTimeActive(Date lastTimeActive) {
        this.lastTimeActive = lastTimeActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Account account = (Account) o;
        return Objects.equals(email, account.email) &&
                Objects.equals(password, account.password) &&
                Objects.equals(accountType, account.accountType) &&
                Objects.equals(user.getId(), account.user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, password, accountType, user, lastTimeActive);
    }


    @Override
    public String toString() {
        return "Account{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", accountType='" + accountType + '\'' +
                ", user=" + user +
                ", lastTimeActive=" + lastTimeActive +
                ", id=" + id +
                '}';
    }
}
