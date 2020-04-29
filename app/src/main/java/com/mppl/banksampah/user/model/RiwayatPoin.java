package com.mppl.banksampah.user.model;

import java.io.Serializable;

public class RiwayatPoin implements Serializable {
    private String Berat;
    private String JenisSampah;
    private String Satuan;
    private String Tanggal;
    private String Poin;
    private String Status;

    public RiwayatPoin() {
    }

    public RiwayatPoin(String berat, String jenisSampah, String satuan, String tanggal, String poin) {
        Berat = berat;
        JenisSampah = jenisSampah;
        Satuan = satuan;
        Tanggal = tanggal;
        Poin = poin;
    }

    public String getBerat() {
        return Berat;
    }

    public void setBerat(String berat) {
        Berat = berat;
    }

    public String getJenisSampah() {
        return JenisSampah;
    }

    public void setJenisSampah(String jenisSampah) {
        JenisSampah = jenisSampah;
    }

    public String getSatuan() {
        return Satuan;
    }

    public void setSatuan(String satuan) {
        Satuan = satuan;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }

    public String getPoin() {
        return Poin;
    }

    public void setPoin(String poin) {
        Poin = poin;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
