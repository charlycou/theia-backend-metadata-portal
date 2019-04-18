/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.lite;

import fr.theia_land.in_situ.dataportal.mdl.POJO.FeatureOfInterest;
import fr.theia_land.in_situ.dataportal.mdl.POJO.ObservedProperty;
import fr.theia_land.in_situ.dataportal.mdl.POJO.TemporalExtent;
import java.util.List;

/**
 *
 * @author coussotc
 */
public class ObservationLite {

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
    private ObservedProperty observedProperty;

//    /**
//     * List of the temporalextent of the resulting the aggregation by observed property name at a given location. For result that are not time series, the date acquisition of the observation is
//     * precised. For results that are not temporal (e.g. geological map the date of acquisition of the observation is
//     * precised followed by a 31-12-9999 00:00:00
//     */
//    private List<TemporalExtent> temporalExtent;
//
//    public List<TemporalExtent> getTemporalExtent() {
//        return temporalExtent;
//    }
//
//    public void setTemporalExtent(List<TemporalExtent> temporalExtent) {
//        this.temporalExtent = temporalExtent;
//    }

    public FeatureOfInterest getFeatureOfInterest() {
        return featureOfInterest;
    }

    public ObservedProperty getObservedProperty() {
        return observedProperty;
    }

}
