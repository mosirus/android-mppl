package com.mppl.banksampah;

import com.google.firebase.database.IgnoreExtraProperties;
import java.io.Serializable;

public class User implements Serializable{

    public User(String nama_lengkap, String email, String username, String no_telp, String password, int point) {
        this.nama_lengkap = nama_lengkap;
        this.email = email;
        this.username = username;
        this.no_telp = no_telp;
        this.password = password;
        this.point = point;
    }

    public User(String name, String email) {
        this.nama_lengkap = name;
        this.email = email;
    }

    public String nama_lengkap;
    public String username;
    public String email;
    public String no_telp;
    public String password;
    public int point;

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    //sudah di edit oleh clara
}
