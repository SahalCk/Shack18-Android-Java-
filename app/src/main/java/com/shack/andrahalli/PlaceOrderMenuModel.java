package com.shack.andrahalli;

public class PlaceOrderMenuModel {

    String itemname,itemprize,vegornonveg,foodtype,url,itemcount;

    PlaceOrderMenuModel(){

    }

    public PlaceOrderMenuModel(String itemname, String itemprize, String vegornonveg, String foodtype, String url, String itemcount) {
        this.itemname = itemname;
        this.itemprize = itemprize;
        this.vegornonveg = vegornonveg;
        this.foodtype = foodtype;
        this.url = url;
        this.itemcount = itemcount;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemprize() {
        return itemprize;
    }

    public void setItemprize(String itemprize) {
        this.itemprize = itemprize;
    }

    public String getVegornonveg() {
        return vegornonveg;
    }

    public void setVegornonveg(String vegornonveg) {
        this.vegornonveg = vegornonveg;
    }

    public String getFoodtype() {
        return foodtype;
    }

    public void setFoodtype(String foodtype) {
        this.foodtype = foodtype;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getItemcount() {
        return itemcount;
    }

    public void setItemcount(String itemcount) {
        this.itemcount = itemcount;
    }
}
