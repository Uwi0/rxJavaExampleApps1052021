package com.kakapo.rxjavaappexample.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.kakapo.rxjavaappexample.adapter.MainAdapter
import com.kakapo.rxjavaappexample.databinding.ActivityMainBinding
import com.kakapo.rxjavaappexample.model.CryptoCurrencyService
import com.kakapo.rxjavaappexample.model.Cryptos
import com.kakapo.rxjavaappexample.utils.Constant
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
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
        setupOkHttpClient()

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

        retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        callEndPoints()
    }

    private fun callEndPoints(){

        val cryptoCurrencyService: CryptoCurrencyService =
            retrofit.create(CryptoCurrencyService::class.java)

        val btcObservable: @NonNull Observable<@NonNull MutableList<Cryptos.Market>>? = cryptoCurrencyService.getCoinData("btc-usd")
            .map { result ->
                Observable.fromIterable(result.ticker.markets)
            }.flatMap{ x ->
                x
            }.filter{ y ->
                y.market = "btc-usd"
                true
            }
            .toList()
            .toObservable()

        val ethObservable: @NonNull Observable<@NonNull MutableList<Cryptos.Market>>? = cryptoCurrencyService.getCoinData("eth-usd")
            .map { result ->
                Observable.fromIterable(result.ticker.markets)
            }.flatMap { x ->
                x
            }.filter{ y ->
                y.market= "eth-usd"
                true
            }
            .toList()
            .toObservable()


        Observable.merge(btcObservable, ethObservable)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResult, this::handleError)
    }

    private fun handleResult(marketList: MutableList<Cryptos.Market>){
        if(marketList.size != 0){
            mainAdapter.setData(marketList)
        }else{
            Toast.makeText(
                this,
                "No Result",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun handleError(t: Throwable) {
        Toast.makeText(
            this,
            "ERROR IN FETCHING API RESPONSE. Try again",
            Toast.LENGTH_LONG
        ).show()
    }
}