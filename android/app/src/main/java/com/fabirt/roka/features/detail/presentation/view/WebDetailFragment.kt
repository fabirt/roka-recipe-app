package com.fabirt.roka.features.detail.presentation.view

import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fabirt.roka.core.utils.configureStatusBar
import com.fabirt.roka.databinding.FragmentWebDetailBinding

class WebDetailFragment : Fragment(), WebViewDelegate {
    private val args: WebDetailFragmentArgs by navArgs()

    private var _binding: FragmentWebDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var client: RecipeWebViewClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        configureStatusBar()
        _binding = FragmentWebDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        setupWebView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        client.delegate = null
        _binding = null
    }

    override fun pageStarted(url: String?) {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun pageFinished(title: String, url: String) {
        binding.tvUrl.text = url
        binding.tvTitle.text = title
        binding.progressBar.visibility = View.GONE
    }

    private fun setupWebView() {
        binding.tvUrl.text = args.url
        binding.tvTitle.text = args.title
        client = RecipeWebViewClient().apply {
            delegate = this@WebDetailFragment
        }
        binding.webView.webViewClient = client
        binding.webView.loadUrl(args.url)
        binding.webView.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK
                && event.action == KeyEvent.ACTION_UP
                && binding.webView.canGoBack()
            ) {
                binding.webView.goBack()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }
}

class RecipeWebViewClient : WebViewClient() {
    var delegate: WebViewDelegate? = null

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        delegate?.pageStarted(url)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        if (view?.title != null && url != null) {
            delegate?.pageFinished(view.title, url)
        }
    }
}

interface WebViewDelegate {
    fun pageStarted(url: String?)

    fun pageFinished(title: String, url: String)
}