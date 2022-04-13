package com.dzakyhdr.networkingsample02.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.dzakyhdr.networkingsample02.R
import com.dzakyhdr.networkingsample02.data.model.RegisterResponseItem
import com.dzakyhdr.networkingsample02.data.network.ApiClient
import com.dzakyhdr.networkingsample02.data.register.RegisterRequest
import com.dzakyhdr.networkingsample02.databinding.FragmentRegisterUserBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterUserFragment : DialogFragment() {

    private var _binding: FragmentRegisterUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterUserBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnSave.setOnClickListener {

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
                    }

                    override fun onFailure(call: Call<RegisterResponseItem>, t: Throwable) {
                        Toast.makeText(requireContext(), "gagal registrasi", Toast.LENGTH_SHORT)
                            .show()
                    }
                })

                dialog?.dismiss()

            }

            binding.btnBatal.setOnClickListener {
                dialog?.dismiss()
            }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}