package com.narunas.comics

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.narunas.comics.gson.Comic
import com.narunas.comics.ui.PagerUIFragment
import com.narunas.comics.viemodel.CommonViewModel.Companion.ComicsSetInReview

class Details: AppCompatActivity() {

    companion object {
        val ACT_IMG: String = "activeImage"
    }
    private var initialImageIndex: Int = -1
    private lateinit var pager: ViewPager
    private var dataObserver = Observer<ArrayList<Comic>> {
        it?.let {
            updateUI(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail_pager)

        initialImageIndex = intent.getIntExtra(ACT_IMG, 0)

        pager = findViewById(R.id.view_pager)

    }

    override fun onResume() {
        super.onResume()
        ComicsSetInReview.observe(this, dataObserver)
    }


    override fun onPause() {
        super.onPause()
        ComicsSetInReview.removeObserver(dataObserver)
    }


    private fun updateUI(data: ArrayList<Comic>) {

        val imageAdapter = FragPagerAdapter(supportFragmentManager, data)
        pager.adapter = imageAdapter
        pager.currentItem = initialImageIndex


    }

    private inner class FragPagerAdapter(fm: FragmentManager, data: ArrayList<Comic>) : FragmentStatePagerAdapter(fm) {

        private val dataSet = data

        override fun getItem(position: Int): Fragment {
            return PagerUIFragment.getInstance(dataSet[position])
        }

        override fun getCount(): Int {
            return dataSet.size
        }
    }
}