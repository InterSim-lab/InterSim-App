package com.taoc.intersim_test.ui.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taoc.intersim_app.R
import com.taoc.intersim_app.databinding.FragmentAccountBinding
import com.taoc.login.LoginActivity

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadUserData()
        setupLogoutButton()
    }

    private fun loadUserData() {
        val sharedPref = requireActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        binding.apply {
            userName.text = sharedPref.getString("FULL_NAME", "No Name")
            userEmail.text = sharedPref.getString("EMAIL", "No Email")
        }
    }

    private fun setupLogoutButton() {
        binding.logoutButton.setOnClickListener {
            requireActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("IS_LOGGED_IN", false)
                .apply()

            startActivity(Intent(requireActivity(), LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}