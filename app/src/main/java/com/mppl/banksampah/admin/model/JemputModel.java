package com.mppl.banksampah.admin.model;

public class JemputModel {
    private int tanggal;
    private String email;
    private String lokasi;


    public JemputModel(int tanggal, String email, String lokasi) {
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

    public int getTanggal() {
        return tanggal;
    }

    public void setTanggal(int tanggal) {
        this.tanggal = tanggal;
    }
}
