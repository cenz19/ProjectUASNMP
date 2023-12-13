package com.nmpubaya.cerbung

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.StringRequest
import com.nmpubaya.cerbung.databinding.FragmentHomeBinding
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import com.android.volley.Request
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

class HomeFragment : Fragment() {

    var playlists:ArrayList<Cerbung> = arrayListOf()
    private lateinit var binding:FragmentHomeBinding

    fun updateList() {
        //Fungsi ini untuk setup adapter
        val lm = LinearLayoutManager(activity)
        with(binding.playListRecView) {
            layoutManager = lm
            setHasFixedSize(true)
            adapter = PlaylistAdapter(playlists)
        }
    }
    fun reload(){
        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.me/native/160421072/get_playlist.php"
        //val url = "http://10.0.2.2/music_zd/get_playlist.php"
        var stringRequest = StringRequest(
            Request.Method.POST, url,
            {
                Log.d("apiresult", it.toString())
                val obj = JSONObject(it)
                if(obj.getString("result") == "OK"){
                    val data = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<Cerbung>>() { }.type
                    playlists = Gson().fromJson(data.toString(), sType) as ArrayList<Cerbung>
                    Log.d("apiresult", playlists.toString())
                    updateList()
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            })
        q.add(stringRequest)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reload()
    }
    override fun onResume() {
        super.onResume()
        reload()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
