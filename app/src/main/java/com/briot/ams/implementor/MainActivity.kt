package com.briot.ams.implementor

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import io.github.pierry.progress.Progress
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


class RetrofitHelper {
    companion object {
        val BASE_URL = BuildConfig.HOSTNAME;

        val okHttpClient = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
    }
}

class MainActivity : AppCompatActivity() {
    companion object {
        fun showAlert(activity: AppCompatActivity, message: String) {
            AlertDialog.Builder(activity).create().apply {
                setTitle("Alert")
                setMessage(message)
                setButton(AlertDialog.BUTTON_NEUTRAL, "OK", { dialog, _ -> dialog.dismiss() })
                show()
            }
        }

        fun showToast(activity: AppCompatActivity, message: String) {
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
        }

        fun showProgressIndicator(context: Context, message: String): Progress {
            val progress = Progress(context)

            progress.setBackgroundColor(context.resources.getColor(android.R.color.holo_blue_dark))
                    .setMessage(message)
                    .setMessageColor(context.resources.getColor(android.R.color.white))
                    .setProgressColor(context.resources.getColor(android.R.color.white))
                    .show();

            return progress
        }

        fun hideProgress(progress: Progress?) {
            progress?.dismiss()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    override fun onSupportNavigateUp()
            = findNavController(findViewById(R.id.nav_host_fragment)).navigateUp()

}
