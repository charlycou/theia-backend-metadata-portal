package fr.theia_land.in_situ.dataportal.controller;

import fr.theia_land.in_situ.dataportal.model.POJO.ResponseDocument;
import fr.theia_land.in_situ.dataportal.model.POJO.detail.dataset.SpatialExtent;
import fr.theia_land.in_situ.dataportal.model.POJO.detail.observation.I18n;
import fr.theia_land.in_situ.dataportal.model.POJO.detail.observation.TheiaVariable;
import fr.theia_land.in_situ.dataportal.model.POJO.detail.producer.Producer;
import fr.theia_land.in_situ.dataportal.model.POJO.facet.FacetClassification;
import fr.theia_land.in_situ.dataportal.model.POJO.popup.litePopup.PopupContent;
import fr.theia_land.in_situ.dataportal.model.entity.ObservationDocument;
import fr.theia_land.in_situ.dataportal.model.entity.ObservationDocumentLite;
import fr.theia_land.in_situ.dataportal.service.*;
import org.bson.Document;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ObservationsController.class)
public class ObservationsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DetailService detailService;

    @MockBean
    FacetService facetService;

    @MockBean
    PageService pageService;

    @MockBean
    PopupService popupService;

    @MockBean
    SearchService searchService;

    @Test
    void findByDocumentId() throws Exception {
        List<ObservationDocument> observationDocuments = new ArrayList<>();
        observationDocuments.add(new ObservationDocument());
        when(searchService.findByDocumentId(anyString()))
                .thenReturn(observationDocuments);
        this.mockMvc.perform(post("/observation/showObservationsDetailed")
                .content("[\"CATC_OBS_CL.Rain_Nig_3\",\"CATC_OBS_CL.Rain_Nig_18\",\"CATC_OBS_CL.Rain_Nig_14\"]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        verify(searchService, times(1)).findByDocumentId(anyString());
    }

    @Test
    void getObservationIdsOfOtherTheiaVariableAtLocation() throws Exception {
        List<String> strings = new ArrayList<>();
        strings.add("test");
        when(searchService.getObservationIdsOfOtherTheiaVariableAtLocation(anyString()))
                .thenReturn(strings);
        this.mockMvc.perform(post("/observation/getObservationIdsOfOtherTheiaVariableAtLocation")
                .content("{\"uri\":\"https://w3id.org/ozcar-theia/variables/organicCarbon\",\"coordinates\":[6.239739,47.04832,370]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        verify(searchService, times(1)).getObservationIdsOfOtherTheiaVariableAtLocation(anyString());
    }

    @Test
    void getVariablesAtOneLocation() throws Exception {
        List<TheiaVariable> theiaVariableList = new ArrayList<>();
        theiaVariableList.add(new TheiaVariable());
        when(searchService.getVariablesAtOneLocation(anyString()))
                .thenReturn(theiaVariableList);
        this.mockMvc.perform(post("/observation/getVariablesAtOneLocation")
                .content("[3.795429,43.64558,0]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        verify(searchService, times(1)).getVariablesAtOneLocation(anyString());
    }

    @Test
    void getObservationsOfADataset() throws Exception {
        List<Document> documents = new ArrayList<>();
        documents.add(new Document());
        when(searchService.getObservationsOfADataset(anyString()))
                .thenReturn(documents);
        this.mockMvc.perform(get("/observation/getObservationsOfADataset/datasetId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        verify(searchService, times(1)).getObservationsOfADataset(anyString());

    }

    @Test
    void getBBOXOfOfADataset() throws Exception {
        SpatialExtent spatialExtent = new SpatialExtent();
        when(searchService.getBBOXOfOfADataset(anyString()))
                .thenReturn(spatialExtent);
        this.mockMvc.perform(get("/observation/getBBOXOfADataset/datasetId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("geometry")))
                .andExpect(jsonPath("$", hasKey("type")))
                .andExpect(jsonPath("$", hasKey("properties")));
        verify(searchService, times(1)).getBBOXOfOfADataset(anyString());
    }

    @Test
    void getCategoryHierarchies() throws Exception {
        JSONArray json = new JSONArray("[\"https://w3id.org/ozcar-theia/surfaceWaterMajorIons\"]");
        List<List<List<I18n>>> list = new ArrayList<>();
        when(facetService.getCategoryHierarchies(anyList()))
                .thenReturn(list);
        this.mockMvc.perform(post("/observation/getCategoryHierarchies")
                .content(json.toString())
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
        verify(facetService, times(1)).getCategoryHierarchies(anyList());

    }

    @Test
    void searchObservations() throws Exception {
        ResponseDocument documents = new ResponseDocument();
        when(searchService.searchObservations(anyString()))
                .thenReturn(documents);
        this.mockMvc.perform(post("/observation/searchObservations")
                .content("{\n"
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
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("observationDocumentLitePage")))
                .andExpect(jsonPath("$", hasKey("facetClassification")))
                .andExpect(jsonPath("$", hasKey("mapItems")));
        verify(searchService, times(1)).searchObservations(anyString());
    }

    @Test
    void initFacets() throws Exception {
        FacetClassification facetClassification = new FacetClassification();
        when(facetService.initFacets())
                .thenReturn(facetClassification);
        this.mockMvc.perform(get("/observation/initFacets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("theiaVariables")))
                .andExpect(jsonPath("$", hasKey("theiaCategoryTree")))
                .andExpect(jsonPath("$", hasKey("climatesFacet")))
                .andExpect(jsonPath("$", hasKey("geologiesFacet")))
                .andExpect(jsonPath("$", hasKey("producerNamesFacet")))
                .andExpect(jsonPath("$", hasKey("totalCount")))
                .andExpect(jsonPath("$", hasKey("fundingNamesFacet")));
        verify(facetService, times(1)).initFacets();
    }

    @Test
    void getObservationsPage() throws Exception {
        Pageable pageable = PageRequest.of(1, 10);
        List<ObservationDocumentLite> observationDocumentLites = new ArrayList<>();
        observationDocumentLites.add(new ObservationDocumentLite());
        Page<ObservationDocumentLite> expected = new PageImpl<>(observationDocumentLites, pageable, observationDocumentLites.size());
        when(pageService.getObservationsPage(anyString())).thenReturn(expected);
        this.mockMvc.perform(post("/observation/changeObservationsPage")
                .content("{\"filters\":{\"temporalExtents\":[],\"spatialExtent\":null,\"climates\":[],\"geologies\":[\"Quartenary soils\"],\"producerNames\":[],\"fundingNames\":[],\"fundingAcronyms\":[],\"fullText\":null,\"theiaCategories\":[],\"theiaVariables\":[]},\"pageSize\":10,\"pageSelected\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("content")))
                .andExpect(jsonPath("$", hasKey("pageable")));
        verify(pageService, times(1)).getObservationsPage(anyString());
    }

    @Test
    void loadPopupContent() throws Exception {
        JSONArray json = new JSONArray("[\"CATC_OBS_CL.Rain_Nig_3\",\"CATC_OBS_CL.Rain_Nig_18\",\"CATC_OBS_CL.Rain_Nig_14\"]");
        PopupContent popupContent = new PopupContent();
        when(popupService.getPopupContent(anyList()))
                .thenReturn(popupContent);
        this.mockMvc.perform(post("/observation/loadPopupContent")
                .content(json.toString())
                .contentType("application/json"))
                .andExpect(jsonPath("$", hasKey("producerName")))
                .andExpect(jsonPath("$", hasKey("stationName")))
                .andExpect(jsonPath("$", hasKey("variableNameAndIds")));
        verify(popupService, times(1)).getPopupContent(anyList());
    }

    @Test
    void getProducerInfo() throws Exception {
        List<Producer> producers = new ArrayList<>();
        producers.add(new Producer());
        when(facetService.getProducerInfo())
                .thenReturn(producers);
        this.mockMvc.perform(get("/observation/getProducersInfo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        verify(facetService, times(1)).getProducerInfo();

    }

    @Test
    void showProducerDetailed() throws Exception {
        Producer producer = new Producer();
        when(detailService.getProducerDetailed(anyString()))
                .thenReturn(producer);
        this.mockMvc.perform(get("/observation/showProducerDetailed/producerId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("producerId")));
        verify(detailService, times(1)).getProducerDetailed(anyString());

    }

    @Test
    void showDatasetDetailed() throws Exception {
        ObservationDocument observationDocument = new ObservationDocument();
        when(detailService.getDatasetDetailed(anyString()))
                .thenReturn(observationDocument);
        this.mockMvc.perform(get("/observation/showDatasetDetailed/datasetId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("documentId")))
                .andExpect(jsonPath("$", hasKey("producer")))
                .andExpect(jsonPath("$", hasKey("dataset")))
                .andExpect(jsonPath("$", hasKey("observation")));
        verify(detailService, times(1)).getDatasetDetailed(anyString());

    }

    @Test
    void getProducerPage() throws Exception {
        Pageable pageable = PageRequest.of(1, 10);
        List<Producer> producers = new ArrayList<>();
        producers.add(new Producer());
        Page<Producer> expected = new PageImpl<>(producers, pageable, producers.size());
        when(pageService.getProducerPage(anyString())).thenReturn(expected);
        this.mockMvc.perform(post("/observation/changeProducerPage")
                .content("{\"filters\":{\"temporalExtents\":[],\"spatialExtent\":null,\"climates\":[],\"geologies\":[\"Quartenary soils\"],\"producerNames\":[],\"fundingNames\":[],\"fundingAcronyms\":[],\"fullText\":null,\"theiaCategories\":[],\"theiaVariables\":[]},\"pageSize\":10,\"pageSelected\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("content")))
                .andExpect(jsonPath("$", hasKey("pageable")));
        verify(pageService, times(1)).getProducerPage(anyString());

    }

    @Test
    void getDatastetPage() throws Exception {
        Pageable pageable = PageRequest.of(1, 10);
        List<Document> documents = new ArrayList<>();
        documents.add(new Document());
        Page<Document> expected = new PageImpl<>(documents, pageable, documents.size());
        when(pageService.getDatasetPage(anyString())).thenReturn(expected);
        this.mockMvc.perform(post("/observation/changeDatasetPage")
                .content("{\"filters\":{\"temporalExtents\":[],\"spatialExtent\":null,\"climates\":[],\"geologies\":[\"Quartenary soils\"],\"producerNames\":[],\"fundingNames\":[],\"fundingAcronyms\":[],\"fullText\":null,\"theiaCategories\":[],\"theiaVariables\":[]},\"pageSize\":10,\"pageSelected\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("content")))
                .andExpect(jsonPath("$", hasKey("pageable")));
        verify(pageService, times(1)).getDatasetPage(anyString());

    }
}