package com.anmol.rosei.Model;

import java.util.Date;

/**
 * Created by anmol on 10/18/2017.
 */

public class mess1 {
    Date date;
    String brkfast,lnch,dinnr;

    public mess1() {
    }

    public mess1(Date date, String brkfast, String lnch, String dinnr) {
        this.date = date;
        this.brkfast = brkfast;
        this.lnch = lnch;
        this.dinnr = dinnr;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

