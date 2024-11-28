package com.taoc.intersim_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.taoc.intersim_app.databinding.ActivityMainBinding
import com.taoc.intersim_app.ui.home.HomeFragment
import com.taoc.intersim_test.ui.account.AccountFragment
<<<<<<< HEAD
import com.taoc.intersim_test.ui.history.HistoryFragment
import com.taoc.intersim_test.ui.search.SearchFragment
=======
>>>>>>> 01c65e76144a30efd66b83218116a32fceda2410

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            replacefragment(HomeFragment())
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    replacefragment(HomeFragment())
                    true
                }
                R.id.navigation_account -> {
                    replacefragment(AccountFragment())
                    true
                }
<<<<<<< HEAD
                R.id.navigation_search -> {
                    replacefragment(SearchFragment())
                    true
                }
                R.id.navigation_history -> {
                    replacefragment(HistoryFragment())
                    true
                }
=======
>>>>>>> 01c65e76144a30efd66b83218116a32fceda2410
                else -> false
            }
        }
    }
    private fun replacefragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, fragment)
            .commit()
    }
}