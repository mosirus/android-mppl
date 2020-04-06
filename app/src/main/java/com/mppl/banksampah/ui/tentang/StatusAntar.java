package com.mppl.banksampah.ui.tentang;

import java.io.Serializable;

public class StatusAntar implements Serializable {
    private String Berat;
    private String JenisSampah;
    private String Satuan;
    private String Status;
    private String Tanggal;

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

    public StatusAntar() {
    }

    public StatusAntar(String berat, String jenisSampah, String satuan, String status, String tanggal) {
        Berat = berat;
        JenisSampah = jenisSampah;
        Satuan = satuan;
        Status = status;
        Tanggal = tanggal;
    }
}
