/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.DAO;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.TheiaVariable;
import fr.theia_land.in_situ.dataportal.mdl.POJO.facet.FacetClassification;
import fr.theia_land.in_situ.dataportal.model.ObservationDocumentLite;
import fr.theia_land.in_situ.dataportal.model.PopupContent;
import fr.theia_land.in_situ.dataportal.model.ResponseDocument;
import java.util.List;
import org.bson.Document;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

/**
 * Interface for custom method definition
 */
public interface CustomObservationDocumentLiteRepository {

    public ResponseDocument searchObservations(String queryFilter);

    public Page<ObservationDocumentLite> getObservationsPage(List<AggregationOperation> aggregationOperations, Pageable pageable);

    public FacetClassification initFacets();

    public PopupContent loadPopupContent(List<String> ids);

    public List<TheiaVariable> getVariablesAtOneLocation(String coordinatesString);
    
    public List<Document> getObservationsOfADataset(String coordinatesString);
    
    public List<String> getObservationIdsOfOtherTheiaVariableAtLocation(String queryFilter);
    
    public  List<AggregationOperation> setMatchOperationUsingFilters(String queryElements, String groupOperationTarget);
    
    public List<String> getDatasetOrProducerIds(List<AggregationOperation> aggregationOperations);
    
}