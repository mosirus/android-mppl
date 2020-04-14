package com.mppl.banksampah.admin.model;

import com.mppl.banksampah.R;

public class Reward {
    private String namaReward;
    private String pointReward;
    private String jenisReward;
    private String URLReward;

    public Reward(){

    }

    public Reward(String namaReward1, String pointReward1, String jenisReward1, String URLReward1){
        namaReward = namaReward1;
        pointReward = pointReward1;
        jenisReward = jenisReward1;
        URLReward = URLReward1;
    }

    public String getNamaReward() {
        return namaReward;
    }

    public void setNamaReward(String namaReward) {
        this.namaReward = namaReward;
    }

    public String getJenisReward() {
        return jenisReward;
    }

    public void setJenisReward(String jenisReward) {
        this.jenisReward = jenisReward;
    }

    public String getPointReward() {
        return pointReward;
    }

    public void setPointReward(String pointReward) {
        this.pointReward = pointReward;
    }

    public String getURLReward() {
        return URLReward;
    }

    public void setURLReward(String URLReward) {
        this.URLReward = URLReward;
    }

}
