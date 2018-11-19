package br.edu.ifspsaocarlos.sdm.postcardcollection.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.postcardcollection.R;

public class Postcard {
    private long userId;
    private Bitmap image;
    private String title;
    private String countryOfOrigin;
    private String cityOfOrigin;
    private String sender;
    private Date dateOfAcquisition;
    private String tags;

    public Postcard() {
    }

//    public Postcard(){
//
//    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getCityOfOrigin() {
        return cityOfOrigin;
    }

    public void setCityOfOrigin(String cityOfOrigin) {
        this.cityOfOrigin = cityOfOrigin;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Date getDateOfAcquisition() {
        return dateOfAcquisition;
    }

    public void setDateOfAcquisition(Date dateOfAcquisition) {
        this.dateOfAcquisition = dateOfAcquisition;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

//    private List<Postcard> postcardList;
//    private void initializeData(){
//        postcardList = new ArrayList<>();
//        postcardList.add(new Postcard("Fillipe Cordeiro", "31 anos", R.drawable.fillipe));
//        postcardList.add(new Postcard("Joao da Silva", "25 anos", R.drawable.joao));
//        postcardList.add(new Postcard("Maria Dolores", "35 anos", R.drawable.maria));
//    }

}
