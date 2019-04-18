/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.facet;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author coussotc
 */
public class FacetClassification {

    private List<FundingsCount> fundingNamesFacet = new ArrayList<>();
    private List<FundingsCount> fundingAcronymsFacet = new ArrayList<>();
    private List<ElementaryCount> climatesFacet = new ArrayList<>();
    private List<ElementaryCount> geologiesFacet = new ArrayList<>();
    private List<ElementaryCount> producerNamesFacet = new ArrayList<>();
    private List<ElementaryCount> totalCount = new ArrayList<>();

    public List<FundingsCount> getFundingNamesFacet() {
        return fundingNamesFacet;
    }

    public void setFundingNamesFacet(List<FundingsCount> fundingNamesFacet) {
        this.fundingNamesFacet = fundingNamesFacet;
    }

    public List<FundingsCount> getFundingAcronymsFacet() {
        return fundingAcronymsFacet;
    }

    public void setFundingAcronymsFacet(List<FundingsCount> fundingAcronymsFacet) {
        this.fundingAcronymsFacet = fundingAcronymsFacet;
    }

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
