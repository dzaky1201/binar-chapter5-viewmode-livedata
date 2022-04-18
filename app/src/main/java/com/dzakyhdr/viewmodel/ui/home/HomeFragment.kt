package com.dzakyhdr.viewmodel.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dzakyhdr.viewmodel.R
import com.dzakyhdr.viewmodel.data.model.CarResponseItem
import com.dzakyhdr.viewmodel.databinding.FragmentHomeBinding
import com.dzakyhdr.viewmodel.ui.CarAdapter
import com.dzakyhdr.viewmodel.utils.SharedPreference


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

        homeViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.loading.visibility = View.VISIBLE
            } else {
                binding.loading.visibility = View.GONE
            }
        }

        homeViewModel.cars.observe(viewLifecycleOwner) {
            showData(it)
        }

        binding.homeToolbar.inflateMenu(R.menu.home_menu)

        homeViewModel.email.observe(viewLifecycleOwner){
            binding.txtUsername.text = it
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

        homeViewModel.getCar()


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