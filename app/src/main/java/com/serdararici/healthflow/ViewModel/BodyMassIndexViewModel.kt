package com.serdararici.healthflow.ViewModel

import androidx.lifecycle.ViewModel

class BodyMassIndexViewModel:ViewModel() {

    fun calculateBodyMassIndex(height:Double, weight:Double): Double{
        var heightToMetre = height/100
        var bmi = weight / (heightToMetre*heightToMetre)
        return bmi
    }
}