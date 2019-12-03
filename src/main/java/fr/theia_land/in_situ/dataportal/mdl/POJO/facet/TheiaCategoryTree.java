/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.facet;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.I18n;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.TheiaVariable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author coussotc
 */
public class TheiaCategoryTree {

    private String uri;
    private List<I18n> prefLabel;
    private int count;
    private List<TheiaCategoryTree> narrowers;
    private List<TheiaCategoryTree> broaders;
    private List<TheiaVariable> theiaVariables;

    private TheiaCategoryTree() {
    }

    public static TheiaCategoryTree withNarrowers(String uri, List<I18n> prefLabel, Set<TheiaCategoryTree> narrowers, int count) {
        TheiaCategoryTree hierarchy = new TheiaCategoryTree();
        hierarchy.setUri(uri);
        hierarchy.setPrefLabel(prefLabel);
        hierarchy.setCount(count);
        hierarchy.setNarrowers(narrowers.stream().sorted((object1, object2) -> object1.getUri().compareTo(object2.getUri())).collect(Collectors.toList()));
        return hierarchy;
    }

    public static TheiaCategoryTree withBroaders(String uri, List<I18n> prefLabel, Set<TheiaCategoryTree> broaders, int count) {
        TheiaCategoryTree hierarchy = new TheiaCategoryTree();
        hierarchy.setUri(uri);;
        hierarchy.setPrefLabel(prefLabel);
        hierarchy.setCount(count);
        hierarchy.setBroaders(broaders.stream().sorted((object1, object2) -> object1.getUri().compareTo(object2.getUri())).collect(Collectors.toList()));
        return hierarchy;
    }
    
//        public static TheiaCategoryTree withBroadersAndNarrowers(String uri, List<I18n> prefLabel, Set<TheiaCategoryTree> narrowers, Set<TheiaCategoryTree> broaders, int count) {
//        TheiaCategoryTree hierarchy = new TheiaCategoryTree();
//        hierarchy.setUri(uri);;
//        hierarchy.setPrefLabel(prefLabel);
//        hierarchy.setCount(count);
//        hierarchy.setNarrowers(narrowers.stream().sorted((object1, object2) -> object1.getUri().compareTo(object2.getUri())).collect(Collectors.toList()));
//        hierarchy.setBroaders(broaders.stream().sorted((object1, object2) -> object1.getUri().compareTo(object2.getUri())).collect(Collectors.toList()));
//        return hierarchy;
//    }

    public static TheiaCategoryTree withTheiaVariables(String uri, List<I18n> prefLabel, Set<TheiaVariable> theiaVariables, int count) {
        TheiaCategoryTree hierarchy = new TheiaCategoryTree();
        hierarchy.setUri(uri);;
        hierarchy.setPrefLabel(prefLabel);
        hierarchy.setCount(count);
        hierarchy.setTheiaVariables(theiaVariables.stream().sorted((object1, object2) -> object1.getUri().compareTo(object2.getUri())).collect(Collectors.toList()));
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

    public List<TheiaCategoryTree> getNarrowers() {
        return narrowers;
    }

    public void setNarrowers(List<TheiaCategoryTree> narrowers) {
        this.narrowers = narrowers;
    }

    public List<TheiaCategoryTree> getBroaders() {
        return broaders;
    }

    public void setBroaders(List<TheiaCategoryTree> broaders) {
        this.broaders = broaders;
    }

    public List<TheiaVariable> getTheiaVariables() {
        return theiaVariables;
    }

    public void setTheiaVariables(List<TheiaVariable> theiaVariables) {
        this.theiaVariables = theiaVariables;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
