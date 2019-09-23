/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.facet;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.TheiaVariable;
import java.util.List;

/**
 *
 * @author coussotc
 */
public class FacetClassification {

    private List<TheiaVariable> theiaVariables;
    private List<TheiaCategoryTree> theiaCategoryTree;
    private List<FundingsCount> fundingNamesFacet;
    //private List<FundingsCount> fundingAcronymsFacet;
    private List<ElementaryCount> climatesFacet;
    private List<ElementaryCount> geologiesFacet;
    private List<ElementaryCount> producerNamesFacet;
    private List<ElementaryCount> totalCount;

    public FacetClassification(List<TheiaVariable> theiaVariables, List<TheiaCategoryTree> theiaCategoryTree, List<FundingsCount> fundingNamesFacet, List<ElementaryCount> climatesFacet, List<ElementaryCount> geologiesFacet, List<ElementaryCount> producerNamesFacet, List<ElementaryCount> totalCount) {
        this.theiaVariables = theiaVariables;
        this.theiaCategoryTree = theiaCategoryTree;
        this.fundingNamesFacet = fundingNamesFacet;
        //this.fundingAcronymsFacet = fundingAcronymsFacet;
        this.climatesFacet = climatesFacet;
        this.geologiesFacet = geologiesFacet;
        this.producerNamesFacet = producerNamesFacet;
        this.totalCount = totalCount;
    }

    public List<TheiaVariable> getTheiaVariables() {
        return theiaVariables;
    }

    public void setTheiaVariables(List<TheiaVariable> theiaVariables) {
        this.theiaVariables = theiaVariables;
    }

    public List<TheiaCategoryTree> getTheiaCategoryTree() {
        return theiaCategoryTree;
    }

    public void setTheiaCategoryTree(List<TheiaCategoryTree> theiaCategoryTree) {
        this.theiaCategoryTree = theiaCategoryTree;
    }


    
    public List<FundingsCount> getFundingNamesFacet() {
        return fundingNamesFacet;
    }

    public void setFundingNamesFacet(List<FundingsCount> fundingNamesFacet) {
        this.fundingNamesFacet = fundingNamesFacet;
    }

//    public List<FundingsCount> getFundingAcronymsFacet() {
//        return fundingAcronymsFacet;
//    }
//
//    public void setFundingAcronymsFacet(List<FundingsCount> fundingAcronymsFacet) {
//        this.fundingAcronymsFacet = fundingAcronymsFacet;
//    }

    public List<ElementaryCount> getClimatesFacet() {
        return climatesFacet;
    }

    public void setClimatesFacet(List<ElementaryCount> climatesFacet) {
        this.climatesFacet = climatesFacet;
    }

    public List<ElementaryCount> getGeologiesFacet() {
        return geologiesFacet;
    }

    public void setGeologiesFacet(List<ElementaryCount> geologiesFacet) {
        this.geologiesFacet = geologiesFacet;
    }

    public List<ElementaryCount> getProducerNamesFacet() {
        return producerNamesFacet;
    }

    public void setProducerNamesFacet(List<ElementaryCount> producerNamesFacet) {
        this.producerNamesFacet = producerNamesFacet;
    }

    public List<ElementaryCount> getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(List<ElementaryCount> totalCount) {
        this.totalCount = totalCount;
    }
    
    
}
