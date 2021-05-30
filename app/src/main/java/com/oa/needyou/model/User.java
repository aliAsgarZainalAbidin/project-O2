
package com.oa.needyou.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("kode")
    private Long mKode;
    @SerializedName("result")
    private List<DataUser> mDataUser;

    public Long getKode() {
        return mKode;
    }

    public void setKode(Long kode) {
        mKode = kode;
    }

    public List<DataUser> getResult() {
        return mDataUser;
    }

    public void setResult(List<DataUser> dataUser) {
        mDataUser = dataUser;
    }

}
