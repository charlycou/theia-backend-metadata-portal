/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation;

import java.util.List;

/**
 *
 * @author coussotc
 */
public class PhysicalSensor extends Sensor {
    /**
     * model of the sensor
     */
    private String model;
    /**
     * manufacturer of the sensor
     */
    private String manufacturer;
    /**
     * serial number of the sensor
     */
    private String serialNumber;
    /**
     * type of the sensor
     */
    private List<I18n> sensorType;
    /**
     * calibration description of the sensor
     */
    private List<I18n> calibration;
    
    private Document document;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public List<I18n> getSensorType() {
        return sensorType;
    }

    public void setSensorType(List<I18n> sensorType) {
        this.sensorType = sensorType;
    }

    public List<I18n> getCalibration() {
        return calibration;
    }

    public void setCalibration(List<I18n> calibration) {
        this.calibration = calibration;
    }
    
    

}
