package com.mppl.banksampah.user.model;

public class AntarSampahUser {
    private String JenisSampah;
    private String Satuan;
    private String Berat;
    private String Tanggal;
    private int Poin;
    private String currentId;
    private String status;
    private String pushKey;

    public AntarSampahUser() {

    }

    public AntarSampahUser(String jenisSampah, String satuan, String berat, String tanggal, int poin, String currentId, String status) {
        JenisSampah = jenisSampah;
        Satuan = satuan;
        Berat = berat;
        Tanggal = tanggal;
        Poin = poin;
        this.currentId = currentId;
        this.status = status;
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

    public String getBerat() {
        return Berat;
    }

    public void setBerat(String berat) {
        Berat = berat;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }

    public int getPoin() {
        return Poin;
    }

    public void setPoin(int poin) {
        Poin = poin;
    }

    public String getCurrentId() {
        return currentId;
    }

    public void setCurrentId(String currentId) {
        this.currentId = currentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPushKey() {
        return pushKey;
    }

    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }

}
