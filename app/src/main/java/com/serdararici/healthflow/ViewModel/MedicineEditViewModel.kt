package com.serdararici.healthflow.ViewModel

import androidx.lifecycle.ViewModel
import com.serdararici.healthflow.Repository.MedicineRepository

class MedicineEditViewModel : ViewModel(){
    val medicineRepo = MedicineRepository()

    fun addMedicineViewModel(medicineName: String, medicineDetails: String, medicineNumbers: String, medicineDate: String, medicineTime: String,
                    onComplete: (Boolean) -> Unit){
        medicineRepo.addMedicineRepository(medicineName,medicineDetails,medicineNumbers,medicineDate,medicineTime){success ->
            onComplete(success)
        }
    }
}