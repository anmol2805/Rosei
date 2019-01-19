package com.anmol.rosei.Helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteCantOpenDatabaseException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteReadOnlyDatabaseException
import com.anmol.rosei.Model.Mess_Menu

private val DATABASE_NAME = "menudb"
private val TABLE_NAME = "messdown_menu_table"
private val COL_DAY = "day"
private val COL_BREAKFAST = "breakfast"
private val COL_LUNCH = "lunch"
private val COL_DINNER = "dinner"
private val COL_DATE = "date"

class MessDownMenuDb (context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null,1){

    override fun onCreate(p0: SQLiteDatabase?) {
        val createtable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_DAY + " VARCHAR(16) PRIMARY KEY NOT NULL UNIQUE," +
                COL_BREAKFAST + " VARCHAR(256)," +
                COL_LUNCH + " VARCHAR(256)," +
                COL_DINNER + " VARCHAR(256)" +
                COL_DATE + " VARCHAR(16))"
        p0?.execSQL(createtable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(p0)
    }

    fun insertData(mess_Menu: Mess_Menu){
        try{
            val db = this.writableDatabase
            db.enableWriteAheadLogging()
            val cv = ContentValues()
            cv.put(COL_DAY,mess_Menu.weekday)
            cv.put(COL_BREAKFAST,mess_Menu.breakfast)
            cv.put(COL_LUNCH,mess_Menu.lunch)
            cv.put(COL_DINNER,mess_Menu.dinner)
            cv.put(COL_DATE,mess_Menu.date)
            val result = db.insert(TABLE_NAME,null,cv)
            if(result == (-1).toLong())
                System.out.println("sqlstatus is failed")
            else
                System.out.println("sqlstatus is successs")
        }catch (e: SQLiteCantOpenDatabaseException){

        }

    }
    fun readData(dataquery: String):MutableList<Mess_Menu>{
        val mess_menus : MutableList<Mess_Menu> = ArrayList()
        try {
            val db = this.readableDatabase
            val result = db.rawQuery(dataquery,null)
            if(result.moveToFirst()){
                do{
                    val weekday = result.getString(result.getColumnIndex(COL_DAY))
                    val breakfast = result.getString(result.getColumnIndex(COL_BREAKFAST))
                    val lunch = result.getString(result.getColumnIndex(COL_LUNCH))
                    val dinner = result.getString(result.getColumnIndex(COL_DINNER))
                    val date = result.getString(result.getColumnIndex(COL_DATE))
                    val mess_Menu = Mess_Menu(weekday,breakfast,lunch,dinner,date)
                    mess_menus.add(mess_Menu)

                }while (result.moveToNext())
            }
            result.close()
            db.close()
            System.out.println("inside:$mess_menus")

        }
        catch (e:SQLiteCantOpenDatabaseException){
            e.printStackTrace()
        }
        catch (e: SQLiteReadOnlyDatabaseException){
            e.printStackTrace()
        }
        return mess_menus
    }
    fun updatenotice(mess_Menu: Mess_Menu){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_BREAKFAST,mess_Menu.breakfast)
        cv.put(COL_LUNCH,mess_Menu.lunch)
        cv.put(COL_DINNER,mess_Menu.dinner)
        cv.put(COL_DATE,mess_Menu.date)
        db.update(TABLE_NAME,cv,"$COL_DAY = ?", arrayOf(mess_Menu.weekday))
        db.close()
    }

}