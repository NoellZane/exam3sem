/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

/**
 *
 * @author Noell Zane
 */
public class JokeDTO {

    private String id;
    private String joke;

    public JokeDTO(String id, String joke) {
        this.id = id;
        this.joke = joke;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public String getJoke() {
        return joke;
    }

    public String getId() {
        return id;
    }
    

}
