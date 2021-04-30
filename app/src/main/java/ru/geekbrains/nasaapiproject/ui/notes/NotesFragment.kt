package ru.geekbrains.nasaapiproject.ui.notes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_notes.*
import ru.geekbrains.nasaapiproject.R

class NotesFragment: Fragment(R.layout.fragment_notes) {

    companion object {
        fun newInstance() = NotesFragment()
    }

    lateinit var itemTouchHelper: ItemTouchHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = arrayListOf(Note("title 1", "text1"),
                Note("title 2", "text2"),
                Note("title 3", "text3"),
                Note("title 4", "text4"),
                Note("title 5", "text5"),
        )

        val adapter = RvAdapter(data,
                object : OnStartDragListener {
                    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                        itemTouchHelper.startDrag(viewHolder)
                    }
                }
        )
        recyclerView.adapter = adapter

        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

}