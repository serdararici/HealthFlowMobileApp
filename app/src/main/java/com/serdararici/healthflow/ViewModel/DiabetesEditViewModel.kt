package com.serdararici.healthflow.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.serdararici.healthflow.Model.Diabetes
import com.serdararici.healthflow.Repository.DiabetesRepository

class DiabetesEditViewModel: ViewModel() {
    val diabetesRepo = DiabetesRepository()

    fun addDiabetesViewModel(diabetesResult:String, hungryLevel:String, diabetesDate:String, diabetesTime:String){
        diabetesRepo.addDiabetes(diabetesResult,hungryLevel,diabetesDate,diabetesTime)
    }

}