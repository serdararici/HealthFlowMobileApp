package com.serdararici.healthflow.ViewModel

import androidx.lifecycle.ViewModel
import com.serdararici.healthflow.Repository.MedicineRepository

class MedicineUpdateViewModel:ViewModel() {
    val medicineRepo = MedicineRepository()

    fun medicineUpdateViewModel(medicineId: String, medicineName: String, medicineDetails: String, medicineNumbers: String, medicineDate: String, medicineTime: String){
        medicineRepo.medicineUpdate(medicineId,medicineName,medicineDetails,medicineNumbers,medicineDate,medicineTime)
    }
}