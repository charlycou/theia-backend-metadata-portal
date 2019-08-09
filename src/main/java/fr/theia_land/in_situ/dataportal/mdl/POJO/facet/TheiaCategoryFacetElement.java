/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.facet;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.I18n;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.TheiaVariable;
import java.util.List;

/**
 *
 * @author coussotc
 */
public class TheiaCategoryFacetElement {
    private String uri;
    private List<String> broaders;
    private List<String> narrowers;
    private int count;
    private List<I18n> prefLabel;
    private List<TheiaVariable> theiaVariables;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<String> getBroaders() {
        return broaders;
    }

    public void setBroaders(List<String> broaders) {
        this.broaders = broaders;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<I18n> getPrefLabel() {
        return prefLabel;
    }

    public void setPrefLabel(List<I18n> prefLabel) {
        this.prefLabel = prefLabel;
    }

    public List<TheiaVariable> getTheiaVariables() {
        return theiaVariables;
    }

    public void setTheiaVariables(List<TheiaVariable> theiaVariables) {
        this.theiaVariables = theiaVariables;
    }

    public List<String> getNarrowers() {
        return narrowers;
    }

    public void setNarrowers(List<String> narrowers) {
        this.narrowers = narrowers;
    }
    
        
}
