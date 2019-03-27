package br.com.felipewisniewski.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.felipewisniewski.model.NetCat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_cat_layout.view.*

class MainAdapter(private val listOfCats: List<NetCat>?) : RecyclerView.Adapter<MainAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_cat_layout, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (listOfCats != null) {
            Glide.with(holder.itemView.context)
                .load(listOfCats[position].url)
                .centerCrop()
                .into(holder.itemView.imageCats)
        }
    }

    override fun getItemCount() = listOfCats?.size ?: 0

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView)
}