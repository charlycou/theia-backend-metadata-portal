/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.litePopup;

import fr.theia_land.in_situ.dataportal.mdl.POJO.FeatureOfInterest;

/**
 *
 * @author coussotc
 */
public class ObservationLitePopup {

    /**
     * Id of the observation. Corresponds to the trigramme of the provider, the number of the dataset, and the number of
     * the observation of the dataset separated by underscores (ex: AMA_2_36);
     */
//    private String observationId;
    /**
     * The feature of interest is an abstraction of a real-world object. It's the spatial object sample by the
     * Observation
     */
    private FeatureOfInterest featureOfInterest;
    /**
     * The observed property is a characteristic of the feature of interest. It identifies or describes the phenomenon
     * for which the observation is made.
     */
    private ObservedPropertyLitePopup observedProperty;
    /**
     * The temporalextent of the result. For result that are not time series, the date acquisition of the observation is
     * precised. For results that are not temporal (e.g. geological map the date of acquisition of the observation is
     * precised followed by a 31-12-9999 00:00:00
     */
//    private TemporalExtent temporalExtent;

//    public String getObservationId() {
//        return observationId;
//    }

    public FeatureOfInterest getFeatureOfInterest() {
        return featureOfInterest;
    }

    public void setFeatureOfInterest(FeatureOfInterest featureOfInterest) {
        this.featureOfInterest = featureOfInterest;
    }
    
    

//    public void setObservedProperty(ObservedPropertyLiteMap observedProperty) {
//        this.observedProperty = observedProperty;
//    }
//
//    public ObservedPropertyLiteMap getObservedProperty() {
//        return observedProperty;
//    }

//    public TemporalExtent getTemporalExtent() {
//        return temporalExtent;
//    }

    public ObservedPropertyLitePopup getObservedProperty() {
        return observedProperty;
    }

    public void setObservedProperty(ObservedPropertyLitePopup observedProperty) {
        this.observedProperty = observedProperty;
    }
}
