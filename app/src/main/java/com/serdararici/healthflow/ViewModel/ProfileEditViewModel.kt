package com.serdararici.healthflow.ViewModel

import androidx.lifecycle.ViewModel
import com.serdararici.healthflow.Repository.ProfileRepository

class ProfileEditViewModel:ViewModel() {
    val profileRepo = ProfileRepository()

    fun updateProfileViewModel(profileId:String,userName:String, userEmail:String,phoneNumber:String,birthDate:String,height:Double,weight:Double){
        profileRepo.updateProfile(profileId,userName,userEmail,phoneNumber,birthDate,height,weight)
    }
}