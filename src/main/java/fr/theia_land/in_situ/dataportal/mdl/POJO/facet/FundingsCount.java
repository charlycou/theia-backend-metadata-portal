/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.facet;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.I18n;
import java.util.List;

/**
 *
 * @author coussotc
 */
public class FundingsCount {
    private String name, acronym, type, idScanR;
    private List<I18n> country;
    private int count;

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<I18n> getCountry() {
        return country;
    }

    public void setCountry(List<I18n> country) {
        this.country = country;
    }   

    public String getIdScanR() {
        return idScanR;
    }

    public void setIdScanR(String idScanR) {
        this.idScanR = idScanR;
    }
    
}
