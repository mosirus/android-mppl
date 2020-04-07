package com.mppl.banksampah.admin;

import java.util.Date;

public class JemputModel {
    private Date tanggal;
    private String email;
    private String lokasi;


    public JemputModel(String tanggal, String email, String lokasi) {
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }
}
