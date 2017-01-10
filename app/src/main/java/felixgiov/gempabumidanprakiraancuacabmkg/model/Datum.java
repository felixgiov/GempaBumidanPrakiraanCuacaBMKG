package felixgiov.gempabumidanprakiraancuacabmkg.model;

/**
 * Created by felix on 9 Jan 2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("waktu")
    @Expose
    private String waktu;
    @SerializedName("lintang_bujur")
    @Expose
    private String lintangBujur;
    @SerializedName("magnitudo")
    @Expose
    private String magnitudo;
    @SerializedName("kedalaman")
    @Expose
    private String kedalaman;
    @SerializedName("wilayah")
    @Expose
    private String wilayah;
    @SerializedName("img")
    @Expose
    private String img;

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getLintangBujur() {
        return lintangBujur;
    }

    public void setLintangBujur(String lintangBujur) {
        this.lintangBujur = lintangBujur;
    }

    public String getMagnitudo() {
        return magnitudo;
    }

    public void setMagnitudo(String magnitudo) {
        this.magnitudo = magnitudo;
    }

    public String getKedalaman() {
        return kedalaman;
    }

    public void setKedalaman(String kedalaman) {
        this.kedalaman = kedalaman;
    }

    public String getWilayah() {
        return wilayah;
    }

    public void setWilayah(String wilayah) {
        this.wilayah = wilayah;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}