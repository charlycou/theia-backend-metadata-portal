/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation;

import java.util.List;

/**
 * The acquisition procedure
 *
 * @author coussotc
 */
public class DataProduction {

    /**
     * Descritpion of the method used for the acquisition of the data
     */
    private List<I18n> method;

    /**
     * Descritpion of the sensor used for the acquisition of the data
     */
    private List<Sensor> sensors;

    public List<I18n> getMethod() {
        return method;
    }

    public void setMethod(List<I18n> method) {
        this.method = method;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    
}
