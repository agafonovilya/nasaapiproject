package ru.geekbrains.nasaapiproject.ui.randomlypicture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrains.nasaapiproject.BuildConfig
import ru.geekbrains.nasaapiproject.ui.api.PodRetrofitImpl
import ru.geekbrains.nasaapiproject.ui.api.PodServerResponseData
import ru.geekbrains.nasaapiproject.ui.pod.PictureOfTheDayData

private const val POD_QUANTITY = 10

class PodSomeQuantityViewModel(
        private val liveDataForViewToObserve: MutableLiveData<RandomlyPictureData> = MutableLiveData(),
        private val retrofitImpl: PodRetrofitImpl = PodRetrofitImpl()
) :
        ViewModel() {

    fun getData(): LiveData<RandomlyPictureData> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = RandomlyPictureData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PictureOfTheDayData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getSomeQuantityOfPictures(apiKey, POD_QUANTITY).enqueue(object :
                    Callback<ArrayList<PodServerResponseData>> {
                override fun onResponse(
                        call: Call<ArrayList<PodServerResponseData>>,
                        response: Response<ArrayList<PodServerResponseData>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                                RandomlyPictureData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                    RandomlyPictureData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                    RandomlyPictureData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<PodServerResponseData>>, t: Throwable) {
                    liveDataForViewToObserve.value = RandomlyPictureData.Error(t)
                }
            })
        }
    }
}