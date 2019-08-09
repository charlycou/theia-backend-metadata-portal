/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation;

import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.GeometryGeoJSON;



/**
 * Class representing a feature a geolocalised volume sampled by the observation.
 * @author coussotc
 */
public class SamplingSolid extends SamplingFeature {
    /**
     * The polygon or polygons of the volume
     */
    private GeometryGeoJSON geometry;

//    public SamplingSolid(I18n name, String type, Properties properties) {
//        super(name, type, properties);
//    }

    public GeometryGeoJSON getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryGeoJSON geometry) {
        this.geometry = geometry;
    }
    
    
     
}
