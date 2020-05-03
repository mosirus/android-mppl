package com.mppl.banksampah.admin.model;

public class RiwayatEvent {
    private String timeEvent;
    private String nameEvent;

    public RiwayatEvent() {
        this.timeEvent = timeEvent;
        this.nameEvent = nameEvent;
    }

    public RiwayatEvent(String timeEvent, String nameEvent) {
        this.timeEvent = timeEvent;
        this.nameEvent = nameEvent;
    }

    public String getTimeEvent() {
        return timeEvent;
    }

    public void setTimeEvent(String timeEvent) {
        this.timeEvent = timeEvent;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }
}
