package com.sample.demo.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.sample.demo.R
import com.sample.demo.adapter.TabsAdapter
import com.sample.demo.databinding.ActivityMainBinding
import com.sample.demo.fragment.MetadataFragment
import com.sample.demo.fragment.user.UsersFragment
import com.sample.demo.model.User


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var usersFragment: UsersFragment
    private lateinit var metadataFragment: MetadataFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        setSupportActionBar(binding.toolbar)

        title = getString(R.string.dashboard)
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.user_tab)))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.metadata_tab)))
        binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val spinnerAdapter: SpinnerAdapter = ArrayAdapter.createFromResource(
            applicationContext,
            R.array.category,
            R.layout.spinner_dropdown_item
        )
        val navigationSpinner = Spinner(supportActionBar!!.themedContext)
        navigationSpinner.adapter = spinnerAdapter
        binding.toolbar.addView(navigationSpinner, 0)

        navigationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                usersFragment.filterData(position)
            }

        }

        usersFragment = UsersFragment.getInstance()

        metadataFragment = MetadataFragment.getInstance()

        val adapter = TabsAdapter(usersFragment, metadataFragment, supportFragmentManager, binding.tabLayout.tabCount)
        binding.viewPager.adapter = adapter
        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        binding.fab.setOnClickListener {
            val intent = Intent(this, CreateUserActivity::class.java)
            startActivity(intent)
        }
    }

    fun setUserInMetaData(user: User) {
        metadataFragment.setUserInMetaData(user = user)
        binding.viewPager.currentItem = 1
    }
}