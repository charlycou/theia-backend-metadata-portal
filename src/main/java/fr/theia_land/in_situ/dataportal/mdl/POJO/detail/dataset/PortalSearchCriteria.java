/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset;

import fr.theia_land.in_situ.dataportal.mdl.POJO.enumerations.EnumClimates;
import fr.theia_land.in_situ.dataportal.mdl.POJO.enumerations.EnumGeologies;
import java.util.List;

/**
 *  Class representing Portal search critera parameter to be used for the portal for dataset discovery
 * @author coussotc
 */
public class PortalSearchCriteria {
    /**
     * Ordered list of climate according to EnumClimates values
     * @see EnumClimates
     */
    private List<String> climates;
    /**
     * Ordered list of geologies according to EnumGeologies values
     * @see EnumGeologies
     */
    private List<String> geologies;

    public List<String> getClimates() {
        return climates;
    }

    public void setClimates(List<String> climates) {
        this.climates = climates;
    }

    public List<String> getGeologies() {
        return geologies;
    }

    public void setGeologies(List<String> geologies) {
        this.geologies = geologies;
    }


}
