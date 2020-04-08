package com.mppl.banksampah.user.model;

import java.io.Serializable;

public class User implements Serializable {

    public User(String nama_lengkap, String email, String no_telp, String password,
                String pekerjaan, String no_identitas, String alamat,int point) {
        this.nama_lengkap = nama_lengkap;
        this.email = email;
        this.no_telp = no_telp;
        this.password = password;
        this.pekerjaan = pekerjaan;
        this.alamat = alamat;
        this.no_identitas = no_identitas;
        this.point = point;
    }

    public String nama_lengkap;
    public String email;
    public String no_telp;
    public String password;
    public String pekerjaan;
    public String alamat;
    public String no_identitas;

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNo_identitas() {
        return no_identitas;
    }

    public void setNo_identitas(String no_identitas) {
        this.no_identitas = no_identitas;
    }

    public int point;

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
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
}
