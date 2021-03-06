package br.iesb.songs.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.iesb.songs.R
import br.iesb.songs.view_model.PlaylistViewModel
import br.iesb.songs.views.PrincipalActivity
import br.iesb.songs.views.adapter.MusicAdapter
import kotlinx.android.synthetic.main.fragment_playlist_songs.*

class PlaylistSongsFragment(
    private val playlist: String,
    private val principalView: PrincipalActivity
) :
    Fragment() {
    private val viewModelP: PlaylistViewModel by lazy {
        ViewModelProvider(this).get(PlaylistViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playlist_songs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        namePlaylistSongs.text = playlist
        closePlaylistSongs.setOnClickListener { dismiss() }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val adapter = context?.let {
            MusicAdapter(
                it,
                mutableListOf(),
                activity,
                viewModelP,
                "PLAYLIST",
                playlist,
                null,
                principalView,
                null
            )
        }
        playlistSongsRecyclerView.layoutManager = LinearLayoutManager(context)
        playlistSongsRecyclerView.adapter = adapter

        viewModelP.allSongs.observe(viewLifecycleOwner, Observer { musics ->
            adapter?.musicSet = musics.toMutableList()
            adapter?.notifyDataSetChanged()

            if (adapter?.itemCount == 0) {
                textToGoListPlayList.visibility = View.VISIBLE
            } else {
                textToGoListPlayList.visibility = View.GONE
            }

        })

        viewModelP.playlist(playlist)
    }

    private fun dismiss() {
        val manager = activity?.supportFragmentManager

        manager?.findFragmentByTag("playlistSongs")?.let { it1 ->
            manager.beginTransaction().remove(
                it1
            ).commit()
        }
    }
}
