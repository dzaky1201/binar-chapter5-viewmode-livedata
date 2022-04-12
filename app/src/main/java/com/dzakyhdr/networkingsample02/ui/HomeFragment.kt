package com.dzakyhdr.networkingsample02.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dzakyhdr.networkingsample02.R
import com.dzakyhdr.networkingsample02.data.model.CarResponseItem
import com.dzakyhdr.networkingsample02.data.network.ApiClient
import com.dzakyhdr.networkingsample02.data.network.ApiService
import com.dzakyhdr.networkingsample02.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ApiClient.instance.getAllCar().enqueue(object : Callback<List<CarResponseItem>> {
            override fun onResponse(
                call: Call<List<CarResponseItem>>,
                response: Response<List<CarResponseItem>>
            ) {
                val body = response.body()
                val code = response.code()

                if (code == 200){
                    binding.loading.visibility = View.GONE
                    showData(body)
                }
            }

            override fun onFailure(call: Call<List<CarResponseItem>>, t: Throwable) {
                binding.loading.visibility = View.GONE
                Snackbar.make(binding.root, "Terjadi Masalah", Snackbar.LENGTH_LONG).show()
            }
        })
    }

    private fun showData(body: List<CarResponseItem>?) {
        val adapter = CarAdapter()
        adapter.submitList(body)
        binding.rvCar.adapter = adapter
        binding.rvCar.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}