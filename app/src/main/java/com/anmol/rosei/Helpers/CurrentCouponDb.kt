package com.anmol.rosei.Helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteCantOpenDatabaseException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteReadOnlyDatabaseException
import com.anmol.rosei.Model.CouponStatus
import com.anmol.rosei.Model.Mess_Menu

private val DATABASE_NAME = "currentcoupondb"
private val TABLE_NAME = "current_coupon_table"
private val COL_DAY = "day"
private val COL_BREAKFAST = "breakfast"
private val COL_LUNCH = "lunch"
private val COL_DINNER = "dinner"


class CurrentCouponDb (context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null,1){

    override fun onCreate(p0: SQLiteDatabase?) {
        val createtable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_DAY + " VARCHAR(16) PRIMARY KEY NOT NULL UNIQUE," +
                COL_BREAKFAST + " VARCHAR(256)," +
                COL_LUNCH + " VARCHAR(256)," +
                COL_DINNER + " VARCHAR(256))"
        p0?.execSQL(createtable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(p0)
    }

    fun insertData(couponStatus: CouponStatus){
        try{
            val db = this.writableDatabase
            db.enableWriteAheadLogging()
            val cv = ContentValues()
            cv.put(COL_DAY,couponStatus.weekday)
            cv.put(COL_BREAKFAST,couponStatus.breakfast)
            cv.put(COL_LUNCH,couponStatus.lunch)
            cv.put(COL_DINNER,couponStatus.dinner)
            val result = db.insert(TABLE_NAME,null,cv)
            if(result == (-1).toLong())
                System.out.println("sqlstatus is failed")
            else
                System.out.println("sqlstatus is successs")
        }catch (e:SQLiteCantOpenDatabaseException){

        }

    }
    fun readData(dataquery: String):MutableList<CouponStatus>{
        val couponStatuses : MutableList<CouponStatus> = ArrayList()
        try {
            val db = this.readableDatabase
            val result = db.rawQuery(dataquery,null)
            if(result.moveToFirst()){
                do{
                    val weekday = result.getString(result.getColumnIndex(COL_DAY))
                    val breakfast = result.getString(result.getColumnIndex(COL_BREAKFAST))
                    val lunch = result.getString(result.getColumnIndex(COL_LUNCH))
                    val dinner = result.getString(result.getColumnIndex(COL_DINNER))
                    val couponStatus = CouponStatus(weekday,breakfast,lunch,dinner)
                    couponStatuses.add(couponStatus)


                }while (result.moveToNext())
            }
            result.close()
            db.close()
            System.out.println("inside:$couponStatuses")

        }
        catch (e:SQLiteCantOpenDatabaseException){
            e.printStackTrace()
        }
        catch (e: SQLiteReadOnlyDatabaseException){
            e.printStackTrace()
        }
        return couponStatuses
    }
    fun updatenotice(couponStatus: CouponStatus){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_BREAKFAST,couponStatus.breakfast)
        cv.put(COL_LUNCH,couponStatus.lunch)
        cv.put(COL_DINNER,couponStatus.dinner)
        db.update(TABLE_NAME,cv,"$COL_DAY = ?", arrayOf(couponStatus.weekday))
        db.close()
    }

}