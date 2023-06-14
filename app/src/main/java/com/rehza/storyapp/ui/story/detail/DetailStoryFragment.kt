package com.rehza.storyapp.ui.story.detail

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.rehza.storyapp.databinding.FragmentDetailStoryBinding

class DetailStoryFragment : Fragment() {

    private lateinit var binding: FragmentDetailStoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailStoryBinding.inflate(inflater, container, false)

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(DetailStoryFragmentDirections.actionDetailStoryFragmentToListStoryFragment())
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = requireArguments()
        val storyName = args.getString("storyName")
        val storyDesc = args.getString("storyDesc")
        val storyPhotoUrl = args.getString("storyPhotoUrl")

        binding.tvDetailName.text = storyName
        binding.tvDetailDescription.text = storyDesc

        Glide.with(binding.root)
            .load(storyPhotoUrl)
            .centerCrop()
            .into(binding.ivDetailPhoto)
    }

}