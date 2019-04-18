/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.enumerations;

/**
 * The processing status characterizes	the general processing steps that the data have been subjected to. Depending on
 * the parameters being controlled, several categories of data are defined:
 *
 * @author coussotc
 */
public enum EnumProcessingLevels {
    RAW("Raw data"),
    QUALITY_CONTROLLED("Quality-controlled data"),
    DERIVED("Derived products");

    private String enumProcessingLevels;

    /**
     * EnumProcessingLevels constructors
     * @param enumProcessingLevels String
     */
    private EnumProcessingLevels(String enumProcessingLevels) {
        this.enumProcessingLevels = enumProcessingLevels;
    }
    /**
     * String returning the processing levels
     * @return String 
     */
    @Override
    public String toString() {
        return this.enumProcessingLevels;
    }

}
