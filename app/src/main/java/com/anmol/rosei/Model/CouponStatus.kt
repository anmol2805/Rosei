package com.anmol.rosei.Model

class CouponStatus{
    var weekday:String?=null
    var breakfast:String?=null
    var lunch:String?=null
    var dinner:String?=null

    constructor()
    constructor(weekday: String?, breakfast: String?, lunch: String?, dinner: String?) {
        this.weekday = weekday
        this.breakfast = breakfast
        this.lunch = lunch
        this.dinner = dinner
    }

}