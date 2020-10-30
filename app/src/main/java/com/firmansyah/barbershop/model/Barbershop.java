package com.firmansyah.barbershop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Barbershop {
    @SerializedName("id_barber")
    @Expose
    private String idBarber;
    @SerializedName("nama_barber")
    @Expose
    private String namaBarber;
    @SerializedName("gambar_barber")
    @Expose
    private String gambarBarber;
    @SerializedName("alamat_barber")
    @Expose
    private String alamatBarber;
    @SerializedName("deskripsi_barber")
    @Expose
    private String deskripsiBarber;
    @SerializedName("latitude_barber")
    @Expose
    private String latitudeBarber;
    @SerializedName("longitude_barber")
    @Expose
    private String longitudeBarber;

    public String getIdBarber() {
        return idBarber;
    }

    public void setIdBarber(String idBarber) {
        this.idBarber = idBarber;
    }

    public String getNamaBarber() {
        return namaBarber;
    }

    public void setNamaBarber(String namaBarber) {
        this.namaBarber = namaBarber;
    }

    public String getGambarBarber() {
        return gambarBarber;
    }

    public void setGambarBarber(String gambarBarber) {
        this.gambarBarber = gambarBarber;
    }

    public String getAlamatBarber() {
        return alamatBarber;
    }

    public void setAlamatBarber(String alamatBarber) {
        this.alamatBarber = alamatBarber;
    }

    public String getDeskripsiBarber() {
        return deskripsiBarber;
    }

    public void setDeskripsiBarber(String deskripsiBarber) {
        this.deskripsiBarber = deskripsiBarber;
    }

    public String getLatitudeBarber() {
        return latitudeBarber;
    }

    public void setLatitudeBarber(String latitudeBarber) {
        this.latitudeBarber = latitudeBarber;
    }

    public String getLongitudeBarber() {
        return longitudeBarber;
    }

    public void setLongitudeBarber(String longitudeBarber) {
        this.longitudeBarber = longitudeBarber;
    }

}
