package com.serdararici.healthflow.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.Query
import com.serdararici.healthflow.Model.Medicine
import com.serdararici.healthflow.Repository.MedicineRepository

class MedicineViewModel : ViewModel() {
    val medicineRepo = MedicineRepository()
    var medicineList = ArrayList<Medicine>()
    /*
    var medicineListLive = MutableLiveData<ArrayList<Medicine>>()
    init {
        getMedicineViewModel(){s,m->}
        medicineListLive = medicineRepo.getMedcines()
    }*/

    fun medicineDeleteViewModel(medicineId: String){
        medicineRepo.medicineDelete(medicineId)
    }

    fun getMedicineViewModel(onComplete: (Boolean, String?) -> Unit) {

        medicineRepo.getMedicineRepository(){ succes, message ->
            onComplete(succes, message)
            medicineList = medicineRepo.medicineList
        }
    }

}