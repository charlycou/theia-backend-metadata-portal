/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation;

import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.Point;



/**
 * Class representing a feature geolocalised on on point sampled by the observation.
 * It can be a meteorological station measuring temperature a one given location
 * @author coussotc
 */
public class SamplingPoint extends SamplingFeature {

    /**
     * The position of the station 
     */
    private Point geometry;

    public Point getGeometry() {
        return geometry;
    }

    public void setGeometry(Point geometry) {
        this.geometry = geometry;
    }
    
    
    
}
