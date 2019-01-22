package com.anmol.rosei.Model;

public class Coupon {
    String meal,mess,day,date,menuitem;

    public Coupon() {
    }

    public Coupon(String meal, String mess, String day, String date,String menuitem) {
        this.meal = meal;
        this.mess = mess;
        this.day = day;
        this.date = date;
        this.menuitem = menuitem;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMenuitem() {
        return menuitem;
    }

    public void setMenuitem(String menuitem) {
        this.menuitem = menuitem;
    }
}
