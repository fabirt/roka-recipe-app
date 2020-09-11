package com.fabirt.roka.features.detail.presentation.view

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fabirt.roka.R
import kotlinx.android.synthetic.main.fragment_web_detail.*

class WebDetailFragment : Fragment() {
    private val args: WebDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_web_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        setupWebView()
    }

    private fun setupWebView() {
        tvUrl.text = args.url
        tvTitle.text = args.title
        webView.webViewClient = RecipeWebViewClient(appBar)
        webView.loadUrl(args.url)
        webView.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK
                && event.action == KeyEvent.ACTION_UP
                && webView.canGoBack()
            ) {
                webView.goBack()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

}

class RecipeWebViewClient(private val view: View) : WebViewClient() {
    private val textViewTitle: TextView = view.findViewById(R.id.tvTitle)
    private val textViewUrl: TextView = view.findViewById(R.id.tvUrl)

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        view?.title?.let {
            textViewTitle.text = it
        }
        url?.let {
            textViewUrl.text = it
        }
    }

}