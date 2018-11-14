package com.narunas.comics.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.narunas.comics.R
import com.narunas.comics.ui.adapter.SingleFragmentAdapter
import com.narunas.comics.viemodel.CommonViewModel.Companion.TopComicsSections
import com.narunas.comics.viemodel.TopSection
import kotlinx.android.synthetic.main.grid_fragment.*

class TopSectionsFragment :Fragment(){

    companion object {

        val TAG: String = TopSectionsFragment::class.java.simpleName

        fun getInstance() :TopSectionsFragment {
            return TopSectionsFragment()
        }

    }

    private lateinit var lm: LinearLayoutManager
    private lateinit var mAdapter: SingleFragmentAdapter
    private lateinit var mRecycler: RecyclerView

    private var observer =  Observer<HashMap<Int, TopSection>> {
        it?.let {
            updateUI(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lm = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        TopComicsSections.observe(this, observer)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.grid_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRecycler = recycler
        mAdapter = SingleFragmentAdapter()


        mRecycler.layoutManager = lm
        mRecycler.adapter = mAdapter


    }

    private fun updateUI(data: HashMap<Int, TopSection>) {
        mAdapter.updateData(data)
    }

    override fun onStop() {
        super.onStop()
        TopComicsSections.removeObserver(observer)
    }
}