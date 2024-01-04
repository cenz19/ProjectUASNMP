package com.nmpubaya.cerbung

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.nmpubaya.cerbung.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding
    private var fragment:ArrayList<Fragment> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val sharedFile = "com.nmpubaya.cerbung"
        val sharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt(MainActivity.KEY_USER_ID, 0)

        fragment.add(HomeFragment.newInstance(id))
        fragment.add(FollowingFragment.newInstance(id))
        fragment.add(CreateFragment.newInstance(id))
        fragment.add(UsersFragment())
        fragment.add(PrefsFragment.newInstance(id))

        binding.viewPager.adapter = MyAdapter(this, fragment)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.bottomNavBar.selectedItemId = binding.bottomNavBar.menu.getItem(position).itemId
            }
        })

        binding.bottomNavBar.setOnItemSelectedListener{
            binding.viewPager.currentItem = when(it.itemId){
                R.id.itemHome -> 0
                R.id.itemFollowing -> 1
                R.id.itemCreate -> 2
                R.id.itemUsers -> 3
                R.id.itemPrefs -> 4
                else -> 0
            }

            true
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.btnNotification -> {
                val intent = Intent(this, NotificationActivity::class.java)
                startActivity(intent)
            }
            android.R.id.home -> onBackPressed()
        }
        return true
    }

}