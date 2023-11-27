package stu.edu.vn.doan.models;

import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;

public class Friend implements Serializable {
    private int uid;
    private String name;
    private String primary_name;
    private String birthday;
    private String social;
    private String email;
    private byte[] avatar;

    public Friend(int uid, String name, String primary_name, String birthday, String social, String email, byte[] avatar) {
        this.uid = uid;
        this.name = name;
        this.primary_name = primary_name;
        this.birthday = birthday;
        this.social = social;
        this.email = email;
        this.avatar = avatar;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrimary_name() {
        return primary_name;
    }

    public void setPrimary_name(String primary_name) {
        this.primary_name = primary_name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSocial() {
        return social;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}
