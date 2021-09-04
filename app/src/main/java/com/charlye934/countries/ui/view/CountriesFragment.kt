package com.charlye934.countries.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.charlye934.countries.databinding.FragmentCountriesBinding
import com.charlye934.countries.ui.viewmodel.CountryViewModel
import com.charlye934.countries.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountriesFragment : Fragment() {

    private val viewModel: CountryViewModel by viewModels()
    private lateinit var binding: FragmentCountriesBinding
    private lateinit var adapterCountry: CountryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCountriesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        swipeRefresh()
        observableViewModel()

    }

    private fun setUpRecyclerView() {
        adapterCountry = CountryAdapter()
        binding.rvCountryList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterCountry
        }
    }

    private fun swipeRefresh(){
        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = false
            viewModel.refresh()
        }
    }

    private fun observableViewModel() {
        viewModel.refresh()

        viewModel.countries.observe(viewLifecycleOwner){ response ->
            when(response){
                is NetworkResult.Sucess ->{
                    response.data?.let {
                        binding.rvCountryList.visibility = View.VISIBLE
                        adapterCountry.updateCountries(it)
                    }

                    binding.loadingView.visibility = View.GONE
                    binding.tvListError.visibility = View.GONE
                }
                is NetworkResult.Error -> {
                    binding.rvCountryList.visibility = View.GONE
                    binding.loadingView.visibility = View.GONE
                    binding.tvListError.visibility = View.VISIBLE
                    binding.tvListError.text = response.message
                }

                is NetworkResult.Loading -> {
                    binding.loadingView.visibility = View.VISIBLE
                    binding.tvListError.visibility = View.GONE
                    binding.rvCountryList.visibility = View.GONE
                }
            }
        }
    }
}