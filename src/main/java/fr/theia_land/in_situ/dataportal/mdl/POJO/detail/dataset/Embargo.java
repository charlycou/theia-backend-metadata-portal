/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset;

import java.util.List;

/**
 * Class representing  the embargo set on data of a given dataset
 * @author coussotc
 */
public class Embargo {
    /**
     * duration of the embargo in days from the current date
     */
    private  int duration;
    /**
     * Ordered list of user email address that can overstep the embargo 
     */
    private List<String> priviledgedUsers;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<String> getPriviledgedUsers() {
        return priviledgedUsers;
    }

    public void setPriviledgedUsers(List<String> priviledgedUsers) {
        this.priviledgedUsers = priviledgedUsers;
    }   
    
}
