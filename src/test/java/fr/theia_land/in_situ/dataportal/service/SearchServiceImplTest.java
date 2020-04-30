package fr.theia_land.in_situ.dataportal.service;

import fr.theia_land.in_situ.dataportal.model.POJO.ResponseDocument;
import fr.theia_land.in_situ.dataportal.model.POJO.detail.dataset.SpatialExtent;
import fr.theia_land.in_situ.dataportal.model.POJO.detail.observation.TheiaVariable;
import fr.theia_land.in_situ.dataportal.model.entity.ObservationDocument;
import fr.theia_land.in_situ.dataportal.repository.ObservationDocumentLiteRepository;
import fr.theia_land.in_situ.dataportal.repository.ObservationDocumentRepository;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class SearchServiceImplTest {

    @Mock
    ObservationDocumentLiteRepository observationDocumentLiteRepositoryMock;
    @Mock
    ObservationDocumentRepository observationDocumentRepositoryMock;
    @InjectMocks
    SearchServiceImpl service;

    @Test
    void findByDocumentId() {
        when(observationDocumentRepositoryMock.findByDocumentId(anyString()))
                .thenReturn(new ObservationDocument());
        String payload = "[\"id1\",\"id2\"]";
        List<ObservationDocument> byDocumentId = service.findByDocumentId(payload);
        assertThat(byDocumentId).hasSize(2).hasOnlyElementsOfType(ObservationDocument.class);
        verify(observationDocumentRepositoryMock,times(2)).findByDocumentId(anyString());
    }

    @Test
    void getObservationIdsOfOtherTheiaVariableAtLocation() {
        List<String> expected = new ArrayList<>();
        expected.add("test");
        String payload = "payload";
        when(observationDocumentLiteRepositoryMock.getObservationIdsOfOtherTheiaVariableAtLocation(anyString()))
                .thenReturn(expected);
        List<String> actual = service.getObservationIdsOfOtherTheiaVariableAtLocation(payload);
        assertThat(actual).hasOnlyElementsOfType(String.class).hasSize(1);
    }

    @Test
    void getVariablesAtOneLocation() {
        List<TheiaVariable> expected = new ArrayList<>();
        expected.add(new TheiaVariable());
        String payload = "payload";
        when(observationDocumentLiteRepositoryMock.getVariablesAtOneLocation(anyString()))
                .thenReturn(expected);
        List<TheiaVariable> actual = service.getVariablesAtOneLocation(payload);
        assertThat(actual).hasOnlyElementsOfType(TheiaVariable.class).hasSize(1);
    }

    @Test
    void getObservationsOfADataset() {
        List<Document> expected = new ArrayList<>();
        expected.add(new Document());
        String payload = "payload";
        when(observationDocumentLiteRepositoryMock.getObservationsOfADataset(anyString()))
                .thenReturn(expected);
        List<Document> actual = service.getObservationsOfADataset(payload);
        assertThat(actual).hasOnlyElementsOfType(Document.class).hasSize(1);
    }

    @Test
    void getBBOXOfOfADataset() {
        SpatialExtent expected = new SpatialExtent();
        String payload = "payload";
        when(observationDocumentRepositoryMock.findDatasetSpatialExtent(anyString()))
                .thenReturn(expected);
        SpatialExtent actual = service.getBBOXOfOfADataset(payload);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void searchObservations() {
        ResponseDocument expected = new ResponseDocument();
        String payload = "payload";
        when(observationDocumentLiteRepositoryMock.searchObservations(anyString()))
                .thenReturn(expected);
        ResponseDocument actual = service.searchObservations(payload);
        assertThat(actual).isEqualTo(expected);
    }
}