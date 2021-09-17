package com.example.musicapp.ui.main

import android.app.SearchManager
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.musicapp.ArtistAdapter
import com.example.musicapp.R
import com.example.musicapp.data.entities.Artist
import com.example.musicapp.databinding.MainFragmentBinding
import com.example.musicapp.ui.adapters.AdapterItemListener
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainFragment : Fragment(), AdapterItemListener {

    private lateinit var binding: MainFragmentBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val recyclerAdapter: ArtistAdapter = ArtistAdapter(mutableListOf(), this)
    private lateinit var cursorAdapter: SimpleCursorAdapter

    private val subscriberM = object : Observer<List<String>> {
        override fun onNext(names: List<String>) {
            val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
            Log.i(LOG, "main subscriber")
            names.forEachIndexed { index, s ->
                cursor.addRow(arrayOf(index, s))
            }
            cursorAdapter.changeCursor(cursor)
        }

        override fun onSubscribe(d: Disposable) {
        }

        override fun onError(e: Throwable) {
        }

        override fun onComplete() {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(layoutInflater, container, false)
        Log.i(LOG, "viewModel: $viewModel")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cursorAdapter = getAdapter()
        binding.recyclerArtist.adapter = recyclerAdapter

        viewModel.getArtists().observe(viewLifecycleOwner) {
            CoroutineScope(Dispatchers.Main).launch {
                retrieveList(it)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_search, menu)

        val searchView =
            SearchView((activity as AppCompatActivity).supportActionBar!!.themedContext)
        val item = menu.findItem(R.id.action_search)

        item.apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }

        searchView.suggestionsAdapter = cursorAdapter

        PublishSubject.create(ObservableOnSubscribe<String> { subscriber ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null && newText.length > 2) {
                        subscriber.onNext(newText)
                    }
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { viewModel.searchArtists(it) }
                    return true
                }
            })
        }).debounce(500, TimeUnit.MILLISECONDS)
            .switchMap { query -> viewModel.updateSuggestions(query) }
            .subscribe(subscriberM)

        searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                val cursor = searchView.suggestionsAdapter.getItem(position) as Cursor
                val selection =
                    cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                searchView.setQuery(selection, false)
                return true
            }
        })

    }

    private fun getAdapter(): SimpleCursorAdapter {
        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.tvItem)
        return SimpleCursorAdapter(
            context,
            R.layout.search_item,
            null,
            from,
            to,
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )
    }

    private fun retrieveList(artists: List<Artist>) {
        recyclerAdapter.apply {
            addArtists(artists)
            notifyDataSetChanged()
        }
    }

    override fun onItemClick(position: Int) {
        findNavController().navigate(
            R.id.action_mainFragment_to_detailsFragment,
            bundleOf("artist_position" to position)
        )
    }

}
