package br.edu.ifspsaocarlos.sdm.postcardcollection.model;

import android.graphics.Bitmap;

import java.util.Date;

public class Postcard {
    private long userId;
    private long postcardId;
    private String imageUrl;
    private String title;
    private String countryOfOrigin;
    private String cityOfOrigin;
    private String sender;
    private String dateOfAcquisition;
    private String tags;

    public Postcard() {
    }

    public Postcard(long userId, long postcardId, String imageUrl, String title, String countryOfOrigin,
                    String cityOfOrigin, String sender, String dateOfAcquisition, String tags) {
        this.userId = userId;
        this.postcardId = postcardId;
        this.imageUrl = imageUrl;
        this.title = title;
        this.countryOfOrigin = countryOfOrigin;
        this.cityOfOrigin = cityOfOrigin;
        this.sender = sender;
        this.dateOfAcquisition = dateOfAcquisition;
        this.tags = tags;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPostcardId() {
        return postcardId;
    }

    public void setPostcardId(long postcardId) {
        this.postcardId = postcardId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getDateOfAcquisition() {
        return dateOfAcquisition;
    }

    public void setDateOfAcquisition(String dateOfAcquisition) {
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
