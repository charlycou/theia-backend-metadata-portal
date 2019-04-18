/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.DAO;

import fr.theia_land.in_situ.dataportal.model.ObservationDocumentLite;
import fr.theia_land.in_situ.dataportal.mdl.POJO.facet.FacetClassification;
import fr.theia_land.in_situ.dataportal.model.PopupContent;
import fr.theia_land.in_situ.dataportal.model.ResponseDocument;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

/**
 * Interface for custom method definition
 */
public interface CustomObservationDocumentLiteRepository {

    public ResponseDocument searchObservations(String query);

    public Page<ObservationDocumentLite> getObservationsPage(String queryElements, Pageable pageable);

    public FacetClassification initFacets();

    public PopupContent loadPopupContent(String ids);
}
