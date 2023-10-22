package com.example.semenov_lab_1.ui.home

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.example.semenov_lab_1.databinding.FragmentHomeBinding
import com.example.semenov_lab_1.ui.RcAdapter
import com.example.semenov_lab_1.ui.Recipe
import com.example.semenov_lab_1.ui.dashboard.DashboardFragment
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread


class HomeFragment : Fragment(), RcAdapter.Listener {

    private var _binding: FragmentHomeBinding? = null
    val adapter=RcAdapter(this)
    lateinit var apiJson: URL
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        thread {
            apiJson =
                URL("https://raw.githubusercontent.com/Lpirskaya/JsonLab/master/recipes2022.json")
            ParsJson(apiJson)
            requireActivity().runOnUiThread {
                binding.rcView.layoutManager = LinearLayoutManager(requireContext())
                binding.rcView.adapter = adapter
            }
        }
        return root
    }

    private fun ParsJson(url: URL){
        val connection = url.openConnection() as HttpURLConnection
        if(connection.responseCode == HttpURLConnection.HTTP_OK) {
            val Input = BufferedReader(InputStreamReader(connection.inputStream))
            val JSONArray = Klaxon().parseJsonArray(Input)
            JSONArray.forEach {
                val JSONobject = it as JsonObject
                val field = JSONobject.values.toTypedArray()
                val recipe = Recipe(
                    field[0].toString(),
                    field[1] as Int,
                    field[2].toString(),
                    field[3].toString(),
                    field[4] as Int
                )
                adapter.RecipeCreate(recipe)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(recipe: Recipe) {
        val sharedPreferences = requireContext().getSharedPreferences("Name", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("Name", recipe.Name)
        editor.apply()
        val navController = findNavController(requireActivity(),com.example.semenov_lab_1.R.id.nav_host_fragment_activity_navigation)
        navController.navigate(com.example.semenov_lab_1.R.id.navigation_dashboard)

    }
}