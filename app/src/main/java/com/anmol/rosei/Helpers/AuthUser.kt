package com.anmol.rosei.Helpers

import android.content.Context
import android.content.SharedPreferences

class AuthUser(context: Context){

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("com.canopydevelopers.rasoi",Context.MODE_PRIVATE)
    fun writegender(gender:String): Boolean {
        val editor = sharedPreferences.edit()
        editor.putString("gender",gender)
        return editor.commit()
    }
    fun readgender(): String {
        return sharedPreferences.getString("gender","male")
    }

    fun writeuser(sid:String): Boolean {
        val editor = sharedPreferences.edit()
        editor.putString("sid",sid)
        return editor.commit()
    }
    fun readuser(): String {
        return sharedPreferences.getString("sid","")
    }
    fun writeusername(fname:String): Boolean {
        val editor = sharedPreferences.edit()
        editor.putString("firstname",fname)
        return editor.commit()
    }
    fun readusername(): String {
        return sharedPreferences.getString("firstname","")
    }
    fun writedate(wd:String): Boolean {
        val editor = sharedPreferences.edit()
        editor.putString("wd",wd)
        return editor.commit()
    }
    fun readdate(): String {
        return sharedPreferences.getString("wd","0001-01-01T00:00:00Z")
    }

    fun writeprice(amount1:Int,amount2:Int,total:Int,cid:String): Boolean {
        val editor = sharedPreferences.edit()
        editor.putInt("amount1",amount1)
        editor.putInt("amount2",amount2)
        editor.putInt("total",total)
        editor.putString("cid",cid)
        return editor.commit()
    }
    fun readamount1(): Int {
        return sharedPreferences.getInt("amount1",0)
    }
    fun readamount2(): Int {
        return sharedPreferences.getInt("amount2",0)
    }
    fun readtotal(): Int {
        return sharedPreferences.getInt("total",0)
    }
    fun readcid(): String {
        return sharedPreferences.getString("cid","")
    }
}