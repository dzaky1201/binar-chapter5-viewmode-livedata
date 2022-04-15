package com.dzakyhdr.viewmodel.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dzakyhdr.viewmodel.R
import com.dzakyhdr.viewmodel.databinding.FragmentLoginUserBinding
import com.dzakyhdr.viewmodel.utils.SharedPreference
import com.google.android.material.snackbar.Snackbar


class LoginUserFragment : Fragment() {

    private var _binding: FragmentLoginUserBinding? = null
    private val binding get() = _binding!!
    private var sharedPref: SharedPreference? = null
    private lateinit var viewModel: LoginViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginUserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPreference(view.context)

        viewModelFactory = LoginViewModelFactory(sharedPref!!)
        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[LoginViewModel::class.java]

        viewModel.loginSession()
        viewModel.moveToHome.observe(viewLifecycleOwner){
            if (it){
                findNavController().navigate(R.id.action_loginUserFragment_to_homeFragment)
            }
        }


        binding.apply {
            btnLogin.setOnClickListener {
                val email = binding.edtEmail.text.toString()
                val password = binding.edtPassword.text.toString()

                if (binding.edtEmail.text.isNullOrBlank() || binding.edtPassword.text.isNullOrBlank()) {
                    Snackbar.make(binding.root, "Lengkapi Field diatas", Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    viewModel.loginUserName(email, password)
                }

                viewModel.login()

            }

            txtRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginUserFragment_to_registerUserFragment)
            }
        }

        viewModel.status.observe(viewLifecycleOwner) {
            if (it) {
                navigateToHome()
            }
        }


    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_loginUserFragment_to_homeFragment)
        viewModel.setDefault()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}