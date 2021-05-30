
package com.oa.needyou.pekerja.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Result implements Parcelable{

    @SerializedName("alamat_order")
    @Expose
    private String mAlamatOrder;
    @SerializedName("biaya_order")
    @Expose
    private String mBiayaOrder;
    @SerializedName("id_order")
    @Expose
    private String mIdOrder;
    @SerializedName("id_pekerja")
    @Expose
    private String mIdPekerja;
    @SerializedName("id_pelanggan")
    @Expose
    private String mIdPelanggan;
    @SerializedName("jam_order")
    @Expose
    private String mJamOrder;
    @SerializedName("keterangan_order")
    @Expose
    private String mKeteranganOrder;
    @SerializedName("kode_order")
    @Expose
    private String mKodeOrder;
    @SerializedName("latitude_order")
    @Expose
    private String mLatitudeOrder;
    @SerializedName("longitude_order")
    @Expose
    private String mLongitudeOrder;
    @SerializedName("path_order")
    @Expose
    private String mPathOrder;
    @SerializedName("status_order")
    @Expose
    private String mStatusOrder;
    @SerializedName("tanggal_order")
    @Expose
    private String mTanggalOrder;
    @SerializedName("rating_order")
    @Expose
    private String mRatinglOrder;

    public Result(String mAlamatOrder, String mBiayaOrder, String mIdOrder, String mIdPekerja, String mIdPelanggan, String mJamOrder, String mKeteranganOrder, String mKodeOrder, String mLatitudeOrder, String mLongitudeOrder, String mPathOrder, String mStatusOrder, String mTanggalOrder, String mRatinglOrder) {
        this.mAlamatOrder = mAlamatOrder;
        this.mBiayaOrder = mBiayaOrder;
        this.mIdOrder = mIdOrder;
        this.mIdPekerja = mIdPekerja;
        this.mIdPelanggan = mIdPelanggan;
        this.mJamOrder = mJamOrder;
        this.mKeteranganOrder = mKeteranganOrder;
        this.mKodeOrder = mKodeOrder;
        this.mLatitudeOrder = mLatitudeOrder;
        this.mLongitudeOrder = mLongitudeOrder;
        this.mPathOrder = mPathOrder;
        this.mStatusOrder = mStatusOrder;
        this.mTanggalOrder = mTanggalOrder;
        this.mRatinglOrder = mRatinglOrder;
    }

    protected Result(Parcel in) {
        mAlamatOrder = in.readString();
        mBiayaOrder = in.readString();
        mIdOrder = in.readString();
        mIdPekerja = in.readString();
        mIdPelanggan = in.readString();
        mJamOrder = in.readString();
        mKeteranganOrder = in.readString();
        mKodeOrder = in.readString();
        mLatitudeOrder = in.readString();
        mLongitudeOrder = in.readString();
        mPathOrder = in.readString();
        mStatusOrder = in.readString();
        mTanggalOrder = in.readString();
        mRatinglOrder = in.readString();
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    public String getmAlamatOrder() {
        return mAlamatOrder;
    }

    public void setmAlamatOrder(String mAlamatOrder) {
        this.mAlamatOrder = mAlamatOrder;
    }

    public String getmBiayaOrder() {
        return mBiayaOrder;
    }

    public void setmBiayaOrder(String mBiayaOrder) {
        this.mBiayaOrder = mBiayaOrder;
    }

    public String getmIdOrder() {
        return mIdOrder;
    }

    public void setmIdOrder(String mIdOrder) {
        this.mIdOrder = mIdOrder;
    }

    public String getmIdPekerja() {
        return mIdPekerja;
    }

    public void setmIdPekerja(String mIdPekerja) {
        this.mIdPekerja = mIdPekerja;
    }

    public String getmIdPelanggan() {
        return mIdPelanggan;
    }

    public void setmIdPelanggan(String mIdPelanggan) {
        this.mIdPelanggan = mIdPelanggan;
    }

    public String getmJamOrder() {
        return mJamOrder;
    }

    public void setmJamOrder(String mJamOrder) {
        this.mJamOrder = mJamOrder;
    }

    public String getmKeteranganOrder() {
        return mKeteranganOrder;
    }

    public void setmKeteranganOrder(String mKeteranganOrder) {
        this.mKeteranganOrder = mKeteranganOrder;
    }

    public String getmKodeOrder() {
        return mKodeOrder;
    }

    public void setmKodeOrder(String mKodeOrder) {
        this.mKodeOrder = mKodeOrder;
    }

    public String getmLatitudeOrder() {
        return mLatitudeOrder;
    }

    public void setmLatitudeOrder(String mLatitudeOrder) {
        this.mLatitudeOrder = mLatitudeOrder;
    }

    public String getmLongitudeOrder() {
        return mLongitudeOrder;
    }

    public void setmLongitudeOrder(String mLongitudeOrder) {
        this.mLongitudeOrder = mLongitudeOrder;
    }

    public String getmPathOrder() {
        return mPathOrder;
    }

    public void setmPathOrder(String mPathOrder) {
        this.mPathOrder = mPathOrder;
    }

    public String getmStatusOrder() {
        return mStatusOrder;
    }

    public void setmStatusOrder(String mStatusOrder) {
        this.mStatusOrder = mStatusOrder;
    }

    public String getmTanggalOrder() {
        return mTanggalOrder;
    }

    public void setmTanggalOrder(String mTanggalOrder) {
        this.mTanggalOrder = mTanggalOrder;
    }

    public String getmRatinglOrder() {
        return mRatinglOrder;
    }

    public void setmRatinglOrder(String mRatinglOrder) {
        this.mRatinglOrder = mRatinglOrder;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAlamatOrder);
        dest.writeString(mBiayaOrder);
        dest.writeString(mIdOrder);
        dest.writeString(mIdPekerja);
        dest.writeString(mIdPelanggan);
        dest.writeString(mJamOrder);
        dest.writeString(mKeteranganOrder);
        dest.writeString(mKodeOrder);
        dest.writeString(mLatitudeOrder);
        dest.writeString(mLongitudeOrder);
        dest.writeString(mPathOrder);
        dest.writeString(mStatusOrder);
        dest.writeString(mTanggalOrder);
        dest.writeString(mRatinglOrder);
    }
}
