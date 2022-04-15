package com.dzakyhdr.networkingsample02.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dzakyhdr.networkingsample02.data.model.CarResponseItem
import com.dzakyhdr.networkingsample02.data.network.ApiClient
import com.dzakyhdr.networkingsample02.databinding.FragmentHomeBinding
import com.dzakyhdr.networkingsample02.ui.CarAdapter
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.cars.observe(viewLifecycleOwner) {
            showData(it)
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.loading.visibility = View.VISIBLE
            } else {
                binding.loading.visibility = View.GONE
            }
        }
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