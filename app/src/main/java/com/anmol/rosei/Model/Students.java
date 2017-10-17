package com.anmol.rosei.Model;

import java.io.Serializable;

/**
 * Created by anmol on 2017-08-13.
 */

public class Students implements Serializable {
    String sid,pwd;


    public Students(String sid, String pwd) {
        this.sid = sid;
        this.pwd = pwd;


    }

    public Students() {
    }



    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
