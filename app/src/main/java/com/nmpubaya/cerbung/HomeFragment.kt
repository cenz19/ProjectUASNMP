package com.nmpubaya.cerbung

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.StringRequest
import com.nmpubaya.cerbung.databinding.FragmentHomeBinding
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import com.android.volley.Request
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val ARG_ID = "users_id"

class HomeFragment : Fragment() {
    var cerbungs: ArrayList<Cerbung> = arrayListOf()
    private lateinit var binding:FragmentHomeBinding
    private var users_id: Int? = null

    fun updateList() {
        //Fungsi ini untuk setup adapter
        val lm = LinearLayoutManager(activity)
        with(binding.cerbungRecView) {
            layoutManager = lm
            setHasFixedSize(true)
            adapter = CerbungAdapter(cerbungs, users_id!!)
        }
    }
    fun reload(){
        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.me/native/160421005/get_all_cerbung.php"
        var stringRequest = object: StringRequest(Request.Method.POST, url,
            {
                Log.d("apiresult", it.toString())
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<Cerbung>>() { }.type
                    cerbungs = Gson().fromJson(data.toString(), sType) as ArrayList<Cerbung>
                    updateList()
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String>? {
                return super.getParams()
            }
        }
        q.add(stringRequest)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reload()
        arguments?.let {
            users_id = it.getInt(ARG_ID)
        }
    }
    override fun onResume() {
        super.onResume()
        reload()
        arguments?.let {
            users_id = it.getInt(ARG_ID)
        }
        (activity as AppCompatActivity).supportActionBar?.title = "Cerbung"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Cerbung"
    }

    companion object {
        @JvmStatic
        fun newInstance(users_id: Int) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID, users_id)
                }
            }
    }
}
