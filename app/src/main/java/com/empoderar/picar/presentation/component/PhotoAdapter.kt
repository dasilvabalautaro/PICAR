package com.empoderar.picar.presentation.component

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.empoderar.picar.R
import com.empoderar.picar.domain.data.Photo
import com.empoderar.picar.presentation.extension.inflate
import com.empoderar.picar.presentation.navigation.Navigator
import kotlinx.android.synthetic.main.view_row_image.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class PhotoAdapter @Inject constructor():
        RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    internal var collection: List<Photo> by Delegates.observable(emptyList()) {
        _, _, _ -> notifyDataSetChanged()
    }

    private var clickListener: (Photo, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.view_row_image))

    override fun onBindViewHolder(viewHolder: ViewHolder,
                                  position: Int) =
            viewHolder.bind(collection[position], clickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(photo: Photo, clickListener: (Photo, Navigator.Extras) -> Unit) {
            itemView.iv_photo.setImageBitmap(photo.image)
            itemView.setOnClickListener {
                clickListener(photo,
                        Navigator.Extras(itemView.iv_photo))

            }
        }
    }

}