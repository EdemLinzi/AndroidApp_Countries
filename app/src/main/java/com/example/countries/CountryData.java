package com.example.countries;

import java.util.ArrayList;

public class CountryData {

    private String name;
    private String imageUrl;
    private Integer area;
    private Integer population;
    private Double lat,lng ;


    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }


    public Double getLat(){
        return lat;
    }

    public Double getLng(){
        return lng;
    }

    public void setLatlng(Double lat,Double lng){
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }




}
