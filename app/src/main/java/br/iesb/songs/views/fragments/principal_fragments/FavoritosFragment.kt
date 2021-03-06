package br.iesb.songs.views.fragments.principal_fragments

import android.content.Context
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
import kotlinx.android.synthetic.main.fragment_favoritos.*

class FavoritosFragment(context: Context, private val principalView: PrincipalActivity) :
    Fragment() {

    private val viewModelP: PlaylistViewModel by lazy {
        ViewModelProvider(this).get(PlaylistViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favoritos, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRecyclerView()

        favoritesList()
    }

    private fun initRecyclerView() {
        favoritesRecyclerViewFavList.layoutManager = LinearLayoutManager(context)

        val adapter = this.context?.let {
            MusicAdapter(
                it,
                mutableListOf(),
                activity,
                viewModelP,
                "FAVORITE",
                "favorites",
                null,
                principalView,
                null
            )
        }
        favoritesRecyclerViewFavList.adapter = adapter

        viewModelP.allSongs.observe(viewLifecycleOwner, Observer { music ->
            adapter?.musicSet?.clear()
            adapter?.musicSet = music.toMutableList()
            adapter?.notifyDataSetChanged()

            if (adapter?.itemCount == 0) {
                textToGo.visibility = View.VISIBLE
            } else {
                textToGo.visibility = View.GONE
            }

        })
    }

    private fun favoritesList() {
        viewModelP.playlist("favorites")
    }
}
