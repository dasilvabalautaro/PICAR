package com.empoderar.picar.presentation.component

//import androidx.appcompat.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.empoderar.picar.R
//import com.empoderar.picar.domain.data.Photo
import com.empoderar.picar.presentation.data.PhotoView
import com.empoderar.picar.presentation.extension.inflate
import com.empoderar.picar.presentation.navigation.Navigator
import kotlinx.android.synthetic.main.view_row_image.view.*
//import kotlinx.android.synthetic.main.view_row_image.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class PhotoAdapter @Inject constructor():
        RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    internal var collection: List<PhotoView> by Delegates.observable(emptyList()) {
        _, _, _ -> notifyDataSetChanged()
    }

    private var clickListener: (PhotoView, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.view_row_image))

    override fun onBindViewHolder(viewHolder: ViewHolder,
                                  position: Int) =
            viewHolder.bind(collection[position], clickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(photo: PhotoView, clickListener: (PhotoView, Navigator.Extras) -> Unit) {
            itemView.iv_photo.setImageBitmap(photo.image)
            itemView.setOnClickListener {
                clickListener(photo,
                        Navigator.Extras(itemView.iv_photo))

            }
        }
    }

}