package com.serdararici.healthflow.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.Query
import com.serdararici.healthflow.Model.Medicine
import com.serdararici.healthflow.Repository.MedicineRepository

class MedicineViewModel : ViewModel() {
    val medicineRepo = MedicineRepository()
    var medicineList = ArrayList<Medicine>()

    var medicineListLive = MutableLiveData<List<Medicine>>()
    init {
        getMedicineViewModel(){s,m->}
        medicineListLive = medicineRepo.getMedicineLive()
    }

    fun medicineDeleteViewModel(medicineId: String){
        medicineRepo.medicineDelete(medicineId)
    }
    fun medicineSearchViewModel(searchingWord:String){
        medicineRepo.medicineSearch(searchingWord)
    }

    fun getMedicineViewModel(onComplete: (Boolean, String?) -> Unit) {

        medicineRepo.getMedicineRepository(){ succes, message ->
            onComplete(succes, message)
            //medicineList = medicineRepo.medicineList
        }
    }

}