/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.geometry;

import java.util.List;

/**
 *  Class representing position in order to make easier the construction of Geometrical object 
 * @author coussotc
 */
public class Position {
    /**
     * double[2] or double[3] reprensenting longitude, latitude or longitude, latitude, altitude
     */
    private List<Double> position;

    public List<Double> getPosition() {
        return position;
    }

    public void setPosition(List<Double> position) {
        this.position = position;
    }

    public Position(List<Double> position) {
        this.position = position;
    }


   
    
    
}
