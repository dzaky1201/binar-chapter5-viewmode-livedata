package com.dzakyhdr.viewmodel.ui.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dzakyhdr.viewmodel.R
import com.dzakyhdr.viewmodel.data.model.RegisterResponseItem
import com.dzakyhdr.viewmodel.data.network.ApiClient
import com.dzakyhdr.viewmodel.data.auth.RegisterRequest
import com.dzakyhdr.viewmodel.databinding.FragmentRegisterUserBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterUserFragment : Fragment() {

    private var _binding: FragmentRegisterUserBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by lazy { ViewModelProvider(requireActivity())[RegisterViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterUserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnRegister.setOnClickListener {

                val email = binding.edtEmail.text.toString()
                val password = binding.edtPassword.text.toString()

                if (binding.edtEmail.text.isNullOrBlank() || binding.edtPassword.text.isNullOrBlank()) {
                    Snackbar.make(binding.root, "Lengkapi Field diatas", Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    viewModel.registerRequest(email, password)
                }

                viewModel.registerResponse()

                viewModel.showSnackbar.observe(viewLifecycleOwner) {
                    if (it) {
                        Snackbar.make(binding.root, "User Berhasil Dibuat", Snackbar.LENGTH_LONG)
                            .show()
                        findNavController().navigate(RegisterUserFragmentDirections.actionRegisterUserFragmentToLoginUserFragment())
                    } else {
                        Snackbar.make(binding.root, "User Sudah Ada", Snackbar.LENGTH_LONG).show()
                    }
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}