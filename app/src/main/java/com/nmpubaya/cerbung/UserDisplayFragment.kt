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
import com.nmpubaya.cerbung.databinding.FragmentUserDisplayBinding
import com.nmpubaya.cerbung.databinding.FragmentReadPublicBinding
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_CERBUNG = "cerbung"
private const val ARG_ID = "id"

class UserDisplayFragment : Fragment() {
    private var cerbung: Cerbung? = null
    var cerbungs: ArrayList<Cerbung> = arrayListOf()
    private lateinit var binding: FragmentUserDisplayBinding
    private var users_id: Int? = null

    fun updateList() {
        //Fungsi ini untuk setup adapter
        val lm = LinearLayoutManager(activity)
        with(binding.userCerbungRecView) {
            layoutManager = lm
            setHasFixedSize(true)
            adapter = CerbungAdapter(cerbungs, users_id!!)
        }
    }
    fun reload(){
        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.me/native/160421005/get_user_cerbung.php"
        var stringRequest = object: StringRequest(
            Request.Method.POST, url,
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
                params["cerbung_id"] = cerbung?.id.toString()
                return params
            }
        }
        q.add(stringRequest)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reload()
        arguments?.let {
            cerbung = it.getParcelable(ARG_CERBUNG)
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
        binding = FragmentUserDisplayBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserDisplayFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserDisplayFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CERBUNG, param1)
                    putString(ARG_ID, param2)
                }
            }
    }
}