package com.serdararici.healthflow.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.serdararici.healthflow.Model.Diabetes
import com.serdararici.healthflow.R
import com.serdararici.healthflow.ViewModel.DiabetesViewModel
import com.serdararici.healthflow.databinding.DiabetesRecyclerRowBinding
import com.serdararici.healthflow.databinding.MedicineRecyclerRowBinding

class DiabetesAdapter(var context:Context,
                      var diabetesList: List<Diabetes>,
                      var viewModel: DiabetesViewModel)
    : RecyclerView.Adapter<DiabetesAdapter.DiabetesViewHolder>(){
    inner class DiabetesViewHolder(binding: DiabetesRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){
        var binding:DiabetesRecyclerRowBinding
        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiabetesViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = DiabetesRecyclerRowBinding.inflate(layoutInflater, parent, false)
        return DiabetesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return diabetesList.size
    }

    override fun onBindViewHolder(holder: DiabetesViewHolder, position: Int) {
        val diabetes = diabetesList.get(position)
        holder.binding.tvDiabetesResult.text = diabetes.diabetesResult
        holder.binding.tvHungryLevel.text = diabetes.hungryLevel
        val medicineDateandTime = diabetes.diabetesDate.toString() + " - " + diabetes.diabetesTime.toString()
        holder.binding.tvDateAndTime.text = medicineDateandTime
        holder.binding.cardViewDiabetesRow.setOnClickListener{
            val ad = AlertDialog.Builder(context)
            var message =  context.getString(R.string.bloodSugarResult) + " " + diabetes.diabetesResult + "\n" + diabetes.hungryLevel + "\n" + medicineDateandTime
            ad.setTitle(R.string.bloodSugarValue)
            ad.setMessage(message)
            ad.setIcon(R.drawable.baseline_playlist_add_circle_24)
            ad.setNegativeButton(R.string.close) { dialogInterface, i ->

            }
            ad.create().show()
        }
        holder.binding.imageViewDelete.setOnClickListener {
            Snackbar.make(it, "${diabetes.diabetesResult} " + context.getString(R.string.willDiabetesDelete), Snackbar.LENGTH_LONG)
                .setAction(R.string.yes){
                    viewModel.deleteDiabetesViewModel(diabetes.diabetesId!!)
                }.show()
        }

    }
}