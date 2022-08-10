package com.hxl.arithcalc.presentation.fragment.equation_history

import android.annotation.SuppressLint
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hxl.arithcalc.databinding.ItemHistoryBinding
import com.hxl.domain.models.Equation

class EquationHistoryRecyclerAdapter(private val equationHistory: List<Equation>) :
    RecyclerView.Adapter<EquationHistoryRecyclerAdapter.ViewHolder>() {
    class ViewHolder(internal val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val equation = equationHistory[position]

        holder.binding.equation = equation.equation
        holder.binding.evaluation = equation.evaluation
        val value = TypedValue()
        when(position % 2){
            0 -> {
                holder.binding.root.context.theme.resolveAttribute(com.google.android.material.R.attr.colorSurface,value, true)
            }
            else -> {
                holder.binding.root.context.theme.resolveAttribute(com.google.android.material.R.attr.colorSecondaryContainer,value, true)
            }
        }
        holder.binding.itemHolder.setBackgroundColor(value.data)
    }

    override fun getItemCount(): Int {
        return equationHistory.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}