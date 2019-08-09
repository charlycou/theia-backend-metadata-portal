/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation;

import java.util.List;

/**
 * Class representing the numerical result of an observation. 
 * The result of an observation is an estimate value of a property of the feature of interest
 * @author coussotc
 */
public class NumericalResult extends Result {
    
    /**
     * character String of missing data value in the output file
     */
    private String missingValue;
    
    /**
     * List of QualityFlag object describing the quality flag of the observation
     */
    private List<QualityFlag> qualityFlags;
    
    /**
     * DataFile object representing the file containing the data of the observation
     */
    private DataFile dataFile;
    
    
    /**
     * List of additional value that can be set for each time step. For exemple, incertitude calculation, error, 
     * sensor parameter...
     */
    private List<AdditionalValue> additionalValues;

    public String getMissingValue() {
        return missingValue;
    }

    public void setMissingValue(String missingValue) {
        this.missingValue = missingValue;
    }

    public List<QualityFlag> getQualityFlags() {
        return qualityFlags;
    }

    public void setQualityFlags(List<QualityFlag> qualityFlags) {
        this.qualityFlags = qualityFlags;
    }

    public DataFile getDataFile() {
        return dataFile;
    }

    public void setDataFile(DataFile dataFile) {
        this.dataFile = dataFile;
    }

    public List<AdditionalValue> getAdditionalValues() {
        return additionalValues;
    }

    public void setAdditionalValues(List<AdditionalValue> additionalValues) {
        this.additionalValues = additionalValues;
    }
    
    
}
