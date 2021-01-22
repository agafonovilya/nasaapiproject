package ru.geekbrains.nasaapiproject.ui.randomlypicture

import ru.geekbrains.nasaapiproject.ui.api.PodServerResponseData

sealed class RandomlyPictureData {
    data class Success(val serverResponseData: ArrayList<PodServerResponseData>) : RandomlyPictureData()
    data class Error(val error: Throwable) : RandomlyPictureData()
    data class Loading(val progress: Int?) : RandomlyPictureData()
}