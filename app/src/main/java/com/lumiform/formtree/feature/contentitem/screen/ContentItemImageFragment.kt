package com.lumiform.formtree.feature.contentitem.screen

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.lumiform.formtree.R
import com.lumiform.formtree.databinding.FragmentContentItemImageBinding
import com.lumiform.formtree.utils.setDebouncedClickListener
import com.lumiform.formtree.utils.showCustomAlertDialog


/**
 * @created 19/07/2025 - 4:59 AM
 * @project FormTree
 * @author adell
 */
class ContentItemImageFragment : Fragment() {

    private lateinit var binding: FragmentContentItemImageBinding
    private val args: ContentItemImageFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContentItemImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTitle.text = args.title


        Glide.with(requireContext())
            .load(args.imageUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    requireActivity().showCustomAlertDialog(
                        title = getString(R.string.oops),
                        message = getString(R.string.error_loading_image),
                        onOkClick = {
                            findNavController().navigateUp()
                        },
                    )

                    return true // prevent Glide from setting error placeholder
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: com.bumptech.glide.request.target.Target<Drawable?>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    return false // let Glide handle displaying the image
                }
            })
            .into(binding.imageView)

        binding.btnBack.setDebouncedClickListener {
            findNavController().navigateUp()
        }
    }
}