package com.serdararici.healthflow.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.serdararici.healthflow.Model.Diabetes
import com.serdararici.healthflow.Repository.DiabetesRepository

class DiabetesViewModel: ViewModel() {
    val diabetesRepo = DiabetesRepository()
    var diabetesListLive = MutableLiveData<List<Diabetes>>()
    init{
        getAllDiabetesViewModel()
        diabetesListLive = diabetesRepo.getDiabetesLive()
    }

    fun deleteDiabetesViewModel(diabetesId: String){
        diabetesRepo.deleteDiabetes(diabetesId)
    }
    fun getAllDiabetesViewModel(){
        diabetesRepo.getAllDiabetes()
    }
}