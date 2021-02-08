package ru.geekbrains.nasaapiproject.ui

import android.content.Intent
import android.os.Bundle
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.fragment_pod.*
import ru.geekbrains.nasaapiproject.R
import ru.geekbrains.nasaapiproject.ui.pod.PictureOfTheDayData
import ru.geekbrains.nasaapiproject.ui.pod.PictureOfTheDayViewModel

class SplashActivity : AppCompatActivity() {

    val SNACKBAR_MAX_LINES = 6

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        image_view.animate().rotationBy(750f)
                .setInterpolator(LinearInterpolator()).duration = 10000

        viewModel.getData().observe(this, Observer<PictureOfTheDayData> { it ->
            when (it) {
                is PictureOfTheDayData.Success -> {
                    it.serverResponseData?.let { it1 ->
                        val intent = Intent(this@SplashActivity, MainActivity::class.java)
                                .apply { putExtra("PodServerResponseData", it1) }
                        startActivity(intent)
                        finish()
                    }
                }
                is PictureOfTheDayData.Loading -> {
                    //showLoading()
                }
                is PictureOfTheDayData.Error -> {
                    Snackbar.make(
                            findViewById(R.id.splash_activity),
                            "Loading error\n${it.error.message}",
                            Snackbar.LENGTH_INDEFINITE
                    ).apply{
                        setAction("close") { finish() }
                        this.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines = SNACKBAR_MAX_LINES
                        show()
                    }
                }
            }
        })

    }

}