/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.enumerations;

/**
 * Enumeration of the possible format of the result of an observation in the system
 * @author coussotc
 */
public enum EnumResultDataTypes {
    NUMERIC("Numeric"),
    TEXT("Text"),
    RASTER("Raster"),
    VECTOR("Vector"),
    PHOTO("Photo"),
    VIDEO("Video"),
    AUDIO("Audio"),
    OTHER("Other");
    
    private String enumResultDataTypes;

    private EnumResultDataTypes(String enumResultDataTypes) {
        this.enumResultDataTypes = enumResultDataTypes;
    }
    
    /**
     * String returning the processing levels
     * @return String 
     */
    @Override
    public String toString() {
        return this.enumResultDataTypes;
    }
    
    
}
