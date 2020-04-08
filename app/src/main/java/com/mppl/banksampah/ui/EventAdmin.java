package com.mppl.banksampah.ui;

public class EventAdmin {
    public String getNamaevent() {
        return namaevent;
    }

    public void setNamaevent(String namaevent) {
        this.namaevent = namaevent;
    }

    public String getTanggaldanwaktu() {
        return tanggaldanwaktu;
    }

    public void setTanggaldanwaktu(String tanggaldanwaktu) {
        this.tanggaldanwaktu = tanggaldanwaktu;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }

    private String namaevent;
    private String tanggaldanwaktu;
    private String deskripsi;
    private String tempat;

}
