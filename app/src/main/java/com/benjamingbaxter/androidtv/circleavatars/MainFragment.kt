/*
 * Copyright 2018 Benjamin Baxter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.benjamingbaxter.androidtv.circleavatars

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v17.leanback.app.BrowseSupportFragment
import android.support.v17.leanback.widget.ArrayObjectAdapter
import android.support.v17.leanback.widget.HeaderItem
import android.support.v17.leanback.widget.ListRow
import android.support.v17.leanback.widget.ListRowPresenter
import android.support.v17.leanback.widget.Presenter
import android.view.ViewGroup
import android.widget.TextView
import com.benjamingbaxter.androidtv.circleavatars.R.drawable

class MainFragment : BrowseSupportFragment() {

    private lateinit var rowsAdapter: ArrayObjectAdapter

    val customListRowPresenter = object : ListRowPresenter() {
        override fun isUsingDefaultListSelectEffect() = false
    }.apply {
        shadowEnabled = false
    }

    val defaultListRowPresenter = ListRowPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rowsAdapter = ArrayObjectAdapter(customListRowPresenter)

        title = getString(R.string.app_name)
        headersState = BrowseSupportFragment.HEADERS_DISABLED
        isHeadersTransitionOnBackEnabled = true

        adapter = rowsAdapter

        loadAdapter()

        setOnItemViewClickedListener { _, item, _, _ ->
            if( item is String ) {
                val presenter = if( item == DEFAULT_LISTROWPRESENTER_VALUE ) {
                    defaultListRowPresenter
                } else {
                    customListRowPresenter
                }
                rowsAdapter = ArrayObjectAdapter(presenter)
                loadAdapter()
                adapter = rowsAdapter
            }
        }
    }

    private fun loadAdapter() {
        val people = mutableListOf<@DrawableRes Int>(
            drawable.avatar_bryce_dallas_howard,
            drawable.avatar_chris_pratt,
            drawable.avatar_henry_cavil,
            drawable.avatar_tom_cruise,
            drawable.avatar_zoey_oneill
        )
        loadPeople("Trending People", people, true)
        loadPeople("Cast of Some Movie", people, false)

        addOptions()
    }

    private fun loadPeople(header: String, people: MutableList<Int>, asCircle: Boolean) {

        val presenterSelector = PersonImageCardViewPresenter(asCircle)

        val personAdapter = people.toArrayObjectAdapter(presenterSelector)

        rowsAdapter.add(ListRow(HeaderItem(header), personAdapter))
    }

    private fun addOptions() {

        val optionsAdapter = ArrayObjectAdapter(object : Presenter() {
            override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
                val view = TextView(parent?.context)
                view.isFocusable = true
                view.isFocusableInTouchMode = true
                view.setPadding(10, 10, 10, 10)
                return ViewHolder(view)
            }

            override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
                val imageCardView = viewHolder?.view as TextView
                imageCardView.text = item as String
            }

            override fun onUnbindViewHolder(p0: ViewHolder?) {}
        })

        optionsAdapter.add(DEFAULT_LISTROWPRESENTER_VALUE)
        optionsAdapter.add(CUSTOM_LISTROWPRESENTER_VALUE)

        rowsAdapter.add(ListRow(HeaderItem("Options"), optionsAdapter))
    }

    companion object {
        private const val DEFAULT_LISTROWPRESENTER_VALUE = "Default ListRowPresenter"
        private const val CUSTOM_LISTROWPRESENTER_VALUE = "Custom ListRowPresenter"
    }
}