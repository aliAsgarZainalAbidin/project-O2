
package com.oa.needyou.pekerja.beranda.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SaldoResponModel {

    @SerializedName("kode")
    private Long mKode;
    @SerializedName("result")
    private List<ResultSaldo> mResultSaldo;

    public Long getKode() {
        return mKode;
    }

    public void setKode(Long kode) {
        mKode = kode;
    }

    public List<ResultSaldo> getResult() {
        return mResultSaldo;
    }

    public void setResult(List<ResultSaldo> resultSaldo) {
        mResultSaldo = resultSaldo;
    }

}
