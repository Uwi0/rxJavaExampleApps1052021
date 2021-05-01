package com.kakapo.rxjavaappexample.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.kakapo.rxjavaappexample.R
import com.kakapo.rxjavaappexample.adapter.MainAdapter
import com.kakapo.rxjavaappexample.databinding.ActivityMainBinding
import com.kakapo.rxjavaappexample.model.CryptoCurrencyService
import com.kakapo.rxjavaappexample.utils.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var retrofit: Retrofit
    private lateinit var mainAdapter: MainAdapter
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mainAdapter = MainAdapter(this)
        setupRecyclerView()

    }

    private fun setupRecyclerView(){
        recyclerView = mBinding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mainAdapter
    }

    private fun setupOkHttpClient(){
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun callEndPoints(){

        val cryptoCurrencyService: CryptoCurrencyService = retrofit.create(CryptoCurrencyService::class.java)

        
    }
}