package com.serdararici.healthflow.Model

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class Profile(var profileId:String?="",
                   var userName: String?="",
                   var userEmail: String?="",
                   var phoneNumber:String?="",
                   var birthDate:String?="",
                   var height:Double?=0.0,
                   var weight:Double?=0.0) : Serializable {
}