package com.danarossa.entities;

import java.io.Serializable;
import java.util.Date;

public class Account extends Entity<Long> implements Serializable {
    private String email;
    private String password;
    private String accountType;
    private User user;
    private Date lastTimeActive;

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

}
