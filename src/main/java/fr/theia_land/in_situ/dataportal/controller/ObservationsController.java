/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.controller;

import fr.theia_land.in_situ.dataportal.model.POJO.ResponseDocument;
import fr.theia_land.in_situ.dataportal.model.POJO.detail.dataset.SpatialExtent;
import fr.theia_land.in_situ.dataportal.model.POJO.detail.observation.I18n;
import fr.theia_land.in_situ.dataportal.model.POJO.detail.observation.TheiaVariable;
import fr.theia_land.in_situ.dataportal.model.POJO.detail.producer.Producer;
import fr.theia_land.in_situ.dataportal.model.POJO.facet.FacetClassification;
import fr.theia_land.in_situ.dataportal.model.POJO.popup.litePopup.PopupContent;
import fr.theia_land.in_situ.dataportal.model.entity.MapItem;
import fr.theia_land.in_situ.dataportal.model.entity.ObservationDocument;
import fr.theia_land.in_situ.dataportal.model.entity.ObservationDocumentLite;
import fr.theia_land.in_situ.dataportal.service.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author coussotc
 */
@RestController
@RequestMapping("/observation")
@CrossOrigin(origins = "*")
public class ObservationsController {

    /**
     * Inject the repository to be queried
     */
    private final PageService pageService;
    private final FacetService facetService;
    private final PopupService popupService;
    private final DetailService detailService;
    private final SearchService searchService;

    @Autowired
    public ObservationsController(PageService pageService, FacetService facetService, PopupService popupService, DetailService detailService, SearchService searchService) {
        this.pageService = pageService;
        this.facetService = facetService;
        this.popupService = popupService;
        this.detailService = detailService;
        this.searchService = searchService;
    }

    /**
     * method used to show detailed information about observations
     *
     * @param payload String that can be parsed into json array containing the id of the observations to be printed
     * @return observationDocuments object
     */
    @ApiOperation(value = "Finds detailed observation documents from the collection 'observations'. ",
            notes = "The documents are queried using a json array of 'documentId' fields. ex: \"[\"CATC_OBS_CE.Veg_Gh_86\",\"CATC_OBS_CE.Veg_Gh_8\"]\"",
            response = ObservationDocument.class,
            responseContainer = "List")
    @PostMapping("/showObservationsDetailed")
    public List<ObservationDocument> findByDocumentId(
            @ApiParam(required = true,
                    value = "Example (quotes inside brackets can be badly escaped by UI...):\n [\"CATC_OBS_CL.Rain_Nig_3\",\"CATC_OBS_CL.Rain_Nig_18\",\"CATC_OBS_CL.Rain_Nig_14\"]",
                    example = "[\"CATC_OBS_CL.Rain_Nig_3\",\"CATC_OBS_CL.Rain_Nig_18\",\"CATC_OBS_CL.Rain_Nig_14\"]",
                    examples = @Example(value = {
                            @ExampleProperty(value = "[\"CATC_OBS_CL.Rain_Nig_3\",\"CATC_OBS_CL.Rain_Nig_18\",\"CATC_OBS_CL.Rain_Nig_14\"]")
                    }))
            @RequestBody String payload) {
        return this.searchService.findByDocumentId(payload);
    }

    /**
     * Finds observationId of a TheiaVariable at a given location. The documents are queried from 'ObservationsLite'
     * collection using the uri of the theia vairable and the coordinantes field of a geoJson object
     *
     * @param payload String -
     *                {\"uri\":\"https://w3id.org/ozcar-theia/variables/organicCarbon\",\"coordinates\":[6.239739,47.04832,370]}
     * @return List of String corresponding to the ids queried
     */
    @ApiOperation(value = "Finds observationId of a TheiaVariable at a given location ",
            notes = "The documents are queried from 'ObservationsLite' collection using the uri of the theia vairable and the coordinantes field of a geoJson object",
            response = ObservationDocument.class,
            responseContainer = "List")
    @PostMapping("/getObservationIdsOfOtherTheiaVariableAtLocation")
    public List<String> getObservationIdsOfOtherTheiaVariableAtLocation(
            @ApiParam(required = true,
                    value = "Example (quotes inside brackets can be badly escaped by UI...):\n {\"uri\":\"https://w3id.org/ozcar-theia/variables/organicCarbon\",\"coordinates\":[6.239739,47.04832,370]}",
                    examples = @Example(value = {
                            @ExampleProperty(value = "{\"uri\":\"https://w3id.org/ozcar-theia/variables/organicCarbon\",\"coordinates\":[6.239739,47.04832,370]}")
                    }))
            @RequestBody String payload) {
        return this.searchService.getObservationIdsOfOtherTheiaVariableAtLocation(payload);
    }

