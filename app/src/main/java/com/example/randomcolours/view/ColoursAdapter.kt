package com.example.randomcolours.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.randomcolours.R
import com.example.randomcolours.common.model.ColoursWordEntity
import kotlinx.android.synthetic.main.colour_item.view.*

class ColoursAdapter(private val coloursWordList: List<ColoursWordEntity>) :
    RecyclerView.Adapter<ColoursAdapter.ColourViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColourViewHolder {
        return ColourViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.colour_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return coloursWordList.size
    }

    override fun onBindViewHolder(holder: ColourViewHolder, position: Int) {
        holder.item.tv_name.text = coloursWordList[position].colourName
        holder.item.tv_hexaDecimal.text = coloursWordList[position].hexadecimal
        holder.item.tv_colourPatch.setBackgroundColor(Color.parseColor(coloursWordList[position].hexadecimal))
    }

    class ColourViewHolder(val item: View) : RecyclerView.ViewHolder(item)
}
