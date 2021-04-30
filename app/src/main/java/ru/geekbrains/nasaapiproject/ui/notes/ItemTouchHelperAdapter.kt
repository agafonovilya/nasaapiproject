package ru.geekbrains.nasaapiproject.ui.notes

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int)
}