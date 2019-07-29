/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.controller;

import fr.theia_land.in_situ.dataportal.DAO.CustomObservationDocumentLiteRepositoryImpl;
import fr.theia_land.in_situ.dataportal.DAO.MapItemRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fr.theia_land.in_situ.dataportal.DAO.ObservationDocumentRepository;
import fr.theia_land.in_situ.dataportal.model.ObservationDocument;
import fr.theia_land.in_situ.dataportal.model.ObservationDocumentLite;
import fr.theia_land.in_situ.dataportal.model.ResponseDocument;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import fr.theia_land.in_situ.dataportal.DAO.ObservationDocumentLiteRepository;
import fr.theia_land.in_situ.dataportal.mdl.POJO.TheiaVariable;
import fr.theia_land.in_situ.dataportal.mdl.POJO.facet.FacetClassification;
import fr.theia_land.in_situ.dataportal.model.MapItem;
import fr.theia_land.in_situ.dataportal.model.PopupContent;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author coussotc
 */
@RestController
@RequestMapping("/observation")
@CrossOrigin(origins = {"http://localhost","http://in-situ.theia-land.fr"})
public class ObservationsController {
    
    /**
     * Inject the repository to be queried
     */
    @Autowired
    private ObservationDocumentLiteRepository observationDocumentLiteRepository;
    @Autowired
    private ObservationDocumentRepository observationDocumentRepository;
    @Autowired
    private MapItemRepository mapItemRepository;
   

    /**
     * method used to show detailed information about observations
     * @param payload String that can be parsed into json array containing the id of the observations to be printed
     * @return observationDocuments object
     */
    @PostMapping("/showObservationsDetailed")
    public List<ObservationDocument> findByDocumentId(@RequestBody String payload) {
        List<ObservationDocument> observationDocuments = new ArrayList<>();
        JSONArray jsonDocumentIds = new JSONArray(payload);
        jsonDocumentIds.forEach(item -> {
            observationDocuments.add(this.observationDocumentRepository.findByDocumentId((String) item));
        });
        return observationDocuments;
    }
    
    @PostMapping("/getObservationIdsOfOtherTheiaVariableAtLocation")
    public List<String> getObservationIdsOfOtherTheiaVariableAtLocation(@RequestBody String payload) {
        return this.observationDocumentLiteRepository.getObservationIdsOfOtherTheiaVariableAtLocation(payload);
    }
    
    
    @PostMapping("/getVariablesAtOneLocation")
    public List<TheiaVariable> getVariablesAtOneLocation(@RequestBody  String payload) {
        return this.observationDocumentLiteRepository.getVariablesAtOneLocation(payload);
    }
    
    @PostMapping("/getObservationsOfADataset")
    public List<Document> getObservationsOfADataset(@RequestBody  String payload) {
        return this.observationDocumentLiteRepository.getObservationsOfADataset(payload);
    }
    
    @GetMapping("/getMapItemsOfADataset/{datasetId}")
    public List<MapItem> getMapItemsOfADataset(@PathVariable String datasetId) {
        return this.mapItemRepository.findByDatasetId(datasetId);
    }
    
    /**
     * Method used to query the database using defined filters
     * @param payload String that can be parsed into json object containg the filters used to query the database
     * @return ResponseDocument Object
     */
    @PostMapping("/searchObservations")
    public ResponseDocument searchObservations(@RequestBody String payload) {
        ResponseDocument response = this.observationDocumentLiteRepository.searchObservations(payload);
        return response;
    }

    /**
     * Method used to initialise the facet using the entier database
     * @return FacetClassificationTmp object
     */
    @PostMapping("/initFacets")
    public FacetClassification initFacets() {
        return this.observationDocumentLiteRepository.initFacets();
    }
    
    /**
     * Method used to change the page of hte paginated ObservationDocumentLite resulting from a query
     * @param payload String that can be parsed into json object containg the filters used to query the database, 
     * the pageSelected and the number observation per page
     * @return Page of ObservationDocumentLite object
     */
    @PostMapping("/changeObservationsPage")
    public Page<ObservationDocumentLite> getObservationsPage(@RequestBody String payload) {
        JSONObject jsonPayload = new JSONObject(payload);
        return this.observationDocumentLiteRepository.getObservationsPage(CustomObservationDocumentLiteRepositoryImpl
                .setMatchOperationUsingFilters(jsonPayload.getJSONObject("filters").toString()),
                PageRequest.of(jsonPayload.getInt("pageSelected")-1,
                         jsonPayload.getInt("pageSize")));
    }
    
    /**
     * Method use to load the content of a popup to be printed on ui
     * @param payload String to be parsed into json Object containing the ids of the observation from which variable names 
     * need to be printed on the map.
     * @return PopupContent object
     */
   @PostMapping("/loadPopupContent")
   public PopupContent loadPopupContent(@RequestBody String payload) {
       return this.observationDocumentLiteRepository.loadPopupContent(payload);
   }


}
