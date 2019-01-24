package lxpsee.top.eshop.domain;

import java.util.Date;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/19 19:45.
 */
public class User {
    private Integer id;
    private String  name;
    private String  password;

    private Date   regDate = new Date();
    private String email;
    private String nickName;

    public User() {
    }

    public User(String name, String password, String email, String nickName) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }
}
