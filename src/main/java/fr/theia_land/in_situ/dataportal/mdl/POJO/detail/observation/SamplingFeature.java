/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation;

import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.Properties;
import java.util.List;





/**
 * Abstract class representing the different form of feature that can be sampled by an observation
 * @author coussotc
 */
public abstract class SamplingFeature {
    /**
    * The name of the feature. i.e. the name of the station.
    */
    private List<I18n> name;
    private String type;
    private Properties properties;

//    public SamplingFeature(I18n name, String type, Properties properties) {
//        this.name = name;
//        this.type = type;
//        this.properties = properties;
//    }
//    

    public List<I18n> getName() {
        return name;
    }

    public void setName(List<I18n> name) {
        this.name = name;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
   
    
    
}
