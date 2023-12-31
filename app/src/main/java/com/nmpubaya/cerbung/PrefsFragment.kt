package com.nmpubaya.cerbung

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.nmpubaya.cerbung.databinding.FragmentPrefsBinding
import com.squareup.picasso.Picasso

class PrefsFragment : Fragment() {
    private lateinit var binding: FragmentPrefsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_prefs, container, false)
        binding = FragmentPrefsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedFile = "com.nmpubaya.cerbung"
        val sharedPreferences = activity?.getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        val nightMode = sharedPreferences?.getBoolean("night", false)
        val username = sharedPreferences?.getString(MainActivity.KEY_USERNAME, "")
        val url_profile = sharedPreferences?.getString(MainActivity.KEY_URL_PROFILE, "")

        if (nightMode == true) {
            binding.switchDarkMode.isChecked = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        var builder = Picasso.Builder(view.context)
        builder.listener { picasso, uri, exception ->  exception.printStackTrace()}
        Picasso.get().load(url_profile).into(binding.imgProfile)

        binding.txtUsernamePref.setText(username)

        binding.switchDarkMode.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor?.putBoolean("night", false)
                editor?.apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor?.putBoolean("night", true)
                editor?.apply()
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PrefsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}