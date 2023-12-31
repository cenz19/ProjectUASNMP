package com.nmpubaya.cerbung

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nmpubaya.cerbung.databinding.FragmentFollowingBinding
import org.json.JSONObject


private const val ARG_FOLLOW = "follow"

class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowingBinding
    var cerbungs: ArrayList<Cerbung> = arrayListOf()
    private var id: Int? = null

    fun updateList() {
        val lm = LinearLayoutManager(activity)
        with (binding.followRecView) {
            layoutManager = lm
            setHasFixedSize(true)
            adapter = FollowCerbungAdapter(cerbungs)
        }
    }

    fun reload(id: Int) {
        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.me/native/160421005/get_follow_cerbung.php"
        val stringRequest = object : StringRequest(Request.Method.POST, url,
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
                val params = HashMap<String, String>()
                params["id"] = id.toString()
                return params
            }
        }
        q.add(stringRequest)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ARG_FOLLOW)
            reload(id!!)
        }

    }

    override fun onResume() {
        super.onResume()
        arguments?.let {
            id = it.getInt(ARG_FOLLOW)
            reload(id!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_following, container, false)
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(id: Int) =
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_FOLLOW, id)
                }
            }
    }
}