// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.mppl.banksampah;

import java.io.Serializable;

public class RiwayatTukarSampah implements Serializable {
    private String Tanggal;
    private String Email;
    private String Notelp;
    private String JenisSampah;
    private String Poin;

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNotelp() {
        return Notelp;
    }

    public void setNotelp(String notelp) {
        Notelp = notelp;
    }

    public String getJenisSampah() {
        return JenisSampah;
    }

    public void setJenisSampah(String jenisSampah) {
        JenisSampah = jenisSampah;
    }

    public String getPoin() {
        return Poin;
    }

    public void setPoin(String poin) {
        Poin = poin;
    }

    public RiwayatTukarSampah(){

    }

    public RiwayatTukarSampah(String tanggal, String email, String notelp, String jenisSampah, String poin){
        Tanggal = tanggal;
        Email = email;
        Notelp = notelp;
        JenisSampah = jenisSampah;
        Poin = poin;
    }


}
