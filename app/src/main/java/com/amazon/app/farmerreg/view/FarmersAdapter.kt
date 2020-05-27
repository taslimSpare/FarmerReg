package com.amazon.app.farmerreg.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amazon.app.farmerreg.R
import com.amazon.app.farmerreg.model.pojo.FarmerProfile
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView



class FarmersAdapter(_context: Context, farmers: List<FarmerProfile>) : RecyclerView.Adapter<FarmersAdapter.ViewHolder>() {


    var mData: List<FarmerProfile> = farmers
    var mInflater: LayoutInflater = LayoutInflater.from(_context)
    var mClickListener: ItemClickListener? = null
    var context: Context = _context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = mInflater.inflate(R.layout.farmer_card, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.farmerName.text = mData[position].farmName
        holder.farmerLocation.text = mData[position].farmLocation
        Glide
            .with(context)
            .load(mData[position].farmerImageUrl)
            .centerCrop()
            .placeholder(R.drawable.farmer)
            .into(holder.circleImageView)
    }


    override fun getItemCount(): Int {
        return mData.size
    }



    inner class ViewHolder : RecyclerView.ViewHolder {

        lateinit var farmerName: TextView
        lateinit var farmerLocation: TextView
        lateinit var circleImageView: CircleImageView

        constructor(itemView: View) : super(itemView) {
            farmerName = itemView.findViewById(R.id.tv_name)
            farmerLocation = itemView.findViewById(R.id.tv_location)
            circleImageView = itemView.findViewById(R.id.iv_profile_picture)

            itemView.setOnClickListener { if(mClickListener != null) mClickListener!!.onItemClick(it, adapterPosition) }
        }


    }


    public fun setClickListener(itemClickListener: ItemClickListener) {
        this.mClickListener = itemClickListener
    }


    public interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}