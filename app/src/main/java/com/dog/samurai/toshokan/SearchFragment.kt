package com.dog.samurai.toshokan

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.list_item.*

class SearchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    lateinit var toshoAdapter: ToshoAdapter
    lateinit var toshoViewModel: ToshoViewModel
    lateinit var prefItems: List<String>
    lateinit var prefViewModel: PrefViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toshoViewModel = ViewModelProviders.of(this).get(ToshoViewModel::class.java)
        prefViewModel = ViewModelProviders.of(this).get(PrefViewModel::class.java)
        App.appComponent.inject(toshoViewModel)
        App.appComponent.inject(prefViewModel)

        setSpinner()
    }

    fun setSpinner() {
        prefViewModel.getData().subscribe({
            prefItems = it.result.map {
                it.prefName
            }
            val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, prefItems)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = arrayAdapter
        }, {
            it.printStackTrace()
        })

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                search(prefItems[position])
            }

        }


    }

    fun search(searchWord: String?) {
        toshoViewModel.getData("", searchWord)
                .subscribe({
                    toshoAdapter = ToshoAdapter()

                    toshoList.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = toshoAdapter
                        setHasFixedSize(true)
                    }

                    toshoAdapter.setItem(it)
                    setListener()

                }, {
                    it.printStackTrace()
                }, {
                    searchedSubject.onNext(true)
                })
    }

    val searchedSubject: PublishSubject<Boolean> = PublishSubject.create()

    private fun setListener() {
        toshoAdapter.listener = object : ToshoAdapter.Listener {
            override fun onClickWeb(url: String) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }

            override fun onClickMap(geocode: String, address: String) {
                val latLong = geocode.split(",")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:${latLong[1]},${latLong[0]}?q=$address"))
                startActivity(intent)
            }

            override fun onClickPhone(number: String) {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
                startActivity(intent)
            }

        }
    }

}