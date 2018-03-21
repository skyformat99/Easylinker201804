package com.sucheon.box.server.app.model.device;

import com.sucheon.box.server.app.model.base.BaseEntity;

import javax.persistence.Entity;
import java.util.Date;

/**
 * 声音模型
 * Date integer,
 * STD real,
 * Mean real,
 * MeanHf real,
 * MeanLf real,
 * Feature1 text,
 * Feature2 text,
 * Feature3 text,
 * Feature4 text,
 * BandSpectrum text,
 * PeakFreqs text,
 * PeakPowers text
 */
@Entity
public class AudioData extends BaseEntity{

    private Date date;
    private Double std;
    private Double meanHf;
    private Double meanLf;
    private String feature1;
    private String feature2;
    private String feature3;
    private String feature4;
    private String bandSpectrum;
    private String peakFrequency;
    private String peakPowers;


    public AudioData() {

    }

    public AudioData(Date date, Double std, Double meanHf, Double meanLf, String feature1, String feature2, String feature3, String feature4, String bandSpectrum, String peakFrequency, String peakPowers) {
        this.date = date;
        this.std = std;
        this.meanHf = meanHf;
        this.meanLf = meanLf;
        this.feature1 = feature1;
        this.feature2 = feature2;
        this.feature3 = feature3;
        this.feature4 = feature4;
        this.bandSpectrum = bandSpectrum;
        this.peakFrequency = peakFrequency;
        this.peakPowers = peakPowers;
    }

    public String getPeakFrequency() {
        return peakFrequency;
    }

    public void setPeakFrequency(String peakFrequency) {
        this.peakFrequency = peakFrequency;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getStd() {
        return std;
    }

    public void setStd(Double std) {
        this.std = std;
    }


    public Double getMeanHf() {
        return meanHf;
    }

    public void setMeanHf(Double meanHf) {
        this.meanHf = meanHf;
    }

    public Double getMeanLf() {
        return meanLf;
    }

    public void setMeanLf(Double meanLf) {
        this.meanLf = meanLf;
    }

    public String getFeature1() {
        return feature1;
    }

    public void setFeature1(String feature1) {
        this.feature1 = feature1;
    }

    public String getFeature2() {
        return feature2;
    }

    public void setFeature2(String feature2) {
        this.feature2 = feature2;
    }

    public String getFeature3() {
        return feature3;
    }

    public void setFeature3(String feature3) {
        this.feature3 = feature3;
    }

    public String getFeature4() {
        return feature4;
    }

    public void setFeature4(String feature4) {
        this.feature4 = feature4;
    }

    public String getBandSpectrum() {
        return bandSpectrum;
    }

    public void setBandSpectrum(String bandSpectrum) {
        this.bandSpectrum = bandSpectrum;
    }


    public String getPeakPowers() {
        return peakPowers;
    }

    public void setPeakPowers(String peakPowers) {
        this.peakPowers = peakPowers;
    }
}