    /**
     * Finds Theia variable available at a given location. The documents are queried using 'coordinates' field of a
     * geojson object fields. ex: [3.795429,43.64558,0]
     *
     * @param payload String value of coordinantes geojsonfields. ex: [3.795429,43.64558,0]
     * @return List of TheiaVariable corresponding to the query.
     */
    @ApiOperation(value = "Finds Theia variable available at a given location ",
            notes = "The documents are queried using 'coordinates' field of a geojson object fields. ex: [3.795429,43.64558,0]",
            response = TheiaVariable.class,
            responseContainer = "List")
    @PostMapping("/getVariablesAtOneLocation")
    public List<TheiaVariable> getVariablesAtOneLocation(
            @ApiParam(required = true,
                    value = "Example (quotes inside brackets can be badly escaped by UI...):\n [3.795429,43.64558,0]",
                    examples = @Example(value = {
                            @ExampleProperty(value = "[3.795429,43.64558,0]")
                    }))
            @RequestBody String payload) {
        return this.searchService.getVariablesAtOneLocation(payload);
    }

    /**
     * Find all mapItems of a given dataset. Documents are queried from the 'observationsLite' collection using the
     * 'datasetId'
     *
     * @param datasetId String - ex: KARS_DAT_MOSSON-1
     * @return List of Document - Each document is an list of ObservationLite object: {"observations":[ObservationLite,
     * ObservationLite, ObservationLite]}
     */
    @ApiOperation(value = "Find all mapItems of a given dataset",
            notes = "Documents are queried from the 'observationsLite' collection using the 'datasetId'",
            response = Document.class,
            responseContainer = "List")
    @GetMapping("/getObservationsOfADataset/{datasetId}")
    public List<Document> getObservationsOfADataset(
            @ApiParam(required = true,
                    value = "Example (quotes inside brackets can be badly escaped by UI...):\n KARS_DAT_MOSSON-1",
                    example = "KARS_DAT_MOSSON-1")
            @PathVariable String datasetId) {
        return this.searchService.getObservationsOfADataset(datasetId);
    }

    /**
     * Find all mapItems of a given dataset. Documents are queried from the 'mapItems' collection using the 'datasetId'
     *
     * @param datasetId String - ex: KARS_DAT_MOSSON-1
     * @return List of MapItems
     */
    @ApiOperation(value = "Find the BBOX of a given dataset",
            notes = "Document are queried from the 'observations' collection using the 'datasetId'",
            response = MapItem.class,
            responseContainer = "List")
    @GetMapping("/getBBOXOfADataset/{datasetId}")
    public SpatialExtent getBBOXOfOfADataset(
            @ApiParam(required = true,
                    value = "Example (quotes inside brackets can be badly escaped by UI...):\n KARS_DAT_MOSSON-1",
                    example = "KARS_DAT_MOSSON-1")
            @PathVariable String datasetId) {
        return this.searchService.getBBOXOfOfADataset(datasetId);
    }

    /**
     * Find the category branches to be printed in info-panel for a list of category
     *
     * @param payload list of uri of the theia categories
     * @return List of List of List of I18n corresponding to the prefLabel of each Theia categories concept
     */
    @ApiOperation(value = "Find the category branches of the list of Theia Category",
            notes = "Document are queried from the 'observations'",
            response = List.class,
            responseContainer = "List")
    @PostMapping("/getCategoryHierarchies")
    public List<List<List<I18n>>> getCategoryHierarchies(
            @ApiParam(required = true,
                    value = "Example (quotes inside brackets can be badly escaped by UI...):\n [\"https://w3id.org/ozcar-theia/surfaceWaterMajorIons\"]",
                    example = "[\"https://w3id.org/ozcar-theia/surfaceWaterMajorIons\"]")
            @RequestBody List<String> payload) {
        return this.facetService.getCategoryHierarchies(payload);
    }


