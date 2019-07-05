/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.facet;

import fr.theia_land.in_situ.dataportal.mdl.POJO.I18n;
import fr.theia_land.in_situ.dataportal.mdl.POJO.TheiaVariable;
import java.util.List;
import java.util.Set;

/**
 *
 * @author coussotc
 */
public class TheiaCategoryTree {
    private String uri;
    private List<I18n> prefLabel;
    private int count;
    private Set<TheiaCategoryTree> narrowers;
    private Set<TheiaVariable> theiaVariables;

    private TheiaCategoryTree() {
    }
    
    

    public static TheiaCategoryTree withNarrowers(String uri, List<I18n> prefLabel, Set<TheiaCategoryTree> narrowers, int count) {
        TheiaCategoryTree hierarchy = new TheiaCategoryTree();
        hierarchy.setUri(uri);;
        hierarchy.setPrefLabel(prefLabel);
        hierarchy.setCount(count);
        hierarchy.setNarrowers(narrowers);
        return hierarchy;
    }

    public static TheiaCategoryTree withTheiaVariables(String uri, List<I18n> prefLabel, Set<TheiaVariable> theiaVariables, int count) {
        TheiaCategoryTree hierarchy = new TheiaCategoryTree();
        hierarchy.setUri(uri);;
        hierarchy.setPrefLabel(prefLabel);
        hierarchy.setCount(count);
        hierarchy.setTheiaVariables(theiaVariables);
        return hierarchy;
    }
    
    
    
    

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<I18n> getPrefLabel() {
        return prefLabel;
    }

    public void setPrefLabel(List<I18n> prefLabel) {
        this.prefLabel = prefLabel;
    }

    public Set<TheiaCategoryTree> getNarrowers() {
        return narrowers;
    }

    public void setNarrowers(Set<TheiaCategoryTree> narrowers) {
        this.narrowers = narrowers;
    }

    public Set<TheiaVariable> getTheiaVariables() {
        return theiaVariables;
    }

    public void setTheiaVariables(Set<TheiaVariable> theiaVariables) {
        this.theiaVariables = theiaVariables;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
