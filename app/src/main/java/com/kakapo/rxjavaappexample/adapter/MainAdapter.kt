package com.kakapo.rxjavaappexample.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kakapo.rxjavaappexample.databinding.RecyclerItemLayoutBinding
import com.kakapo.rxjavaappexample.model.Crypto

class MainAdapter(private val context: Context) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var list: List<Crypto.Market>? = null

    inner class ViewHolder(view: RecyclerItemLayoutBinding) : RecyclerView.ViewHolder(view.root){
        val txtCoins = view.txtCoin
        val txtMarket = view.txtMarket
        val txtPrice = view.txtPrice
        val cardView = view.cardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerItemLayoutBinding
            .inflate(LayoutInflater.from(context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list?.let {
            val list = it[position]
            holder.txtCoins.text = list.coinName
            holder.txtMarket.text = list.market
            holder.txtPrice.text = list.price

            if(list.coinName!! == "eth"){
                holder.cardView.setCardBackgroundColor(Color.GRAY)
            }else{
                holder.cardView.setCardBackgroundColor(Color.GREEN)
            }
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Crypto.Market>){
        list = data
        notifyDataSetChanged()
    }
}