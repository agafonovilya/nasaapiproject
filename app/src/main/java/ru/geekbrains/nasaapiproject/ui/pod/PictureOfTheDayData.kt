package ru.geekbrains.nasaapiproject.ui.pod

import ru.geekbrains.nasaapiproject.ui.api.PodServerResponseData

sealed class PictureOfTheDayData {
    data class Success(val serverResponseData: PodServerResponseData) : PictureOfTheDayData()
    data class Error(val error: Throwable) : PictureOfTheDayData()
    data class Loading(val progress: Int?) : PictureOfTheDayData()
}