    /**
     * Method used to query the database using defined filters
     *
     * @param payload String that can be parsed into json object containg the filters used to query the database
     * @return ResponseDocument Object
     */
    @ApiOperation(value = "Query documents from 'ObservationsLite' and 'MapItems' collections using the filters defined by he user",
            notes = "The documents are queried using the filters json object. The list of observation and the mapItems document are return"
                    + "and the facet element are recalculated.",
            response = FacetClassification.class)
    @PostMapping("/searchObservations")
    public ResponseDocument searchObservations(@ApiParam(required = true,
            value = "Example (quotes inside brackets can be badly escaped by UI...):\n {\n"
                    + "	\"temporalExtents\": [{\n"
                    + "		\"position\": 1,\n"
                    + "		\"fromDate\": \"1996-07-22T22:00:00.000Z\",\n"
                    + "		\"toDate\": \"2019-07-30T22:00:00.000Z\"\n"
                    + "	}],\n"
                    + "	\"spatialExtent\": {\n"
                    + "		\"type\": \"FeatureCollection\",\n"
                    + "		\"features\": [{\n"
                    + "			\"type\": \"Feature\",\n"
                    + "			\"properties\": {},\n"
                    + "			\"geometry\": {\n"
                    + "				\"type\": \"Polygon\",\n"
                    + "				\"coordinates\": [\n"
                    + "					[\n"
                    + "						[5.87142, 44.944389],\n"
                    + "						[5.87142, 45.73686],\n"
                    + "						[7.25419, 45.73686],\n"
                    + "						[7.25419, 44.944389],\n"
                    + "						[5.87142, 44.944389]\n"
                    + "					]\n"
                    + "				]\n"
                    + "			}\n"
                    + "		}]\n"
                    + "	},\n"
                    + "	\"climates\": [\"Mountain climate\"],\n"
                    + "	\"geologies\": [\"Plutonic rocks\"],\n"
                    + "	\"producerNames\": [\"CRYOBS-CLIM\"],\n"
                    + "	\"fundingNames\": [],\n"
                    + "	\"fundingAcronyms\": [],\n"
                    + "	\"fullText\": null,\n"
                    + "	\"theiaCategories\": [\"https://w3id.org/ozcar-theia/solidPrecipitation\", \"https://w3id.org/ozcar-theia/liquidPrecipitation\", \"https://w3id.org/ozcar-theia/atmosphericRadiation\", \"https://w3id.org/ozcar-theia/atmosphericWaterVapor\", \"https://w3id.org/ozcar-theia/atmosphericChemistry\", \"https://w3id.org/ozcar-theia/atmosphericTemperature\", \"https://w3id.org/ozcar-theia/atmosphericPressure\", \"https://w3id.org/ozcar-theia/wind\", \"https://w3id.org/ozcar-theia/surfaceFluxes\"],\n"
                    + "	\"theiaVariables\": []\n"
                    + "}",
            examples = @Example(value = {
                    @ExampleProperty(value = "{\n"
                            + "	\"temporalExtents\": [{\n"
                            + "		\"position\": 1,\n"
                            + "		\"fromDate\": \"1996-07-22T22:00:00.000Z\",\n"
                            + "		\"toDate\": \"2019-07-30T22:00:00.000Z\"\n"
                            + "	}],\n"
                            + "	\"spatialExtent\": {\n"
                            + "		\"type\": \"FeatureCollection\",\n"
                            + "		\"features\": [{\n"
                            + "			\"type\": \"Feature\",\n"
                            + "			\"properties\": {},\n"
                            + "			\"geometry\": {\n"
                            + "				\"type\": \"Polygon\",\n"
                            + "				\"coordinates\": [\n"
                            + "					[\n"
                            + "						[5.87142, 44.944389],\n"
                            + "						[5.87142, 45.73686],\n"
                            + "						[7.25419, 45.73686],\n"
                            + "						[7.25419, 44.944389],\n"
                            + "						[5.87142, 44.944389]\n"
                            + "					]\n"
                            + "				]\n"
                            + "			}\n"
                            + "		}]\n"
                            + "	},\n"
                            + "	\"climates\": [\"Mountain climate\"],\n"
                            + "	\"geologies\": [\"Plutonic rocks\"],\n"
                            + "	\"producerNames\": [\"CRYOBS-CLIM\"],\n"
                            + "	\"fundingNames\": [],\n"
                            + "	\"fundingAcronyms\": [],\n"
                            + "	\"fullText\": null,\n"
                            + "	\"theiaCategories\": [\"https://w3id.org/ozcar-theia/solidPrecipitation\", \"https://w3id.org/ozcar-theia/liquidPrecipitation\", \"https://w3id.org/ozcar-theia/atmosphericRadiation\", \"https://w3id.org/ozcar-theia/atmosphericWaterVapor\", \"https://w3id.org/ozcar-theia/atmosphericChemistry\", \"https://w3id.org/ozcar-theia/atmosphericTemperature\", \"https://w3id.org/ozcar-theia/atmosphericPressure\", \"https://w3id.org/ozcar-theia/wind\", \"https://w3id.org/ozcar-theia/surfaceFluxes\"],\n"
                            + "	\"theiaVariables\": []\n"
                            + "}")
            }))
                                               @RequestBody String payload) {
        return this.searchService.searchObservations(payload);

    }

