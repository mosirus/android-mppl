package com.mppl.banksampah.user.model;

public class RequestedReward {
    private String tanggalRequest;
    private String emailRequester;
    private String poinBarangRequest;
    private String namaBarangRequest;
    private String statusRequested;

    public RequestedReward(){

    }

    public RequestedReward(String tanggalRequest1, String emailRequester1, String poinBarangRequest1, String namaBarangRequest1,String statusRequested1){
        tanggalRequest = tanggalRequest1;
        emailRequester = emailRequester1;
        poinBarangRequest = poinBarangRequest1;
        namaBarangRequest = namaBarangRequest1;
        statusRequested = statusRequested1;
    }

    public String getTanggalRequest() {
        return tanggalRequest;
    }

    public void setTanggalRequest(String tanggalRequest) {
        this.tanggalRequest = tanggalRequest;
    }

    public String getEmailRequester() {
        return emailRequester;
    }

    public void setEmailRequester(String emailRequester) {
        this.emailRequester = emailRequester;
    }

    public String getPoinBarangRequest() {
        return poinBarangRequest;
    }

    public void setPoinBarangRequest(String poinBarangRequest) {
        this.poinBarangRequest = poinBarangRequest;
    }

    public String getNamaBarangRequest() {
        return namaBarangRequest;
    }

    public void setNamaBarangRequest(String namaBarangRequest) {
        this.namaBarangRequest = namaBarangRequest;
    }

    public String getStatusRequested() {
        return statusRequested;
    }

    public void setStatusRequested(String statusRequested) {
        this.statusRequested = statusRequested;
    }
}
