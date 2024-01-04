package com.serdararici.healthflow.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.snackbar.Snackbar
import com.serdararici.healthflow.Model.Medicine
import com.serdararici.healthflow.R
import com.serdararici.healthflow.View.MainActivity
import com.serdararici.healthflow.View.MedicineFragmentDirections
import com.serdararici.healthflow.ViewModel.MedicineViewModel
import com.serdararici.healthflow.databinding.MedicineRecyclerRowBinding
import kotlinx.coroutines.withContext

class MedicineAdapter(val context: Context,
                      val medicineList : List<Medicine>,
                      var viewModel: MedicineViewModel)
    : RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> () {
    inner class MedicineViewHolder(val binding: MedicineRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){
        private lateinit var navController: NavController
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val binding = MedicineRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.medicine_recycler_row, parent, false)
        return MedicineViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return medicineList.size
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicine = medicineList[position]
        holder.binding.tvMedicineName.text = medicine.medicineName
        holder.binding.tvMedicineOfNumbers.text = medicine.medicineOfNumber
        val medicineDateandTime = medicine.medicineDate.toString() + " - " + medicine.medicineTime.toString()
        holder.binding.tvMedicineDate.text = medicineDateandTime
        holder.binding.rowCardView.setOnClickListener {
            val ad = AlertDialog.Builder(context)
            ad.setTitle(medicine.medicineName)
            ad.setMessage(medicine.medicineDetails + "\nAdet: " + medicine.medicineOfNumber + "\n" + medicine.medicineTime)
            ad.setIcon(R.drawable.baseline_medical_services_24)
            ad.setNegativeButton(R.string.close) { dialogInterface, i ->

            }
            ad.create().show()
        }
        holder.binding.ivMedicineMore.setOnClickListener {
            val popup = PopupMenu(context, holder.binding.ivMedicineMore)
            popup.menuInflater.inflate(R.menu.medicine_popup_menu,popup.menu)
            popup.show()
            popup.setOnMenuItemClickListener {item ->
                when(item.itemId){
                    R.id.editMedicine -> {
                        val action = MedicineFragmentDirections.actionMedicineFragmentToMedicineUpdateFragment(medicine)
                        Navigation.findNavController(it).navigate(action)
                        true
                    }
                    R.id.deleteMedicine -> {
                        Snackbar.make(it, "${medicine.medicineName} silinsin mi?", Snackbar.LENGTH_LONG)
                            .setAction(R.string.yes){
                                viewModel.medicineDeleteViewModel(medicine.medicineId!!)
                            }.show()
                        true
                    }
                    else -> false
                }
            }
        }

        /*fun medicineListUpdate(newMedicineList: List<Medicine>) {
            medicineList.clear()
            medicineList.addAll(newMedicineList)
            notifyDataSetChanged()
        }*/
    }
}