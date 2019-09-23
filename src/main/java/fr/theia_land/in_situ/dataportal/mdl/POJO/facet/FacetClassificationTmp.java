/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.facet;

import java.util.List;

/**
 * Result object of the Facet Operation of the database. The class is a temporary class "tmp" because "theiaCategorieFacetElements"
 * attribute is not optimised to be returned to the frontend client. This attribute needs to be post-processed by the backend
 * before to be returned to the front end client.
 * @author coussotc
 */
public class FacetClassificationTmp {

    private List<TheiaCategoryFacetElement> theiaCategorieFacetElements;
    private List<FundingsCount> fundingNamesFacet;
    //private List<FundingsCount> fundingAcronymsFacet;
    private List<ElementaryCount> climatesFacet;
    private List<ElementaryCount> geologiesFacet;
    private List<ElementaryCount> producerNamesFacet;
    private List<ElementaryCount> totalCount;

    public List<TheiaCategoryFacetElement> getTheiaCategorieFacetElements() {
        return theiaCategorieFacetElements;
    }

    public void setTheiaCategorieFacetElements(List<TheiaCategoryFacetElement> theiaCategorieFacetElements) {
        this.theiaCategorieFacetElements = theiaCategorieFacetElements;
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
