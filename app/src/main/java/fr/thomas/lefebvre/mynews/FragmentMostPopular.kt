package fr.thomas.lefebvre.mynews


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_fragment_top_stories.*


class FragmentMostPopular : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_fragment_top_stories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tv_fragment_name.text="Most Popular"
    }
}
