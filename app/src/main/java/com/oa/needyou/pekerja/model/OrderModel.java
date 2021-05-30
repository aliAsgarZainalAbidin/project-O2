
package com.oa.needyou.pekerja.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OrderModel {

    @SerializedName("kode")
    private Long mKode;
    @SerializedName("result")
    private List<Result> mResult;

    public Long getKode() {
        return mKode;
    }

    public void setKode(Long kode) {
        mKode = kode;
    }

    public List<Result> getResult() {
        return mResult;
    }

    public void setResult(List<Result> result) {
        mResult = result;
    }

}
