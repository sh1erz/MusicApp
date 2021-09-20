package com.example.musicapp.ui.search

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
import com.example.musicapp.R
import com.example.musicapp.data.entities.Artist
import com.example.musicapp.data.entities.Searchable
import com.example.musicapp.data.entities.Track
import com.example.musicapp.databinding.FragmentSearchBinding
import com.example.musicapp.ui.adapters.OnArtistClickListener
import com.example.musicapp.ui.adapters.OnTrackClickListener
import com.example.musicapp.ui.details.ArtistFragment
import com.example.musicapp.ui.details.TrackFragment
import com.example.musicapp.ui.main.model.LOG
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SearchFragment : Fragment(), OnArtistClickListener, OnTrackClickListener {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var cursorAdapter: SimpleCursorAdapter
    private val recyclerAdapter: SearchAdapter = SearchAdapter(mutableListOf(), this, this)
    private val disposables = CompositeDisposable()
        private val subscriber = object : Observer<List<String>> {
        override fun onNext(names: List<String>) {
            val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
            names.forEachIndexed { index, s ->
                cursor.addRow(arrayOf(index, s))
            }
            cursorAdapter.changeCursor(cursor)
        }

        override fun onSubscribe(d: Disposable) {
            disposables.add(d)
        }

        override fun onError(e: Throwable) {
            Log.i(LOG, "  -- subscriber onError err: ${e.message ?: "no mess"}")
        }

        override fun onComplete() {
        }
    }

    override fun onArtistItemClick(artist: Artist) {
        findNavController().navigate(
            R.id.action_search_to_artistDetails,
            bundleOf(ArtistFragment.ARTIST to artist)
        )
    }

    override fun onTrackItemClick(track: Track) {
        //add to db
        viewModel.addTrack(track)
        findNavController().navigate(
            R.id.action_search_to_trackDetails,
            bundleOf(TrackFragment.TRACK to track)
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cursorAdapter = getAdapter()
        Log.i(LOG, "onViewCreated: $viewModel")
        binding.recyclerSearch.adapter = recyclerAdapter
        viewModel.searchedList.observe(viewLifecycleOwner) { items ->
                reloadList(items)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.dispose()
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

        viewModel.publishSubject.debounce(500, TimeUnit.MILLISECONDS)
            .switchMap { query -> viewModel.updateSuggestions(query) }
            .subscribe(subscriber)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText.length > 2) {
                    viewModel.publishSubject.onNext(newText)
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.search(it) }
                return true
            }
        })

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

    private fun reloadList(items: List<Searchable>) {
        recyclerAdapter.apply {
            reloadItems(items)
            notifyDataSetChanged()
        }
    }


}