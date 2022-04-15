package com.dzakyhdr.networkingsample02.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dzakyhdr.networkingsample02.R
import com.dzakyhdr.networkingsample02.data.model.CarResponseItem
import com.dzakyhdr.networkingsample02.databinding.FragmentHomeBinding
import com.dzakyhdr.networkingsample02.ui.CarAdapter
import com.dzakyhdr.networkingsample02.utils.SharedPreference


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var sharedPref: SharedPreference? = null
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeViewModelFactory: HomeViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPreference(view.context)
        homeViewModelFactory = HomeViewModelFactory(sharedPref!!)
        homeViewModel = ViewModelProvider(
            requireActivity(),
            homeViewModelFactory
        )[HomeViewModel::class.java]

        homeViewModel.getCar()

        homeViewModel.cars.observe(viewLifecycleOwner) {
            showData(it)
        }

        binding.homeToolbar.inflateMenu(R.menu.home_menu)

        homeViewModel.email.observe(viewLifecycleOwner){
            binding.txtUsername.text = it
        }


        homeViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.loading.visibility = View.VISIBLE
            } else {
                binding.loading.visibility = View.GONE
            }
        }

        binding.homeToolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.logout -> {
                    homeViewModel.logOut()
                    findNavController().navigate(R.id.action_homeFragment_to_loginUserFragment)
                    true
                }
                else -> false
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