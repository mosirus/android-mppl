package com.mppl.banksampah.admin.model;

public class EventAdmin {
//    private int photoEvent;
    private String URLEvent;
    private String namaEvent;
    private String waktuEvent;
    private String tempatEvent;
    private String descEvent;

//    public int getPhotoEvent() {
//        return photoEvent;
//    }
//
//    public void setPhotoEvent(int photoEvent) {
//        this.photoEvent = photoEvent;
//    }


    public EventAdmin() {
        this.URLEvent = URLEvent;
        this.namaEvent = namaEvent;
        this.waktuEvent = waktuEvent;
        this.tempatEvent = tempatEvent;
        this.descEvent = descEvent;
    }

    public EventAdmin(String URLEvent, String namaEvent, String waktuEvent, String tempatEvent, String descEvent) {
        this.URLEvent = URLEvent;
        this.namaEvent = namaEvent;
        this.waktuEvent = waktuEvent;
        this.tempatEvent = tempatEvent;
        this.descEvent = descEvent;
    }

    public String getURLEvent() {
        return URLEvent;
    }

    public void setURLEvent(String URLEvent) {
        this.URLEvent = URLEvent;
    }

    public String getNamaEvent() {
        return namaEvent;
    }

    public void setNamaEvent(String namaEvent) {
        this.namaEvent = namaEvent;
    }

    public String getWaktuEvent() {
        return waktuEvent;
    }

    public void setWaktuEvent(String waktuEvent) {
        this.waktuEvent = waktuEvent;
    }

    public String getTempatEvent() {
        return tempatEvent;
    }

    public void setTempatEvent(String tempatEvent) {
        this.tempatEvent = tempatEvent;
    }

    public String getDescEvent() {
        return descEvent;
    }

    public void setDescEvent(String descEvent) {
        this.descEvent = descEvent;
    }
}
