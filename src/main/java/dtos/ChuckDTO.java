/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.List;

/**
 *
 * @author Noell Zane
 */
    public class ChuckDTO {
        
    List<String> categories;
    private String id;
    private String url;
    private String value;

    public ChuckDTO(List<String> categories, String id, String url, String value) {
        this.categories = categories;
        this.id = id;
        this.url = url;
        this.value = value;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    

    public String getId() {
        return id;
    }

    public String getJoke() {
        return value;
    }

}