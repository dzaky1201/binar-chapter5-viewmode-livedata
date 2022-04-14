package com.dzakyhdr.networkingsample02.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.dzakyhdr.networkingsample02.R
import com.dzakyhdr.networkingsample02.data.auth.LoginRequest
import com.dzakyhdr.networkingsample02.data.model.LoginResponseItem
import com.dzakyhdr.networkingsample02.data.network.ApiClient
import com.dzakyhdr.networkingsample02.databinding.FragmentLoginUserBinding
import com.dzakyhdr.networkingsample02.utils.SharedPreference
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginUserFragment : Fragment() {

    private var _binding: FragmentLoginUserBinding? = null
    private val binding get() = _binding!!
    private var sharedPref: SharedPreference? = null
    private var status = false

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
        status = sharedPref?.getPrefKeyStatus("login_status") == true
        if (status){
            findNavController().navigate(R.id.action_loginUserFragment_to_homeFragment)
        }
        binding.apply {
            btnLogin.setOnClickListener {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()

                if (binding.edtEmail.text.isNullOrBlank() ||  binding.edtPassword.text.isNullOrBlank()){
                    Snackbar.make(binding.root, "Lengkapi Field diatas", Snackbar.LENGTH_LONG).show()
                } else {
                    val login = LoginRequest(email, password)
                    ApiClient.instance.loginUser(login).enqueue(object : Callback<LoginResponseItem>{
                        override fun onResponse(
                            call: Call<LoginResponseItem>,
                            response: Response<LoginResponseItem>
                        ) {
                            val responseBody = response.body()

                            if (response.code() == 201){
                                sharedPref?.saveKey(responseBody?.email!!, responseBody.password!!)
                                sharedPref?.saveKeyState(true)
                                findNavController().navigate(R.id.action_loginUserFragment_to_homeFragment)
                            } else {
                                Toast.makeText(requireContext(), "Gagal Login", Toast.LENGTH_SHORT)
                                    .show()
                            }

                        }

                        override fun onFailure(call: Call<LoginResponseItem>, t: Throwable) {
                            Toast.makeText(view.context, "User Tidak ditemukan", Toast.LENGTH_SHORT)
                                .show()
                        }
                    })

                }


            }

            txtRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginUserFragment_to_registerUserFragment)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}