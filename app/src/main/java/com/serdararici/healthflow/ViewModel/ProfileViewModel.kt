package com.serdararici.healthflow.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.serdararici.healthflow.Model.Profile
import com.serdararici.healthflow.Repository.ProfileRepository

class ProfileViewModel:ViewModel() {
    val profileRepo = ProfileRepository()
    var profileList = ArrayList<Profile>()
    var profileListLive = MutableLiveData<ArrayList<Profile>>()
    init {
        getProfiles()
        profileListLive = profileRepo.getProfiles()
    }

    fun addProfileViewModel(userName:String, userEmail:String,phoneNumber:String,birthDate:String,height:Double,weight:Double){
        profileRepo.addProfile(userName,userEmail,phoneNumber,birthDate,height,weight)
    }
    fun deleteProfileViewModel(profileId:String){
        profileRepo.deleteProfile(profileId)
    }
    fun getProfiles(){
        profileRepo.getProfile()
    }
    fun getProfileViewModel(){
        profileRepo.getProfile()
        profileList = profileRepo.profileList

    }
    fun caculateAge(birthDate:String):Int{
        return profileRepo.calculateAge(birthDate)
    }
}