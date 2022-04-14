package com.dzakyhdr.networkingsample02.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dzakyhdr.networkingsample02.R
import com.dzakyhdr.networkingsample02.data.model.RegisterResponseItem
import com.dzakyhdr.networkingsample02.data.network.ApiClient
import com.dzakyhdr.networkingsample02.data.auth.RegisterRequest
import com.dzakyhdr.networkingsample02.databinding.FragmentRegisterUserBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterUserFragment : Fragment() {

    private var _binding: FragmentRegisterUserBinding? = null
    private val binding get() = _binding!!

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

                val register = RegisterRequest(
                    edtEmail.text.toString(),
                    edtPassword.text.toString(),
                    "admin"
                )

                ApiClient.instance.registerUser(register).enqueue(object : Callback<RegisterResponseItem>{
                    override fun onResponse(
                        call: Call<RegisterResponseItem>,
                        response: Response<RegisterResponseItem>
                    ) {
                        Log.d("RegisterUserFragment", "${response.body()}")
                        findNavController().navigate(R.id.action_registerUserFragment_to_loginUserFragment)
                        Snackbar.make(binding.root, "User Behasil Dibuat", Snackbar.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<RegisterResponseItem>, t: Throwable) {
                        Toast.makeText(requireContext(), "gagal registrasi", Toast.LENGTH_SHORT)
                            .show()
                    }
                })




            }



        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}