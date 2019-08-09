/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation;

import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.GeometryGeoJSON;

/**
 *
 * @author coussotc
 */
public class SamplingGeometry extends SamplingFeature{
    
    private GeometryGeoJSON geometry;

//    public SamplingGeometry(I18n name, String type, Properties properties) {
//        super(name, type, properties);
//    }

    public GeometryGeoJSON getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryGeoJSON geometry) {
        this.geometry = geometry;
    }
    
    
    
}
