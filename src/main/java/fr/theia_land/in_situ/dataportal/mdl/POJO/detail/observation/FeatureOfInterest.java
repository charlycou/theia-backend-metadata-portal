/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation;

/**
 * The feature of interest is an abstraction of a real-world object. It's the spatial object
 * from which one characteristic is sampled by the observation
 * @author coussotc
 */
public class FeatureOfInterest {
    /**
     * Spatial representation of the feature of interest
     */
    private SamplingFeature samplingFeature;

    public SamplingFeature getSamplingFeature() {
        return samplingFeature;
    }

    public void setSamplingFeature(SamplingFeature samplingFeature) {
        this.samplingFeature = samplingFeature;
    }
 
}
