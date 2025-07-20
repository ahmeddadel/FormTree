package com.lumiform.formtree.feature.contentitem.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lumiform.domain.model.ContentItemModel
import com.lumiform.formtree.R
import com.lumiform.formtree.utils.setOnClickListenerWithDebounce

/**
 * @created 19/07/2025 - 2:13 PM
 * @project FormTree
 * @author adell
 */
class ContentItemAdapter(private val onImageClick: (String, String) -> Unit) :
    ListAdapter<DisplayItem, RecyclerView.ViewHolder>(DiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).item) {
            is ContentItemModel.Page -> 0
            is ContentItemModel.Section -> 1
            is ContentItemModel.TextQuestion -> 2
            is ContentItemModel.ImageQuestion -> 3
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> PageViewHolder(
                inflater.inflate(
                    R.layout.item_page,
                    parent,
                    false
                )
            )

            1 -> SectionViewHolder(
                inflater.inflate(
                    R.layout.item_section,
                    parent,
                    false
                )
            )

            2 -> TextQuestionViewHolder(
                inflater.inflate(
                    R.layout.item_text_question,
                    parent,
                    false
                )
            )

            3 -> ImageQuestionViewHolder(
                inflater.inflate(
                    R.layout.item_image_question,
                    parent,
                    false
                ), onImageClick
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is PageViewHolder -> holder.bind(item.item as ContentItemModel.Page, item.depth)
            is SectionViewHolder -> holder.bind(item.item as ContentItemModel.Section, item.depth)
            is TextQuestionViewHolder -> holder.bind(
                item.item as ContentItemModel.TextQuestion,
                item.depth
            )

            is ImageQuestionViewHolder -> holder.bind(
                item.item as ContentItemModel.ImageQuestion,
                item.depth
            )
        }
    }

    class PageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ContentItemModel.Page, depth: Int) {
            val textView = itemView.findViewById<TextView>(R.id.pageTitle)
            textView.text = item.title
            textView.setTextSize(
                android.util.TypedValue.COMPLEX_UNIT_PX,
                itemView.context.resources.getDimension(com.intuit.ssp.R.dimen._18ssp)
            )
            textView.setPadding(depth * 20, 0, 0, 0)
        }
    }

    class SectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ContentItemModel.Section, depth: Int) {
            val textView = itemView.findViewById<TextView>(R.id.sectionTitle)
            textView.text = item.title
            textView.setTextSize(
                android.util.TypedValue.COMPLEX_UNIT_PX,
                itemView.context.resources.getDimension(com.intuit.ssp.R.dimen._16ssp)
            )
            textView.setPadding(depth * 20, 0, 0, 0)
        }
    }

    class TextQuestionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ContentItemModel.TextQuestion, depth: Int) {
            val textView = itemView.findViewById<TextView>(R.id.textQuestion)
            textView.text = item.title
            textView.setTextSize(
                android.util.TypedValue.COMPLEX_UNIT_PX,
                itemView.context.resources.getDimension(com.intuit.ssp.R.dimen._10ssp)
            )
            textView.setPadding(depth * 20, 0, 0, 0)
        }
    }

    class ImageQuestionViewHolder(view: View, private val onImageClick: (String, String) -> Unit) :
        RecyclerView.ViewHolder(view) {
        fun bind(item: ContentItemModel.ImageQuestion, depth: Int) {
            val imageView = itemView.findViewById<ImageView>(R.id.imageThumbnail)
            val title = itemView.findViewById<TextView>(R.id.imageTitle)

            title.text = item.title
            title.setTextSize(
                android.util.TypedValue.COMPLEX_UNIT_PX,
                itemView.context.resources.getDimension(com.intuit.ssp.R.dimen._10ssp)
            )
            title.setPadding(depth * 20, 0, 0, 0)

            Glide.with(imageView.context)
                .load(item.src)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .override(150, 150)
                .into(imageView)

            imageView.setOnClickListenerWithDebounce {
                onImageClick(item.src, item.title)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<DisplayItem>() {
        override fun areItemsTheSame(
            oldItem: DisplayItem,
            newItem: DisplayItem
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: DisplayItem,
            newItem: DisplayItem
        ) = oldItem == newItem
    }
}
