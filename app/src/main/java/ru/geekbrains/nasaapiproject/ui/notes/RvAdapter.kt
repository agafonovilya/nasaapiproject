package ru.geekbrains.nasaapiproject.ui.notes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_note.view.*
import ru.geekbrains.nasaapiproject.R

class RvAdapter(private var data: MutableList<Note>, private val dragListener: OnStartDragListener) :
        RecyclerView.Adapter<RvAdapter.ViewHolder>(), ItemTouchHelperAdapter {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position].title, data[position].text)
    }

    override fun getItemCount(): Int  = data.size

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        data.removeAt(fromPosition).apply {
            data.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ItemTouchHelperViewHolder {

        fun bind(title: String, text: String) {
            itemView.note_title.hint = title
            itemView.note_text.text = text

            itemView.addItemImageView.setOnClickListener { addItem() }
            itemView.removeItemImageView.setOnClickListener { removeItem() }
            itemView.note_moveItemUp.setOnClickListener { moveUp() }
            itemView.note_moveItemDown.setOnClickListener { moveDown() }
            itemView.note_title.setOnClickListener { changeTextVisibility() }

            itemView.dragHandleImageView.setOnTouchListener { _, event ->
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(this)
                }
                false
            }
        }

        private fun changeTextVisibility() {
            itemView.note_text.visibility = if (itemView.note_text.isVisible) View.GONE else View.VISIBLE
            notifyItemChanged(layoutPosition)
        }

        private fun addItem() {
            data.add(layoutPosition, generateItem())
            notifyItemInserted(layoutPosition)
        }

        private fun generateItem() = Note("newTitle", "newText")


        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun moveUp() {
            layoutPosition.takeIf { it > 0 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        private fun moveDown() {
            layoutPosition.takeIf { it < data.size - 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition + 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(Color.WHITE)
        }

    }


}