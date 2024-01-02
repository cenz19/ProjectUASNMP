package com.nmpubaya.cerbung

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat.finishAffinity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.nmpubaya.cerbung.databinding.ActivityMainBinding
import com.nmpubaya.cerbung.databinding.FragmentPrefsBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject

private const val ARG_ID = "id"

class PrefsFragment : Fragment() {
    private lateinit var binding: FragmentPrefsBinding
    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ARG_ID)
        }
    }

    override fun onResume() {
        super.onResume()
        arguments?.let {
            id = it.getInt(ARG_ID)
        }
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

    fun clearData() {
        binding.txtOldPasswordPref.text?.clear()
        binding.txtNewPasswordPref.text?.clear()
        binding.txtRetypePasswordPref.text?.clear()
        binding.imgProfile.focusable
    }

    fun updatePassword(old_pass: String, new_pass: String, retype_pass: String, users_id: Int) {
        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.me/native/160421005/update_password.php"
        val dialog = AlertDialog.Builder(requireActivity())

        val stringRequest = object : StringRequest(Request.Method.POST, url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    dialog.setMessage("Successfully changed the password")
                    dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        clearData()
                        dialog.dismiss()
                    })
                    dialog.create().show()
                } else {
                    dialog.setMessage("Cannot change the password\nPlease check again")
                    dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        clearData()
                        dialog.dismiss()
                    })
                    dialog.create().show()
                }
            },
            {
                Log.e("apiresult", it.toString())
                dialog.setMessage("Cannot change the password\nPlease check again")
                dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                    clearData()
                    dialog.dismiss()
                })
                dialog.create().show()
            }
        ) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["old_pass"] = old_pass
                params["new_pass"] = new_pass
                params["id"] = users_id.toString()
                return params
            }
        }
        q.add(stringRequest)
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

        binding.btnChangePassword.setOnClickListener {
            val old_pass = binding.txtOldPasswordPref.text.toString()
            val pass = binding.txtNewPasswordPref.text.toString()
            val retype_pass = binding.txtRetypePasswordPref.text.toString()
            val id_user = id

            if (pass != retype_pass) {
                val dialog = AlertDialog.Builder(requireActivity())
                dialog.setMessage("Password is not the same as retype password\nPlease enter it again")
                dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                dialog.create().show()
            } else {
                if (old_pass != null && pass != null && retype_pass != null && id_user != null) {
                    updatePassword(old_pass, pass, retype_pass, id_user)
                }
            }
        }

        binding.fabLogout.setOnClickListener{
            val intent = Intent(activity, MainActivity::class.java)
            val editor = sharedPreferences?.edit()
            editor?.remove(MainActivity.KEY_USER_ID)
            editor?.remove(MainActivity.KEY_URL_PROFILE)
            editor?.remove(MainActivity.KEY_USERNAME)
            editor?.apply()
            startActivity(intent)
        }
    }



    companion object {
        @JvmStatic
        fun newInstance(id: Int) =
            PrefsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID, id)
                }
            }
    }
}