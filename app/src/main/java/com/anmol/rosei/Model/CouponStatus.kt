package com.anmol.rosei.Model

import java.lang.StringBuilder

class CouponStatus{
    var weekday:String?=null
    var breakfast:StringBuilder?=null
    var lunch:StringBuilder?=null
    var dinner:StringBuilder?=null

    constructor()
    constructor(weekday: String?, breakfast: StringBuilder?, lunch: StringBuilder?, dinner: StringBuilder?) {
        this.weekday = weekday
        this.breakfast = breakfast
        this.lunch = lunch
        this.dinner = dinner
    }

}