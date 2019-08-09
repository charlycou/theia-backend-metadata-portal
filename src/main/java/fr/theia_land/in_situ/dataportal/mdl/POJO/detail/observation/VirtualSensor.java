/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation;

import java.util.List;





/**
 * Class describing a virtual Sensor. This sensor can be a model used to produce simulated data
 * @author coussotc
 */
public class VirtualSensor extends Sensor {
    /**
     * The name of the model
     */
    private List<I18n> name;
    /**
     * The descirption of the paramter and different forcing for the observation
     */
    private List<I18n> parametrisationDescription ;
    /**
     * related publications and notices
     */
    private Document document;

    public List<I18n> getName() {
        return name;
    }

    public void setName(List<I18n> name) {
        this.name = name;
    }

    public List<I18n> getParametrisationDescription() {
        return parametrisationDescription;
    }

    public void setParametrisationDescription(List<I18n> parametrisationDescription) {
        this.parametrisationDescription = parametrisationDescription;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
    
    

}

