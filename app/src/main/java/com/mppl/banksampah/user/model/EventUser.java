package com.mppl.banksampah.user.model;

public class EventUser {
    private String URLEvent;
    private String namaEvent;
    private String waktuEvent;
    private String tempatEvent;

    public EventUser(){
        this.URLEvent = URLEvent;
        this.namaEvent = namaEvent;
        this.waktuEvent = waktuEvent;
        this.tempatEvent = tempatEvent;
    }

    public EventUser(String URLEvent, String namaEvent, String waktuEvent, String tempatEvent) {
        this.URLEvent = URLEvent;
        this.namaEvent = namaEvent;
        this.waktuEvent = waktuEvent;
        this.tempatEvent = tempatEvent;
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
}
