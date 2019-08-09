/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation;

import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.GeometryGeoJSON;


/**
 * Class representing a feature geolocalised on a Surface sampled by the observation.
 * It can be Land use map.
 * @author coussotc
 */
public class SamplingSurface extends SamplingFeature {
    /**
     * The polygon or polygons of the surface 
     */
    private GeometryGeoJSON geometry;

    public GeometryGeoJSON getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryGeoJSON geometry) {
        this.geometry = geometry;
    }
  
    
}
