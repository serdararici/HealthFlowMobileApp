package com.serdararici.healthflow.Model

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class Diabetes(var diabetesId:String?="",
                    var diabetesResult: String?="",
                    var hungryLevel: String?="",
                    var diabetesDate: String?="",
                    var diabetesTime: String?="") :Serializable {
}