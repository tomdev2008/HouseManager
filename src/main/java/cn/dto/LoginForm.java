package cn.dto;

import java.io.Serializable;

/**
 * Created by ZLY on 2017-04-10.
 */
public class LoginForm implements Serializable {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
