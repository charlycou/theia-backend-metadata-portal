/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset.TemporalExtent;



/**
 * Class representing an Observation. Each datasets in composed of observations.
 An observation is an action whose the NumericalResult is a value of a Property of the 
 FeatureOfInterest at a given time, obtained following a Procedure.
 * @author coussotc
 */
public class Observation {
    /**
     * Id of the observation. Corresponds to the trigramme of the provider, the number of the dataset, and the number of the observation 
     * of the dataset separated by underscores (ex: AMA_2_36);
     */
    private String observationId;
    /**
     * The feature of interest is an abstraction of a real-world object. It's the spatial object
     * sample by the Observation
     */
    private FeatureOfInterest featureOfInterest;
    /**
     * The observed property is a characteristic of the feature of interest. It identifies or
     * describes the phenomenon for which the observation is made.
     */
    private ObservedProperty observedProperty;
    /**
     * The procedure, or observation process, is the description of the processus used 
     * to produce the result from an observed property of the feature of interest
     */
    private Procedure procedure;
    /**
     * The result of an observation is an estimate value of a property of the feature of interest
     */
    private NumericalResult result;
    /**
     * boolean in order to know if the result is a time serie or not
     */
    private boolean timeSerie;
    /**
     * The temporalextent of the result. For result that are not time series, the date acquisition of the 
     * observation is precised. For results that are not temporal (e.g. geological map the date of acquisition of
     * the observation is precised followed by a 31-12-9999 00:00:00
     */
    private TemporalExtent temporalExtent;
    /**
     * The type of the result according to EnumResultDataTypes enumeration
     */
    private String dataType;
    /**
     * The level of processing for the data of the observation
     */
    private String processingLevel;

    public String getObservationId() {
        return observationId;
    }

    public void setObservationId(String observationId) {
        this.observationId = observationId;
    }

    public FeatureOfInterest getFeatureOfInterest() {
        return featureOfInterest;
    }

    public void setFeatureOfInterest(FeatureOfInterest featureOfInterest) {
        this.featureOfInterest = featureOfInterest;
    }

    public ObservedProperty getObservedProperty() {
        return observedProperty;
    }

    public void setObservedProperty(ObservedProperty observedProperty) {
        this.observedProperty = observedProperty;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    public NumericalResult getResult() {
        return result;
    }

    public void setResult(NumericalResult result) {
        this.result = result;
    }

    public boolean isTimeSerie() {
        return timeSerie;
    }

    public void setTimeSerie(boolean timeSerie) {
        this.timeSerie = timeSerie;
    }

    public TemporalExtent getTemporalExtent() {
        return temporalExtent;
    }

    public void setTemporalExtent(TemporalExtent temporalExtent) {
        this.temporalExtent = temporalExtent;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getProcessingLevel() {
        return processingLevel;
    }

    public void setProcessingLevel(String processingLevel) {
        this.processingLevel = processingLevel;
    }
    
    
}

