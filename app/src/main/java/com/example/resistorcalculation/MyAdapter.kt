package com.example.resistorcalculation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list.view.*

class MyAdapter(private val allhistory: ArrayList<AllHistory>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val key_id = itemView.number
        val voltage  = itemView.text1
        val f_voltage  = itemView.text2
        val current = itemView.text3
        val n_o_leds = itemView.text4
        val resistence = itemView.text5
        val min_power  = itemView.text6
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): MyViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val history = allhistory[position]

        holder.key_id.text = history.hid.toString()
        holder.voltage.text = "%.3f".format(history.voltage)
        holder.f_voltage.text = "%.3f".format(history.f_voltage)
        holder.current.text = "%.3f".format(history.current)
        holder.n_o_leds.text = history.n_o_leds.toString()
        holder.resistence.text = "%.3f".format(history.resistence)
        holder.min_power.text = "%.3f".format(history.min_power)
    }

    override fun getItemCount() = allhistory.size
}