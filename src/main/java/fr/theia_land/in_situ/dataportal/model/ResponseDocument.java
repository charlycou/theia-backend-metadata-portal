/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.model;

import fr.theia_land.in_situ.dataportal.mdl.POJO.facet.FacetClassification;
import fr.theia_land.in_situ.dataportal.mdl.POJO.facet.FacetClassificationTmp;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 *
 * @author coussotc
 */
public class ResponseDocument {
//    private PagedListHolder<ObservationDocumentLite> observationDocumentLitePage;
    private Page<ObservationDocumentLite> observationDocumentLitePage;
    private FacetClassification facetClassification;
    private List<MapItem> mapItems;

    public Page<ObservationDocumentLite> getObservationDocumentLitePage() {
        return observationDocumentLitePage;
    }

    public void setObservationDocumentLitePage(Page<ObservationDocumentLite> observationDocumentLitePage) {
        this.observationDocumentLitePage = observationDocumentLitePage;
    }

    public List<MapItem> getMapItems() {
        return mapItems;
    }

    public void setMapItems(List<MapItem> mapItems) {
        this.mapItems = mapItems;
    }

    public FacetClassification getFacetClassification() {
        return facetClassification;
    }

    public void setFacetClassification(FacetClassification facetClassification) {
        this.facetClassification = facetClassification;
    }

    

}
