package com.projetdev.malo.musichall.fragments.collection

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.projetdev.malo.musichall.R
import com.projetdev.malo.musichall.Utils.Constant
import com.projetdev.malo.musichall.adapters.details.album.AlbumAdapter
import com.projetdev.malo.musichall.adapters.main.MainRVDecorator
import com.projetdev.malo.musichall.api.ApiCall


class DiscCollectionFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null


    private val api: ApiCall = ApiCall(Constant.ADDRESS)

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: LinearLayoutManager
    private lateinit var adapter: AlbumAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val rootView = inflater.inflate(R.layout.fragment_disc_collection, container, false)
        recyclerView = rootView.findViewById(R.id.disc_collection_fragment_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = AlbumAdapter(api.getAlbumCollection(), activity)
        recyclerView.apply {
            setHasFixedSize(true)
            addItemDecoration(MainRVDecorator(150))
        }

        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.adapter = AlbumAdapter(api.getAlbumCollection(), activity)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }
}
