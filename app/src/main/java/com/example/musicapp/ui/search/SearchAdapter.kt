package com.example.musicapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.entities.Artist
import com.example.musicapp.data.entities.Searchable
import com.example.musicapp.data.entities.Track
import com.example.musicapp.databinding.ArtistItemBinding
import com.example.musicapp.databinding.TrackItemBinding
import com.example.musicapp.ui.adapters.OnArtistClickListener
import com.example.musicapp.ui.adapters.OnTrackClickListener
import com.squareup.picasso.Picasso

class SearchAdapter(
    private val items: MutableList<Searchable>,
    private val trackListener: OnTrackClickListener,
    private val artistListener: OnArtistClickListener

) : RecyclerView.Adapter<SearchAdapter.CustomViewHolder>() {

    fun reloadItems(newItems: List<Searchable>){
        items.clear()
        items.addAll(newItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ARTIST_TYPE -> {
                CustomViewHolder(ArtistItemBinding.inflate(inflater, parent, false))
            }
            TRACK_TYPE -> {
                CustomViewHolder(TrackItemBinding.inflate(inflater, parent, false))
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        when (holder.itemViewType) {
            ARTIST_TYPE -> {
                val artist = items[position] as Artist
                holder.artistBinding?.apply {
                    tvName.text = artist.name
                    Picasso.with(imgArtist.context)
                        .load(artist.picture_medium)
                        .into(imgArtist)
                    constraint.setOnClickListener {
                        artistListener.onArtistItemClick(artist)
                    }
                }
            }
            TRACK_TYPE -> {
                val track = items[position] as Track
                holder.trackBinding?.apply {
                    tvTitle.text = track.title
                    tvArtist.text = track.artist.name
                    Picasso.with(picture.context)
                        .load(track.album.cover_medium)
                        .into(picture)
                    constraint.setOnClickListener {
                        trackListener.onTrackItemClick(track)
                    }
                }
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int =
        when (items[position]) {
            is Artist -> ARTIST_TYPE
            is Track -> TRACK_TYPE
            else -> throw IllegalArgumentException()
        }


    class CustomViewHolder : RecyclerView.ViewHolder {
        var artistBinding: ArtistItemBinding? = null
        var trackBinding: TrackItemBinding? = null

        constructor(trackBinding: TrackItemBinding) : super(trackBinding.root) {
            this.trackBinding = trackBinding
        }

        constructor(artistBinding: ArtistItemBinding) : super(artistBinding.root) {
            this.artistBinding = artistBinding
        }

    }

    companion object {
        const val ARTIST_TYPE = 0
        const val TRACK_TYPE = 1
    }
}



