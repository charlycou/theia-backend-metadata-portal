/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation;

import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.GeometryGeoJSON;



/**
 * Class representing a feature geolocalised on a profil or transect sampled by the observation.
 * It can be a profil measuring a the flow of a glacier
 * @author coussotc
 */
public class SamplingCurve extends SamplingFeature {
    /**
     * The geometry of the profil or transect
     */
    private GeometryGeoJSON geometry;

    public GeometryGeoJSON getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryGeoJSON geometry) {
        this.geometry = geometry;
    }
    
    
    
}
