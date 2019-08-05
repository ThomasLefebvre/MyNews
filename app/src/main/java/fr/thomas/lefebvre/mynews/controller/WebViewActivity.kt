package fr.thomas.lefebvre.mynews.controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import fr.thomas.lefebvre.mynews.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val urlWebView:String= intent.getStringExtra(Intent.EXTRA_TEXT)
        web_view.loadUrl(urlWebView)
    }
}
