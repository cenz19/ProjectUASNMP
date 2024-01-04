package com.nmpubaya.cerbung

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nmpubaya.cerbung.databinding.FragmentMostLikedBinding
import org.json.JSONObject


class MostLikedFragment : Fragment() {
    private lateinit var binding: FragmentMostLikedBinding
    var users: ArrayList<User> = arrayListOf()
    fun updateList() {
        //Fungsi ini untuk setup adapter
        val lm = LinearLayoutManager(activity)
        with(binding.mostLikedRecView) {
            layoutManager = lm
            setHasFixedSize(true)
            adapter = UserAdapter(users)
        }
    }
    fun reload(){
        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.me/native/160421005/get_all_user.php"
        val stringRequest = object: StringRequest(
            Request.Method.POST, url,
            {
                Log.d("apiresult", it.toString())
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<User>>() { }.type
                    users = Gson().fromJson(data.toString(), sType) as ArrayList<User>
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

        }
    }

    override fun onResume() {
        super.onResume()
        reload()
        (activity as AppCompatActivity).supportActionBar?.title = "Users"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_most_liked, container, false)
        binding = FragmentMostLikedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Users"
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MostLikedFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}