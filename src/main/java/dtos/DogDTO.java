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
public class DogDTO {
    
    private String message;

    public DogDTO(String url) {
        this.message = url;
    }

    public String getMessage() {
        return message;
    }
    
    
}
