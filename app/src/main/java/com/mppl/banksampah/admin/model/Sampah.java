package com.mppl.banksampah.admin.model;

public class Sampah {
    private String jenisSampah;
    private String satuan;

    public Sampah() {
    }

    public Sampah(String jenisSampah, String satuan) {
        this.jenisSampah = jenisSampah;
        this.satuan = satuan;
    }

    public String getJenisSampah() {
        return jenisSampah;
    }

    public void setJenisSampah(String jenisSampah) {
        this.jenisSampah = jenisSampah;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }
}