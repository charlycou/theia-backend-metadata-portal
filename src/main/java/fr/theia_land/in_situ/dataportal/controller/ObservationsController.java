/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.controller;

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
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset.SpatialExtent;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.I18n;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.Observation;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.TheiaVariable;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.producer.Producer;
import fr.theia_land.in_situ.dataportal.mdl.POJO.facet.FacetClassification;
import fr.theia_land.in_situ.dataportal.mdl.POJO.facet.TheiaCategoryTree;
import fr.theia_land.in_situ.dataportal.model.MapItem;
import fr.theia_land.in_situ.dataportal.model.PopupContent;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
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
@CrossOrigin(origins = "*")
public class ObservationsController {

    /**
     * Inject the repository to be queried
     */
    @Autowired
    private ObservationDocumentLiteRepository observationDocumentLiteRepository;
    @Autowired
    private ObservationDocumentRepository observationDocumentRepository;

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
        List<ObservationDocument> observationDocuments = new ArrayList<>();
        JSONArray jsonDocumentIds = new JSONArray(payload);
        jsonDocumentIds.forEach(item -> {
            observationDocuments.add(this.observationDocumentRepository.findByDocumentId((String) item));
        });
        return observationDocuments;
    }

    /**
     * Finds observationId of a TheiaVariable at a given location. The documents are queried from 'ObservationsLite'
     * collection using the uri of the theia vairable and the coordinantes field of a geoJson object
     *
     * @param payload String -
     * {\"uri\":\"https://w3id.org/ozcar-theia/variables/organicCarbon\",\"coordinates\":[6.239739,47.04832,370]}
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
        return this.observationDocumentLiteRepository.getObservationIdsOfOtherTheiaVariableAtLocation(payload);
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
        return this.observationDocumentLiteRepository.getVariablesAtOneLocation(payload);
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
        return this.observationDocumentLiteRepository.getObservationsOfADataset(datasetId);
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
        return this.observationDocumentRepository.findDatasetSpatialExtent(datasetId);
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
        List<TheiaCategoryTree> theiaCategoryTrees = this.observationDocumentRepository.getCategoryHierarchies(payload);
        List<List<List<I18n>>> categoriesHierarchies = new ArrayList();
        for (TheiaCategoryTree tct : theiaCategoryTrees) {
            categoriesHierarchies.addAll(setCategoryBranches(tct));
        }
        return categoriesHierarchies;
    }

    private List<List<List<I18n>>> setCategoryBranches(TheiaCategoryTree tct) {
        List<List<List<I18n>>> resultList = new ArrayList();
        if (tct.getBroaders() != null && tct.getBroaders().size() > 0) {
            for (TheiaCategoryTree broader : tct.getBroaders()) {
                resultList.addAll(setCategoryBranches(broader));
            }
        }
        if (resultList.size() > 0) {
            List<List<List<I18n>>> tmpResultList = new ArrayList<>();
            for (List<List<I18n>> list : resultList) {
                list.add(tct.getPrefLabel());
                tmpResultList.add(list);
            }
            return tmpResultList;
        } else {
            List<List<I18n>> tmp = new ArrayList();
            tmp.add(tct.getPrefLabel());
            resultList.add(tmp);
            return resultList;
        }
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
        ResponseDocument response = this.observationDocumentLiteRepository.searchObservations(payload);
        return response;
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
        return this.observationDocumentLiteRepository.initFacets();
    }

    /**
     * Method used to change the page of the paginated ObservationDocumentLite resulting from a query
     *
     * @param payload String that can be parsed into json object containg the filters used to query the database, the
     * pageSelected and the number observation per page
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
        JSONObject jsonPayload = new JSONObject(payload);
        return this.observationDocumentLiteRepository.getObservationsPage(
                this.observationDocumentLiteRepository.setMatchOperationUsingFilters(jsonPayload.getJSONObject("filters").toString(), null),
                PageRequest.of(jsonPayload.getInt("pageSelected") - 1,
                        jsonPayload.getInt("pageSize")));
    }

    /**
     * Method use to load the content of a popup to be printed on ui
     *
     * @param payload String to be parsed into json Object containing the ids of the observation from which variable
     * names need to be printed on the map.
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
        return this.observationDocumentLiteRepository.loadPopupContent(payload);
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
        return this.observationDocumentRepository.getProducersInfo();
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
        return this.observationDocumentRepository.getProducerDetailed(producerId);

    }
    
        @ApiOperation(value = "Load detailed information about one producer",
            notes = "Document are queried from the 'observations' collection",
            response = Producer.class
    )
    @GetMapping("showDatasetDetailed/{producerId}")
    public ObservationDocument showDatasetDetailed(
            @ApiParam(required = true,
                    value = "Example (quotes inside brackets can be badly escaped by UI...):\n KARS",
                    example = "KARS")
            @PathVariable String producerId) {
        return this.observationDocumentRepository.getDatasetDetailed(producerId);

    }


    @PostMapping("changeProducerPage")
    public Page<Producer> getProducerPage(@ApiParam(required = true,
            value = "Example (quotes inside brackets can be badly escaped by UI...):\n {\"filters\":{\"temporalExtents\":[],\"spatialExtent\":null,\"climates\":[],\"geologies\":[\"Quartenary soils\"],\"producerNames\":[],\"fundingNames\":[],\"fundingAcronyms\":[],\"fullText\":null,\"theiaCategories\":[],\"theiaVariables\":[]},\"pageSize\":10,\"pageSelected\":2}",
            examples = @Example(value = {
        @ExampleProperty(value = "{\"filters\":{\"temporalExtents\":[],\"spatialExtent\":null,\"climates\":[],\"geologies\":[\"Quartenary soils\"],\"producerNames\":[],\"fundingNames\":[],\"fundingAcronyms\":[],\"fullText\":null,\"theiaCategories\":[],\"theiaVariables\":[]},\"pageSize\":10,\"pageSelected\":2}")
    }))
            @RequestBody String payload) {
        JSONObject jsonPayload = new JSONObject(payload);
        List<String> producerIds = this.observationDocumentLiteRepository.getDatasetOrProducerIds(
                this.observationDocumentLiteRepository.setMatchOperationUsingFilters(jsonPayload.getJSONObject("filters").toString(), "producer"));
        return this.observationDocumentRepository.getProducersPage(producerIds, PageRequest.of(jsonPayload.getInt("pageSelected") - 1,
                jsonPayload.getInt("pageSize")));

    }

    @PostMapping("changeDatasetPage")
    public Page<Document> getDatastetPage(@ApiParam(required = true,
            value = "Example (quotes inside brackets can be badly escaped by UI...):\n {\"filters\":{\"temporalExtents\":[],\"spatialExtent\":null,\"climates\":[],\"geologies\":[\"Quartenary soils\"],\"producerNames\":[],\"fundingNames\":[],\"fundingAcronyms\":[],\"fullText\":null,\"theiaCategories\":[],\"theiaVariables\":[]},\"pageSize\":10,\"pageSelected\":2}",
            examples = @Example(value = {
        @ExampleProperty(value = "{\"filters\":{\"temporalExtents\":[],\"spatialExtent\":null,\"climates\":[],\"geologies\":[\"Quartenary soils\"],\"producerNames\":[],\"fundingNames\":[],\"fundingAcronyms\":[],\"fullText\":null,\"theiaCategories\":[],\"theiaVariables\":[]},\"pageSize\":10,\"pageSelected\":2}")
    }))
            @RequestBody String payload) {
        JSONObject jsonPayload = new JSONObject(payload);
        List<String> datasetIds = this.observationDocumentLiteRepository.getDatasetOrProducerIds(
                this.observationDocumentLiteRepository.setMatchOperationUsingFilters(jsonPayload.getJSONObject("filters").toString(), "dataset"));
        return this.observationDocumentRepository.getDatasetsPage(datasetIds, PageRequest.of(jsonPayload.getInt("pageSelected") - 1,
                jsonPayload.getInt("pageSize")));
    }

}
