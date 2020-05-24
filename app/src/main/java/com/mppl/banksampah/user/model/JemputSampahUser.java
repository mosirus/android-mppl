package com.mppl.banksampah.user.model;

public class JemputSampahUser {
    private String Berat;
    private String JenisSampah;
    private String LokasiJemput;
    private String Poin;
    private String Satuan;
    private String Status;
    private String Tanggal;
    private String pushKey;
    private String Email;
    private String currentId;


    public JemputSampahUser() {
    }

    public JemputSampahUser(String berat, String jenisSampah, String lokasiJemput, String poin, String satuan, String status, String tanggal, String pushKey, String email, String currentId) {
        Berat = berat;
        JenisSampah = jenisSampah;
        LokasiJemput = lokasiJemput;
        Poin = poin;
        Satuan = satuan;
        Status = status;
        Tanggal = tanggal;
        this.pushKey = pushKey;
        Email = email;
        this.currentId = currentId;
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

    public String getLokasiJemput() {
        return LokasiJemput;
    }

    public void setLokasiJemput(String lokasiJemput) {
        LokasiJemput = lokasiJemput;
    }

    public String getPoin() {
        return Poin;
    }

    public void setPoin(String poin) {
        Poin = poin;
    }

    public String getSatuan() {
        return Satuan;
    }

    public void setSatuan(String satuan) {
        Satuan = satuan;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }

    public String getCurrentId() {
        return currentId;
    }

    public void setCurrentId(String currentId) {
        this.currentId = currentId;
    }

    public String getPushKey() {
        return pushKey;
    }

    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}