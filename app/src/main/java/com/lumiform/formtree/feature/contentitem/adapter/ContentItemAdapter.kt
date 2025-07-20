package com.lumiform.formtree.feature.contentitem.adapter

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lumiform.domain.model.ContentItemModel
import com.lumiform.formtree.R
import com.lumiform.formtree.databinding.ItemImageQuestionBinding
import com.lumiform.formtree.databinding.ItemPageBinding
import com.lumiform.formtree.databinding.ItemSectionBinding
import com.lumiform.formtree.databinding.ItemTextQuestionBinding
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
            0 -> PageViewHolder(ItemPageBinding.inflate(inflater, parent, false))

            1 -> SectionViewHolder(ItemSectionBinding.inflate(inflater, parent, false))

            2 -> TextQuestionViewHolder(ItemTextQuestionBinding.inflate(inflater, parent, false))

            3 -> ImageQuestionViewHolder(
                ItemImageQuestionBinding.inflate(inflater, parent, false),
                onImageClick
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

    class PageViewHolder(private val binding: ItemPageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContentItemModel.Page, depth: Int) {
            binding.pageTitle.text = item.title
            binding.pageTitle.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                itemView.context.resources.getDimension(com.intuit.ssp.R.dimen._22ssp)
            )
            binding.pageTitle.setPadding(depth * 20, 0, 0, 0)
        }
    }

    class SectionViewHolder(private val binding: ItemSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContentItemModel.Section, depth: Int) {
            binding.sectionTitle.text = item.title

            // decrease nested section title size based on depth
            val baseSize = itemView.context.resources.getDimension(com.intuit.ssp.R.dimen._20ssp)
            val decreasePerDepth =
                itemView.context.resources.getDimension(com.intuit.ssp.R.dimen._2ssp)
            val finalSize = (baseSize - (depth * decreasePerDepth)).coerceAtLeast(
                itemView.context.resources.getDimension(com.intuit.ssp.R.dimen._13ssp)
            )
            binding.sectionTitle.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                finalSize
            )

            binding.sectionTitle.setPadding(depth * 20, 0, 0, 0)
        }
    }

    class TextQuestionViewHolder(private val binding: ItemTextQuestionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContentItemModel.TextQuestion, depth: Int) {
            binding.textQuestion.text = item.title
            binding.textQuestion.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                itemView.context.resources.getDimension(com.intuit.ssp.R.dimen._12ssp)
            )
            binding.textQuestion.setPadding(depth * 20, 0, 0, 0)
        }
    }

    class ImageQuestionViewHolder(
        private val binding: ItemImageQuestionBinding,
        private val onImageClick: (String, String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContentItemModel.ImageQuestion, depth: Int) {
            binding.imageTitle.text = item.title
            binding.imageTitle.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                itemView.context.resources.getDimension(com.intuit.ssp.R.dimen._12ssp)
            )
            binding.imageTitle.setPadding(depth * 20, 0, 0, 0)

            Glide.with(binding.imageThumbnail.context)
                .load(item.src)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .override(150, 150)
                .into(binding.imageThumbnail)

            binding.imageThumbnail.setOnClickListenerWithDebounce {
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
