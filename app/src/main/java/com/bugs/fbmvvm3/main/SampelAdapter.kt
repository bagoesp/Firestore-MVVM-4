package com.bugs.fbmvvm3.main

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bugs.fbmvvm3.databinding.ItemSampelBinding
import com.bugs.fbmvvm3.model.Sampel

class SampelAdapter(
    private val activity: MainActivity,
    private val sampelList: List<Sampel>
) : RecyclerView.Adapter<SampelAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSampelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sampelList[position])
    }

    override fun getItemCount(): Int = sampelList.size

    inner class ViewHolder(private val binding: ItemSampelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(sampel: Sampel){
            binding.tvLabel.text = sampel.label.toString()
            binding.tvAp1.text = sampel.ap1.toString()
            binding.tvAp2.text = sampel.ap2.toString()
            binding.tvAp3.text = sampel.ap3.toString()
            //binding.tvCreation.text = DateUtils.getRelativeTimeSpanString(sampel.creation!!)
            if (sampel.creation != null){
                binding.tvCreation.text = DateUtils.getRelativeTimeSpanString(sampel.creation)
            }
            else binding.tvCreation.text = "somewhen"

            binding.btnDelete.setOnClickListener {
                activity.delSampel(sampel)
            }
        }
    }
}