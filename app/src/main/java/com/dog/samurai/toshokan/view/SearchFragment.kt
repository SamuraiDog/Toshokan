package com.dog.samurai.toshokan.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dog.samurai.toshokan.App
import com.dog.samurai.toshokan.GlideApp
import com.dog.samurai.toshokan.R
import com.dog.samurai.toshokan.model.FlickrData
import com.dog.samurai.toshokan.model.Prefectures
import com.dog.samurai.toshokan.model.VisitorResult
import com.dog.samurai.toshokan.view.adapter.VisitorAdapter
import com.dog.samurai.toshokan.viewModel.FlickrViewModel
import com.dog.samurai.toshokan.viewModel.ResasViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    private lateinit var visitorAdapter: VisitorAdapter
    private lateinit var prefItems: List<String>
    private lateinit var selectedPrefectures: Prefectures
    private lateinit var yearList: List<String>
    private var selectedYear: String = "2012"
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
                search(selectedYear, selectedPrefectures)
            }
        }
    }

    private fun search(searchYear: String, searchItem: Prefectures) {
        Observable.zip(getVisitor(searchYear, searchItem.prefCode), getImage(searchItem.prefName),
                BiFunction<VisitorResult, FlickrData, Any> { visitorResult, imageData ->

                    GlideApp.with(this)
                            .load(imageSelect(imageData))
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .centerCrop()
                            .transition(DrawableTransitionOptions().crossFade())
                            .into(pref_image)

                    visitorAdapter = VisitorAdapter()

                    toshoList.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = visitorAdapter
                        setHasFixedSize(true)
                    }

                    visitorAdapter.setItem(visitorResult)

                }).subscribe({}, { it.printStackTrace() })

    }

    private fun imageSelect(imageData: FlickrData): String {
        return imageData.photos.photo.first {
            it.url_h != null && (it.height_h!! <= it.width_h!!)
        }.url_h!!
    }

    private fun getVisitor(searchYear: String, searchCode: Int): Observable<VisitorResult> {
        return resasViewModel.getFromData(searchYear.toInt(), searchCode)
    }

    private fun getImage(searchWord: String): Observable<FlickrData> {
        return flickrViewModel.getData(searchWord)
    }

    private fun setYearSpinner() {
        if (context == null) return
        yearList = listOf("2012", "2013", "2014", "2015", "2016")

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