    /**
     * Method used to initialise the facet using the entier database
     *
     * @return FacetClassification containing the facet elements calculated
     */
    @ApiOperation(value = "Initialize the facet elements",
            notes = "The facet elements and their count are calculated over the entire 'ObservationsLite' collection",
            response = FacetClassification.class)
    @GetMapping("/initFacets")
    public FacetClassification initFacets() {
        return this.facetService.initFacets();
    }

    /**
     * Method used to change the page of the paginated ObservationDocumentLite resulting from a query
     *
     * @param payload String that can be parsed into json object containg the filters used to query the database, the
     *                pageSelected and the number observation per page
     * @return Page of ObservationDocumentLite object
     */
    @ApiOperation(value = "Find the document to print in tte observation from 'ObservationsLite' collection  ",
            notes = "The documents are queried using the filters json object, the page size and the page number' fields",
            response = ObservationDocumentLite.class,
            responseContainer = "Page")
    @PostMapping("/changeObservationsPage")
    public Page<ObservationDocumentLite> getObservationsPage(@ApiParam(required = true,
            value = "Example (quotes inside brackets can be badly escaped by UI...):\n {\"filters\":{\"temporalExtents\":[],\"spatialExtent\":null,\"climates\":[],\"geologies\":[\"Quartenary soils\"],\"producerNames\":[],\"fundingNames\":[],\"fundingAcronyms\":[],\"fullText\":null,\"theiaCategories\":[],\"theiaVariables\":[]},\"pageSize\":10,\"pageSelected\":2}",
            examples = @Example(value = {
                    @ExampleProperty(value = "{\"filters\":{\"temporalExtents\":[],\"spatialExtent\":null,\"climates\":[],\"geologies\":[\"Quartenary soils\"],\"producerNames\":[],\"fundingNames\":[],\"fundingAcronyms\":[],\"fullText\":null,\"theiaCategories\":[],\"theiaVariables\":[]},\"pageSize\":10,\"pageSelected\":2}")
            }))
                                                             @RequestBody String payload) {
        return this.pageService.getObservationsPage(payload);
    }

    /**
     * Method use to load the content of a popup to be printed on ui
     *
     * @param payload String to be parsed into json Object containing the ids of the observation from which variable
     *                names need to be printed on the map.
     * @return PopupContent object
     */
    @ApiOperation(value = "Load the content of a map popup ",
            notes = "The documents are queried using a json array of 'observationId' fields. ex: \"[\"CATC_OBS_CE.Veg_Gh_86\",\"CATC_OBS_CE.Veg_Gh_8\"]\"",
            response = PopupContent.class)
    @PostMapping("/loadPopupContent")
    public PopupContent loadPopupContent(@ApiParam(required = true,
            value = "Example (quotes inside brackets can be badly escaped by UI...):\n [\"CATC_OBS_CL.Rain_Nig_3\",\"CATC_OBS_CL.Rain_Nig_18\",\"CATC_OBS_CL.Rain_Nig_14\"]",
            example = "[\"CATC_OBS_CL.Rain_Nig_3\",\"CATC_OBS_CL.Rain_Nig_18\",\"CATC_OBS_CL.Rain_Nig_14\"]",
            examples = @Example(value = {
                    @ExampleProperty(value = "[\"CATC_OBS_CL.Rain_Nig_3\",\"CATC_OBS_CL.Rain_Nig_18\",\"CATC_OBS_CL.Rain_Nig_14\"]")
            }))
                                         @RequestBody List<String> payload) {
        return this.popupService.getPopupContent(payload);
    }

