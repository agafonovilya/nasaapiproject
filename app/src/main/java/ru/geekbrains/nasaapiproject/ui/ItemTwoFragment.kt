package ru.geekbrains.nasaapiproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.geekbrains.nasaapiproject.R


class ItemTwoFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_item_two, container, false)
    }

    companion object {
        fun newInstance(): ItemTwoFragment {
            return ItemTwoFragment()
        }
    }
}