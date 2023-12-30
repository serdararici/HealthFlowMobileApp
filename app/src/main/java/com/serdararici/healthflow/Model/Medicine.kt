package com.serdararici.healthflow.Model

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable
import java.sql.Time
import java.util.Date

@IgnoreExtraProperties
data class Medicine(var medicineId:String?="",
                    var medicineName:String?="",
                    var medicineDetails:String?="",
                    var medicineOfNumber:String?="",
                    var medicineDate:String?="",
                    var medicineTime:String?=""): Serializable{

}