    /**
     * Methods used to load information of all the producer in order to have a little description about each producer in
     * the producer facet
     *
     * @return List of Producer object
     */
    @ApiOperation(value = "Load information about each producer",
            notes = "Document are queried from the 'observations' collection",
            response = Producer.class,
            responseContainer = "List"
    )
    @GetMapping("/getProducersInfo")
    public List<Producer> getProducerInfo() {
        return this.facetService.getProducerInfo();
    }

    @ApiOperation(value = "Load detailed information about one producer",
            notes = "Document are queried from the 'observations' collection",
            response = Producer.class
    )
    @GetMapping("showProducerDetailed/{producerId}")
    public Producer showProducerDetailed(
            @ApiParam(required = true,
                    value = "Example (quotes inside brackets can be badly escaped by UI...):\n KARS",
                    example = "KARS")
            @PathVariable String producerId) {
        return this.detailService.getProducerDetailed(producerId);
    }

    @ApiOperation(value = "Load detailed information about one dataset",
            notes = "Document are queried from the 'observations' collection",
            response = Producer.class
    )
    @GetMapping("showDatasetDetailed/{datasetId}")
    public ObservationDocument showDatasetDetailed(
            @ApiParam(required = true,
                    value = "Example (quotes inside brackets can be badly escaped by UI...):\n KARS",
                    example = "KARS")
            @PathVariable String datasetId) {
        return this.detailService.getDatasetDetailed(datasetId);

    }


    @PostMapping("changeProducerPage")
    public Page<Producer> getProducerPage(@ApiParam(required = true,
            value = "Example (quotes inside brackets can be badly escaped by UI...):\n {\"filters\":{\"temporalExtents\":[],\"spatialExtent\":null,\"climates\":[],\"geologies\":[\"Quartenary soils\"],\"producerNames\":[],\"fundingNames\":[],\"fundingAcronyms\":[],\"fullText\":null,\"theiaCategories\":[],\"theiaVariables\":[]},\"pageSize\":10,\"pageSelected\":2}",
            examples = @Example(value = {
                    @ExampleProperty(value = "{\"filters\":{\"temporalExtents\":[],\"spatialExtent\":null,\"climates\":[],\"geologies\":[\"Quartenary soils\"],\"producerNames\":[],\"fundingNames\":[],\"fundingAcronyms\":[],\"fullText\":null,\"theiaCategories\":[],\"theiaVariables\":[]},\"pageSize\":10,\"pageSelected\":2}")
            }))
                                          @RequestBody String payload) {
        return this.pageService.getProducerPage(payload);
    }

    @PostMapping("changeDatasetPage")
    public Page<Document> getDatastetPage(@ApiParam(required = true,
            value = "Example (quotes inside brackets can be badly escaped by UI...):\n {\"filters\":{\"temporalExtents\":[],\"spatialExtent\":null,\"climates\":[],\"geologies\":[\"Quartenary soils\"],\"producerNames\":[],\"fundingNames\":[],\"fundingAcronyms\":[],\"fullText\":null,\"theiaCategories\":[],\"theiaVariables\":[]},\"pageSize\":10,\"pageSelected\":2}",
            examples = @Example(value = {
                    @ExampleProperty(value = "{\"filters\":{\"temporalExtents\":[],\"spatialExtent\":null,\"climates\":[],\"geologies\":[\"Quartenary soils\"],\"producerNames\":[],\"fundingNames\":[],\"fundingAcronyms\":[],\"fullText\":null,\"theiaCategories\":[],\"theiaVariables\":[]},\"pageSize\":10,\"pageSelected\":2}")
            }))
                                          @RequestBody String payload) {
        return this.pageService.getDatasetPage(payload);
    }

}
