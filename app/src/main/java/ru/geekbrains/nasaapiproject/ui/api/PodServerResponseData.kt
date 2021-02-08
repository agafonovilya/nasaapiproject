package ru.geekbrains.nasaapiproject.ui.api

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class PodServerResponseData(
    @field:SerializedName("copyright") val copyright: String?,
    @field:SerializedName("date") val date: String?,
    @field:SerializedName("explanation") val explanation: String?,
    @field:SerializedName("media_type") val mediaType: String?,
    @field:SerializedName("title") val title: String?,
    @field:SerializedName("url") val url: String?,
    @field:SerializedName("hdurl") val hdurl: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(copyright)
        parcel.writeString(date)
        parcel.writeString(explanation)
        parcel.writeString(mediaType)
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeString(hdurl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PodServerResponseData> {
        override fun createFromParcel(parcel: Parcel): PodServerResponseData {
            return PodServerResponseData(parcel)
        }

        override fun newArray(size: Int): Array<PodServerResponseData?> {
            return arrayOfNulls(size)
        }
    }
}