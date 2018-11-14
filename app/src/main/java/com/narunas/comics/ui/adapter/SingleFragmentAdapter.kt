package com.narunas.comics.ui.adapter

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.narunas.comics.Details
import com.narunas.comics.Details.Companion.ACT_IMG
import com.narunas.comics.R
import com.narunas.comics.gson.Comic
import com.narunas.comics.ui.common.BaseImageView
import com.narunas.comics.viemodel.CommonViewModel.Companion.ComicsSetInReview
import com.narunas.comics.viemodel.TopSection

class SingleFragmentAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var dataSet =  HashMap<Int, TopSection>()


    fun updateData(data:  HashMap<Int, TopSection>) {

        dataSet = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_section, viewGroup, false)
        return SingleSectionViewHolder(v)
    }

    override fun getItemCount(): Int {
       return dataSet.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(holder is SingleSectionViewHolder) {

            val comics = dataSet.get(position)?.comicsSet?.data?.comics
            if(comics != null) {
                holder.sectionTitle.text = dataSet.get(position)?.title

                val gridAdapter = GridAdapter()
                gridAdapter.updateData(comics)

                val gridLm = GridLayoutManager(
                    holder.itemView.context,
                    holder.itemView.resources.getInteger(R.integer.grid_count),
                    GridLayoutManager.VERTICAL,
                    false)

                holder.grid.layoutManager = gridLm
                holder.grid.adapter = gridAdapter
            } else {
                holder.sectionTitle.text = " Section data is not loaded"
            }
        }
    }

    inner class SingleSectionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var grid: RecyclerView
        var sectionTitle: TextView

        init {

            grid = itemView.findViewById(R.id.image_grid)
            sectionTitle = itemView.findViewById(R.id.section_title)
        }

    }

    class GridAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private var dataSet = ArrayList<Comic>()

        fun updateData(data: ArrayList<Comic>) {
            dataSet = data
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(view: ViewGroup, p1: Int): RecyclerView.ViewHolder {
            val v = LayoutInflater.from(view.context).inflate(R.layout.card_thumbnail, view, false)
            return ImageViewHolder(v)
        }

        override fun getItemCount(): Int {
            return dataSet.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            if(holder is ImageViewHolder) {
                val comic = dataSet[position]

                val url = StringBuffer()
                url.append(comic.thumb.path)
                url.append(".")
                url.append(comic.thumb.extention)

                holder.image.imageSource(url.toString(), true)
                holder.title.text = comic.title

                holder.image.setOnClickListener {

                        ComicsSetInReview.postValue(dataSet)
                        val intent = Intent(holder.itemView.context, Details::class.java)
                        intent.putExtra(ACT_IMG, position)
                        holder.itemView.context.startActivity(intent)
                }
            }
        }

        inner class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

            var image: BaseImageView
            var title: TextView

            init {
                image = itemView.findViewById(R.id.thumb_image)
                title = itemView.findViewById(R.id.thumb_title)
            }

        }
    }
}