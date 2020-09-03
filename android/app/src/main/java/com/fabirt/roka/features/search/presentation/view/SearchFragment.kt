package com.fabirt.roka.features.search.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.fabirt.roka.R
import kotlinx.android.synthetic.main.search_bar.*

class SearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextSearch.addTextChangedListener { text ->
            if (text.isNullOrEmpty()) {
                btnCancelSearch.visibility = View.INVISIBLE
            } else {
                btnCancelSearch.visibility = View.VISIBLE
            }
        }

        btnCancelSearch.setOnClickListener {
            editTextSearch.text.clear()
        }
    }

}