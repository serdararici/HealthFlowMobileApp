package com.serdararici.healthflow.Repository

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.serdararici.healthflow.Model.Medicine
import com.serdararici.healthflow.adapter.MedicineAdapter
import kotlinx.coroutines.withContext

class MedicineRepository {
    private val database = Firebase.firestore
    var medicineList = ArrayList<Medicine>()
    val userEmail = FirebaseAuth.getInstance().currentUser?.email as Any

    /*var medicineListLive:MutableLiveData<ArrayList<Medicine>>
    init{
        medicineListLive = MutableLiveData()
    }*/

    fun addMedicineRepository(medicineName: String, medicineDetails: String, medicineNumbers: String, medicineDate: String, medicineTime: String,
                    onComplete: (Boolean) -> Unit){
        val medicineHashMap = hashMapOf<String, Any>(
            "userEmail" to userEmail,
            "medicineName" to medicineName,
            "medicineDetails" to medicineDetails,
            "medicineNumbers" to medicineNumbers,
            "medicineDate" to medicineDate,
            "medicineTime" to medicineTime,
            "addedDate" to Timestamp.now())

        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        database.collection("medicine")
            .add(medicineHashMap)
            .addOnCompleteListener {task->
                if (task.isSuccessful){
                    onComplete(true)
                }
            }.addOnFailureListener {
                onComplete(false)
            }
    }

    /*fun getMedcines() : MutableLiveData<ArrayList<Medicine>> {
        return medicineListLive
    }*/

    fun medicineSearch(searchingWord:String){
        database.collection("medicine").orderBy("addedDate",Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                if(exception !=null) {

                } else {
                    if(snapshot!=null){
                        if(!snapshot.isEmpty){
                            val documents = snapshot.documents

                            medicineList.clear()

                            for (document in documents) {
                                val userEmail = document.get("userEmail") as String
                                val medicineName = document.get("medicineName") as String
                                val medicineDetails = document.get("medicineDetails") as String
                                val medicineNumbers = document.get("medicineNumbers") as String
                                val medicineDate = document.get("medicineDate") as String
                                val medicineTime = document.get("medicineTime") as String
                                val medicineId = document.id
                                val medicines = Medicine(medicineId,medicineName,medicineDetails,medicineNumbers,medicineDate,medicineTime)
                                if(medicines.medicineName!!.lowercase().contains(searchingWord.lowercase())){
                                    medicineList.add(medicines)
                                }
                                //medicineListLive.value = medicineList
                            }
                        }
                    }
                }
            }
    }
    fun medicineDelete(medicineId:String){
        database.collection("medicine").document(medicineId).delete()
    }
    fun medicineUpdate(medicineId: String, medicineName: String, medicineDetails: String, medicineNumbers: String, medicineDate: String, medicineTime: String,){
        val medicineHashMap = hashMapOf<String, Any>(
            "userEmail" to userEmail,
            "medicineName" to medicineName,
            "medicineDetails" to medicineDetails,
            "medicineNumbers" to medicineNumbers,
            "medicineDate" to medicineDate,
            "medicineTime" to medicineTime,
            "addedDate" to Timestamp.now())
        database.collection("medicine").document(medicineId).update(medicineHashMap)
    }

    fun getMedicineRepository(onComplete: (Boolean, String?) -> Unit) {

        //.whereEqualTo("userEmail",userEmail)
        database.collection("medicine").whereEqualTo("userEmail",userEmail).orderBy("addedDate",Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
            if(exception !=null) {
                onComplete(false, exception?.message)
            } else {
                if(snapshot!=null){
                    if(!snapshot.isEmpty){
                        val documents = snapshot.documents

                        medicineList.clear()

                        for (document in documents) {
                            val userEmail = document.get("userEmail") as String
                            val medicineName = document.get("medicineName") as String
                            val medicineDetails = document.get("medicineDetails") as String
                            val medicineNumbers = document.get("medicineNumbers") as String
                            val medicineDate = document.get("medicineDate") as String
                            val medicineTime = document.get("medicineTime") as String

                            val medicineId = document.id
                            val medicines = Medicine(medicineId,medicineName,medicineDetails,medicineNumbers,medicineDate,medicineTime)
                            medicineList.add(medicines)
                            //medicineListLive.value = medicineList

                        }
                        onComplete(true, "")
                    }
                }
            }
        }
    }
}