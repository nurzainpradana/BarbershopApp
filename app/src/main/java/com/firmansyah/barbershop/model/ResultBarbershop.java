package com.firmansyah.barbershop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultBarbershop {
    @SerializedName("result")
    @Expose
    private List<Barbershop> result = null;

    public List<Barbershop> getResult() {
        return result;
    }

    public void setResult(List<Barbershop> result) {
        this.result = result;
    }
}
