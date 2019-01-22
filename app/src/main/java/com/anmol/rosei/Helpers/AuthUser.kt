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
}