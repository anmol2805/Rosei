package com.anmol.rosei.Model;

import java.util.Date;

/**
 * Created by anmol on 10/18/2017.
 */

public class mess1 {

    String date,brkfast,lnch,dinnr,brkfastdown,lnchdown,dinnrdown,bs,ls,ds;

    public mess1() {
    }

    public mess1(String date, String brkfast, String lnch, String dinnr,String brkfastdown, String lnchdown, String dinnrdown, String bs, String ls, String ds) {
        this.date = date;
        this.brkfast = brkfast;
        this.lnch = lnch;
        this.dinnr = dinnr;
        this.brkfastdown = brkfastdown;
        this.lnchdown = lnchdown;
        this.dinnrdown = dinnrdown;
        this.bs = bs;
        this.ls = ls;
        this.ds = ds;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBrkfastdown() {
        return brkfastdown;
    }

    public void setBrkfastdown(String brkfastdown) {
        this.brkfastdown = brkfastdown;
    }

    public String getLnchdown() {
        return lnchdown;
    }

    public void setLnchdown(String lnchdown) {
        this.lnchdown = lnchdown;
    }

    public String getDinnrdown() {
        return dinnrdown;
    }

    public void setDinnrdown(String dinnrdown) {
        this.dinnrdown = dinnrdown;
    }

    public String getBs() {
        return bs;
    }

    public void setBs(String bs) {
        this.bs = bs;
    }

    public String getLs() {
        return ls;
    }

    public void setLs(String ls) {
        this.ls = ls;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public String getDay() {
        return date;
    }

    public void setDay(String date) {
        this.date = date;
    }

    public String getBrkfast() {
        return brkfast;
    }

    public void setBrkfast(String brkfast) {
        this.brkfast = brkfast;
    }

    public String getLnch() {
        return lnch;
    }

    public void setLnch(String lnch) {
        this.lnch = lnch;
    }

    public String getDinnr() {
        return dinnr;
    }

    public void setDinnr(String dinnr) {
        this.dinnr = dinnr;
    }
}

