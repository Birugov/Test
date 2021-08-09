package ru.techcrat.test.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.techcrat.test.R
import ru.techcrat.test.data.Area
import ru.techcrat.test.databinding.AreasRecyclerElemBinding

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Area>() {
    override fun areItemsTheSame(oldItem: Area, newItem: Area) =
        oldItem.areaName == newItem.areaName

    override fun areContentsTheSame(oldItem: Area, newItem: Area) =
        oldItem == newItem
}

class AreasAdapter(private val listener: OnDeleteClickListener) : ListAdapter<Area, AreasAdapter.AreasViewHolder>(DIFF_CALLBACK) {

    inner class AreasViewHolder(private val container: AreasRecyclerElemBinding) :
        RecyclerView.ViewHolder(container.root),  View.OnClickListener {
        fun bind(area: Area){
            container.tvAreaName.text = area.areaName
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onDeleteClick(position)
            }
        }

        init {
            container.deleteBtn.setOnClickListener(this)
        }
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreasViewHolder {
        return AreasViewHolder(
            AreasRecyclerElemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AreasViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}
