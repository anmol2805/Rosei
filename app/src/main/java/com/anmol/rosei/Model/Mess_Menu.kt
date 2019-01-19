package com.anmol.rosei.Model

class Mess_Menu{
    var weekday:String?= null
    var breakfast:String?=null
    var lunch:String?=null
    var dinner:String?=null
    var date:String?=null

    constructor()
    constructor(weekday: String?, breakfast: String?, lunch: String?, dinner: String?,date:String?) {
        this.weekday = weekday
        this.breakfast = breakfast
        this.lunch = lunch
        this.dinner = dinner
        this.date = date
    }
}