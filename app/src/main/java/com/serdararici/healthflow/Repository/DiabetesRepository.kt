package com.serdararici.healthflow.Repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.serdararici.healthflow.Model.Diabetes
import com.serdararici.healthflow.Model.Medicine
import com.serdararici.healthflow.Model.Profile

class DiabetesRepository {
    private val database = Firebase.firestore
    var diabetesList = ArrayList<Diabetes>()
    val userEmail = FirebaseAuth.getInstance().currentUser?.email as Any
    var diabetesListLive:MutableLiveData<List<Diabetes>>
    init{
        diabetesListLive = MutableLiveData()
    }

    fun getDiabetesLive():MutableLiveData<List<Diabetes>>{
        return diabetesListLive
    }
    fun addDiabetes(diabetesResult:String, hungryLevel:String, diabetesDate:String, diabetesTime:String){
        val diabetesHashMap = hashMapOf<String, Any>(
            "userEmail" to userEmail,
            "diabetesResult" to diabetesResult,
            "hungryLevel" to hungryLevel,
            "diabetesDate" to diabetesDate,
            "diabetesTime" to diabetesTime,
            "addedDate" to Timestamp.now())

        database.collection("diabetes").add(diabetesHashMap)
    }
    fun deleteDiabetes(diabetesId: String){
        database.collection("diabetes").document(diabetesId).delete()
    }
    fun getAllDiabetes(){

        val userEmail = FirebaseAuth.getInstance().currentUser?.email as Any
        database.collection("diabetes").whereEqualTo("userEmail", userEmail)
            .addSnapshotListener { snapshot, exception ->
                if(exception!=null){

                }else{
                    if(snapshot!=null){
                        if(!snapshot.isEmpty){
                            val documents = snapshot.documents

                            diabetesList.clear()

                            for (document in documents) {
                                val userEmail = document.get("userEmail") as String
                                val diabetesResult = document.get("diabetesResult") as String
                                val hungryLevel = document.get("hungryLevel") as String
                                val diabetesDate = document.get("diabetesDate") as String
                                val diabetesTime = document.get("diabetesTime") as String
                                val diabetesId = document.id

                                val diabetes = Diabetes(diabetesId,diabetesResult,hungryLevel,diabetesDate,diabetesTime)
                                diabetesList.add(diabetes)
                            }
                            diabetesListLive.postValue(diabetesList)
                        }
                    }
                }
            }
    }
}