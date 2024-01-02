package com.nmpubaya.cerbung

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nmpubaya.cerbung.databinding.ActivityCreateCerbung3Binding
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class CreateCerbung3Activity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateCerbung3Binding

    fun insertAndPublishCerbung(title: String, desc:String, img_cover: String, genre_id: Int,
                                access: Int, first_par: String, users_id: Int, waktu_dibuat: String) {
        val q = Volley.newRequestQueue(this)
        val url = "https://ubaya.me/native/160421005/create_cerbung.php"
        val dialog = AlertDialog.Builder(this)
        val stringRequest = object : StringRequest(Request.Method.POST, url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val msg = obj.getString("msg")
                    val sType = object : TypeToken<Int>() { }.type
                    val id_cerbung = Gson().fromJson(msg.toString(), sType) as Int
                    val urlPar = "https://ubaya.me/native/160421005/create_paragraph.php"
                    val strReq = object : StringRequest(Request.Method.POST, urlPar,
                        {
                            Log.d("apiresult", it)
                            val objt = JSONObject(it)
                            if (objt.getString("result") == "OK") {
                                dialog.setMessage("Successfully created cerbung\nReturn to Home to see it.")
                                dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                                    val i = Intent(this, HomeActivity::class.java)
                                    startActivity(i)
                                    finish()
                                })
                                dialog.create().show()
                            }
                        },
                        {
                            Log.e("apiresult", it.message.toString())
                            dialog.setMessage("Failed to create cerbung")
                            dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                                dialog.dismiss()
                            })
                            dialog.create().show()
                        }
                    ) {
                        override fun getParams(): MutableMap<String, String>? {
                            val params = HashMap<String, String>()
                            params["isi"] = first_par
                            params["waktu_buat"] = waktu_dibuat
                            params["cerbung_id"] = id_cerbung.toString()
                            params["users_id"] = users_id.toString()
                            return params
                        }
                    }
                    q.add(strReq)
                } else {
                    dialog.setMessage("Failed to create cerbung")
                    dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
                    dialog.create().show()
                }
            },
            {
                Log.e("apiresult", it.printStackTrace().toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["title"] = title
                params["description"] = desc
                params["num_likes"] = "0"
                params["access"] = access.toString()
                params["genre_id"] = genre_id.toString()
                params["num_paragraph"] = "0"
                params["url_gambar"] = img_cover
                params["waktu_dibuat"] = waktu_dibuat
                params["users_id"] = users_id.toString()
                return params
            }
        }
        q.add(stringRequest)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCerbung3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(CreateCerbung1Activity.KEY_ID, 0)
        val title = intent.getStringExtra(CreateCerbung1Activity.KEY_TITLE)
        val desc = intent.getStringExtra(CreateCerbung1Activity.KEY_DESC)
        val img_cover = intent.getStringExtra(CreateCerbung1Activity.KEY_IMG_COVER)
        val genre = intent.getParcelableExtra(CreateCerbung1Activity.KEY_GENRE) as Genre?
        val access = intent.getIntExtra(CreateCerbung2Activity.KEY_ACCESS, 0)
        val first_paragraph = intent.getStringExtra(CreateCerbung2Activity.KEY_FIRST_PARAGRAPH)
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val waktu_dibuat = formatter.format(date)


        binding.txtTitleCreateCerbung3.text = title
        binding.txtShortDescriptionCreateCerbung3.text = desc
        if (access == 1) {
            binding.txtAccessCreateCerbung3.text = "Restricted"
        } else if (access == 2) {
            binding.txtAccessCreateCerbung3.text = "Public"
        }
        binding.txtFirstParagraphCreateCerbung3.text = first_paragraph
        binding.txtGenreCreateCerbung3.text = genre?.nama

        binding.btnPrev3.setOnClickListener {
            val i = Intent(this, CreateCerbung2Activity::class.java)
            i.putExtra(CreateCerbung1Activity.KEY_ID, id)
            i.putExtra(CreateCerbung1Activity.KEY_TITLE, title)
            i.putExtra(CreateCerbung1Activity.KEY_DESC, desc)
            i.putExtra(CreateCerbung1Activity.KEY_IMG_COVER, img_cover)
            i.putExtra(CreateCerbung1Activity.KEY_GENRE, genre)
            i.putExtra(CreateCerbung2Activity.KEY_ACCESS, access)
            i.putExtra(CreateCerbung2Activity.KEY_FIRST_PARAGRAPH, first_paragraph)
            startActivity(i)
        }

        binding.btnPublish3.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            if (binding.checkBox.isChecked) {
                if (title != null && desc != null && img_cover != null && first_paragraph != null) {
                    insertAndPublishCerbung(title, desc, img_cover, genre!!.id, access, first_paragraph, id, waktu_dibuat)
                } else {
                    dialog.setMessage("There are still empty field. Please enter it")
                    dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
                    dialog.create().show()
                }
            } else {
                dialog.setMessage("Please tick on the checkbox to accept the terms.")
                dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                dialog.create().show()
            }
        }
    }
}