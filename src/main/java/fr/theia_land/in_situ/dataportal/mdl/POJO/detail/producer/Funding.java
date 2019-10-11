/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.producer;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.I18n;
import java.util.List;

/**
 * Class representing the organisations affiliated to the the provider
 * @author coussotc
 */
public class Funding {
    /**
     * The organism type according to EnumFundingTypes enumeration
     */
    private String type, idScanR;
    
    /**
     * Acronym of the organism affiliated
     */
    private String acronym;

    /**
     * Complete name of the affiliation
     */
    private List<I18n> name, country;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public List<I18n> getName() {
        return name;
    }

    public void setName(List<I18n> name) {
        this.name = name;
    }

    public String getIdScanR() {
        return idScanR;
    }

    public void setIdScanR(String idScanR) {
        this.idScanR = idScanR;
    }

    public List<I18n> getCountry() {
        return country;
    }

    public void setCountry(List<I18n> country) {
        this.country = country;
    }
    
   
}
