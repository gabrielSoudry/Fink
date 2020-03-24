package com.esilv.fink.Model;

import java.util.Random;

class User {
    private String name;
    private String imageURL;
    private String key;
    private String description;

    public User () {
        Random random = new Random();
        int nombreAleatoire = 0 + (int)(Math.random() * ((100 - 0) + 1));


        this.imageURL= "https://i.picsum.photos/id/"+nombreAleatoire+ "/200/200.jpg";
        //empty constructor needed
    }
    public User (int position){

    }

    public User(String name, String imageUrl ,String Des) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        this.name = name;
        this.imageURL = imageUrl;
        this.description = Des;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImageUrl() {
        return imageURL;
    }
    public void setImageUrl(String imageUrl) {
        this.imageURL = imageUrl;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
}
