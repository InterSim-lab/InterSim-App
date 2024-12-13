package com.taoc.intersim_test.ui.account

import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.taoc.intersim_app.R
import com.taoc.intersim_app.databinding.FragmentAccountBinding
import com.taoc.intersim_test.data.pref.UserPreferences
import com.taoc.intersim_test.login.LoginActivity
import kotlinx.coroutines.launch

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var userPreferences: UserPreferences
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            binding.ivPhotoProfile.setImageURI(it)
            saveProfileImageUri(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userPreferences = UserPreferences(requireContext())

        loadUserData()
        loadProfileImage()
        setupLogoutButton()
        setupProfileImageClick()
    }

    private fun setupProfileImageClick() {
        binding.ivPhotoProfile.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }
    }

    private fun loadUserData() {
        lifecycleScope.launch {
            userPreferences.userDetails.collect { (fullName, email) ->
                binding.apply {
                    ivPhotoProfile.setImageResource(R.drawable.image_account_circle)
                    tvName.text = fullName
                    tvEmail.text = email
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun loadProfileImage() {
        lifecycleScope.launch {
            userPreferences.profileImageUri.collect { uriString ->
                if (!uriString.isNullOrEmpty()) {
                    try{
                        val uri = Uri.parse(uriString)
                        val source = ImageDecoder.createSource(requireContext().contentResolver, uri)
                        val drawable = ImageDecoder.decodeDrawable(source)
                        binding.ivPhotoProfile.setImageDrawable(drawable)
                    } catch (e: Exception) {
                        binding.ivPhotoProfile.setImageResource(R.drawable.image_account_circle)
                        e.printStackTrace()
                    }
                } else {
                    binding.ivPhotoProfile.setImageResource(R.drawable.image_account_circle)
                }
            }
        }
    }

    private fun saveProfileImageUri(uri: Uri) {
        lifecycleScope.launch {
            userPreferences.saveProfileImageUri(uri.toString())
        }
    }

    private fun setupLogoutButton() {
        binding.tvLogout.setOnClickListener {
            lifecycleScope.launch {
                userPreferences.clearUserData()

                startActivity(Intent(requireActivity(), LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
                requireActivity().finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}