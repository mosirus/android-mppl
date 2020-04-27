package com.mppl.banksampah.user.model;

import java.io.Serializable;

public class StatusAntar implements Serializable {
    private String Berat;
    private String JenisSampah;
    private String NamaSampah;
    private String Satuan;
    private String Status;
    private String Tanggal;
    private String LokasiJemput;
    private String Poin;


    public StatusAntar() {
    }

    public StatusAntar(String berat, String jenisSampah, String namaSampah, String satuan, String status, String tanggal, String lokasiJemput, String poin) {
        Berat = berat;
        JenisSampah = jenisSampah;
        NamaSampah = namaSampah;
        Satuan = satuan;
        Status = status;
        Tanggal = tanggal;
        LokasiJemput = lokasiJemput;
        Poin = poin;
    }

    public String getBerat() {
        return Berat;
    }

    public String getNamaSampah() {
        return NamaSampah;
    }

    public void setNamaSampah(String namaSampah) {
        this.NamaSampah = namaSampah;
    }

    public String getLokasiJemput() {
        return LokasiJemput;
    }

    public void setLokasiJemput(String lokasiJemput) {
        this.LokasiJemput = lokasiJemput;
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

    public String getPoin() {
        return Poin;
    }

    public void setPoin(String poin) {
        Poin = poin;
    }
}
