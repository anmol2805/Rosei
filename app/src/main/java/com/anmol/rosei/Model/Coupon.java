package com.anmol.rosei.Model;

public class Coupon {
    String meal,mess,day,date,menu,time;

    public Coupon() {
    }

    public Coupon(String meal, String mess, String day, String date, String menu, String time) {
        this.meal = meal;
        this.mess = mess;
        this.day = day;
        this.date = date;
        this.menu = menu;
        this.time = time;
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

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
