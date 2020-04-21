package com.mppl.banksampah.user.model;

public class AntarSampahUser {
    private String jenisSampah;
    private String satuanSampah;
    private int jumlahSampah;
    private String tanggal;
    private int poin;
    private String currentId;
    private String status;
    //private String noHpUser;

    public AntarSampahUser(){

    }

    public AntarSampahUser(String jenisSampah1,String satuanSampah1, int jumlahSampah1, String tanggal1, int poin1, String currentId1,String status1){
        jenisSampah = jenisSampah1;
        satuanSampah = satuanSampah1;
        jumlahSampah = jumlahSampah1;
        tanggal = tanggal1;
        poin = poin1;
        currentId = currentId1;
        status = status1;
        //noHpUser = noHpUser1;
    }

    public String getJenisSampah() {
        return jenisSampah;
    }

    public void setJenisSampah(String jenisSampah) {
        this.jenisSampah = jenisSampah;
    }

    public String getSatuanSampah() {
        return satuanSampah;
    }

    public void setSatuanSampah(String satuanSampah) {
        this.satuanSampah = satuanSampah;
    }

    public int getJumlahSampah() {
        return jumlahSampah;
    }

    public void setJumlahSampah(int jumlahSampah) {
        this.jumlahSampah = jumlahSampah;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getPoin() {
        return poin;
    }

    public void setPoin(int poin) {
        this.poin = poin;
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

    /*public String getNoHpUser() {
        return noHpUser;
    }

    public void setNoHpUser(String noHpUser) {
        this.noHpUser = noHpUser;
    }*/
}
