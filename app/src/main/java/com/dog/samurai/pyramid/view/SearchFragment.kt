package com.dog.samurai.pyramid.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dog.samurai.pyramid.App
import com.dog.samurai.pyramid.GlideApp
import com.dog.samurai.pyramid.R
import com.dog.samurai.pyramid.model.FlickrData
import com.dog.samurai.pyramid.model.Prefectures
import com.dog.samurai.pyramid.model.Pyramid
import com.dog.samurai.pyramid.viewModel.FlickrViewModel
import com.dog.samurai.pyramid.viewModel.ResasViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    private lateinit var prefItems: List<String>
    private lateinit var selectedPrefectures: Prefectures
    private lateinit var yearList: List<String>
    private var selectedYear: String = ""
    private lateinit var resasViewModel: ResasViewModel
    private lateinit var flickrViewModel: FlickrViewModel
    private lateinit var compositeDisposable: CompositeDisposable

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resasViewModel = ViewModelProviders.of(activity as MainActivity).get(ResasViewModel::class.java)
        flickrViewModel = ViewModelProviders.of(activity as MainActivity).get(FlickrViewModel::class.java)
        App.appComponent.inject(resasViewModel)
        App.appComponent.inject(flickrViewModel)

        compositeDisposable = CompositeDisposable()

        setPrefSpinner()

        MobileAds.initialize(context, "")
        showAd()
    }

    private fun showAd() {
        val adView = adView
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    private fun setPrefSpinner() {
        val disposable = resasViewModel.getPrefecture().subscribe({ result ->
            prefItems = result.result.map {
                it.prefName
            }

            if (context == null) return@subscribe
            val arrayAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, prefItems)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            pref_spinner.adapter = arrayAdapter

            setPrefSpinnerListener()
            setYearSpinner()
        }, {
            it.printStackTrace()
        })

        compositeDisposable.add(disposable)
    }

    private fun setPrefSpinnerListener() {
        pref_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                selectedPrefectures = resasViewModel.prefectureMap.first {
                    it.prefName == prefItems[position]
                }
                if (selectedYear.isEmpty()) return
                search(selectedYear, selectedPrefectures)
            }
        }
    }

    private fun search(searchYear: String, searchItem: Prefectures) {
        val disposable = Observable.zip(getPyramid(searchYear, searchItem.prefCode), getImage(searchItem.prefName),
                BiFunction<Pyramid, FlickrData, Boolean> { pyramid, imageData ->

                    GlideApp.with(this)
                            .load(imageSelect(imageData))
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .centerCrop()
                            .transition(DrawableTransitionOptions().crossFade())
                            .into(pref_image)

                    pyramid.result.yearLeft.apply {
                        old_count.text = oldAgeCount.toString()
                        old_count.append("人 ($oldAgePercent %)")
                        middle_count.text = middleAgeCount.toString()
                        middle_count.append("人 ($middleAgePercent %)")
                        newage_count.text = newAgeCount.toString()
                        newage_count.append("人 ($newAgePercent %)")

                        val total = oldAgeCount + middleAgeCount + newAgeCount
                        total_count.text = total.toString()
                        total_count.append("人")

                        val support: Float = (middleAgeCount.toFloat() / oldAgeCount.toFloat())
                        val decimal = String.format("%.1f", support)

                        val result = String.format(resources.getString(R.string.result_text), decimal)

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            result_text.text = Html.fromHtml(result, Html.FROM_HTML_MODE_LEGACY)
                        } else {
                            @Suppress("DEPRECATION")
                            result_text.text = Html.fromHtml(result)

                        }

                    }

                    var excludeYoung = 0
                    var includeOld = 0

                    pyramid.result.yearLeft.data.filter {
                        it.ageClass == "15～19歳"
                    }.map {
                        excludeYoung = it.man
                        excludeYoung += it.woman
                    }

                    val realAdultCount = pyramid.result.yearLeft.middleAgeCount - excludeYoung
                    val realSupport = realAdultCount.toFloat() / pyramid.result.yearLeft.oldAgeCount.toFloat()


                    pyramid.result.yearLeft.data.filter {
                        it.ageClass == "65～69歳"
                    }.map {
                        includeOld = it.man
                        includeOld += it.woman
                    }

                    val realWorkerCount = pyramid.result.yearLeft.middleAgeCount - excludeYoung + includeOld

                    var elderly = 0
                    pyramid.result.yearLeft.data.filter {
                        it.ageClass == "70～74歳" || it.ageClass == "75～79歳"
                                || it.ageClass == "80～84歳" || it.ageClass == "85～89歳"
                                || it.ageClass == "90歳～"

                    }.map {
                        elderly += it.man
                        elderly += it.woman
                    }

                    val supportElderly = realWorkerCount.toFloat() / elderly.toFloat()

                    val culcText = String.format(resources.getString(R.string.culc_text),
                            realAdultCount,
                            String.format("%.1f", realSupport),
                            String.format("%.1f", supportElderly)
                    )

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        culc_text.text = Html.fromHtml(culcText, Html.FROM_HTML_MODE_LEGACY)
                    } else {
                        @Suppress("DEPRECATION")
                        culc_text.text = Html.fromHtml(culcText)

                    }

                    title.text = ""
                    title.append("${searchYear}年の${searchItem.prefName}")

                    true

                }).subscribe({}, { it.printStackTrace() })

        compositeDisposable.add(disposable)

    }

    private fun imageSelect(imageData: FlickrData): String {
        imageData.photos.photo.shuffled()
        return imageData.photos.photo.first {
            it.url_h != null && (it.height_h!! <= it.width_h!!)
        }.url_h!!
    }

    private fun getPyramid(searchYear: String, searchCode: Int): Observable<Pyramid> {
        return resasViewModel.getPyramid(searchYear.toInt(), searchCode)
    }

    private fun getImage(searchWord: String): Observable<FlickrData> {
        return flickrViewModel.getData(searchWord)
    }

    private fun setYearSpinner() {
        if (context == null) return
        yearList = listOf("1980", "1985", "1990", "1995", "2000", "2005", "2010", "2015", "2020", "2025", "2030", "2035", "2040")

        val arrayAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, yearList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        year_spinner.adapter = arrayAdapter

        setYearSpinnerListener()
    }

    private fun setYearSpinnerListener() {
        year_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                selectedYear = yearList[position]
                search(selectedYear, selectedPrefectures)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }


}
