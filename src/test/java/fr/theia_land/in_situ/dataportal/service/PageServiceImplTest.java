package fr.theia_land.in_situ.dataportal.service;

import fr.theia_land.in_situ.dataportal.model.POJO.detail.producer.Producer;
import fr.theia_land.in_situ.dataportal.model.entity.ObservationDocumentLite;
import fr.theia_land.in_situ.dataportal.repository.ObservationDocumentLiteRepository;
import fr.theia_land.in_situ.dataportal.repository.ObservationDocumentRepository;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@SpringBootTest
class PageServiceImplTest {
    @Mock
    ObservationDocumentRepository observationDocumentRepositoryMock;
    @Mock
    ObservationDocumentLiteRepository observationDocumentLiteRepositoryMock;
    @InjectMocks
    PageServiceImpl service;

    @Test
    void getObservationsPage() {
        Pageable pageable = PageRequest.of(1,10);
        List<ObservationDocumentLite> observationDocumentLites = new ArrayList<>();
        observationDocumentLites.add(new ObservationDocumentLite());
        Page<ObservationDocumentLite> expected = new PageImpl<>(observationDocumentLites, pageable, observationDocumentLites.size());
        when(observationDocumentLiteRepositoryMock.getObservationsPage(anyList(), ArgumentMatchers.any(Pageable.class)))
                .thenReturn(expected);
        Page actual = service.getObservationsPage("{\n" +
                "\t\"filters\": {\n" +
                "\t\t\"payload\": \"test\"\n" +
                "\t},\n" +
                "\t\"pageSelected\": \"1\",\n" +
                "\t\"pageSize\":\"10\"\n" +
                "}");
        assertThat(actual).isEqualTo(expected);
        verify(observationDocumentLiteRepositoryMock,times(1))
                .getObservationsPage(anyList(), ArgumentMatchers.any(Pageable.class));
    }

    @Test
    void getProducerPage() {
        Pageable pageable = PageRequest.of(1,10);
        List<Producer> producers = new ArrayList<>();
        producers.add(new Producer());
        Page<Producer> expected = new PageImpl<>(producers, pageable, producers.size());
        List<String> strings = new ArrayList<>();
        strings.add("test");
        when(observationDocumentLiteRepositoryMock.getDatasetOrProducerIds(anyList())).thenReturn(strings);
        when(observationDocumentRepositoryMock.getProducersPage(anyList(),any(Pageable.class))).thenReturn(expected);
        Page<Producer> actual = service.getProducerPage("{\n" +
                "\t\"filters\": {\n" +
                "\t\t\"payload\": \"test\"\n" +
                "\t},\n" +
                "\t\"pageSelected\": \"1\",\n" +
                "\t\"pageSize\":\"10\"\n" +
                "}");
        assertThat(actual).isEqualTo(expected);
        verify(observationDocumentLiteRepositoryMock,times(1)).getDatasetOrProducerIds(anyList());
        verify(observationDocumentRepositoryMock,times(1)).getProducersPage(anyList(),any(Pageable.class));
    }

    @Test
    void getDatasetPage() {
        Pageable pageable = PageRequest.of(1,10);
        List<Document> documents = new ArrayList<>();
        documents.add(new Document());
        Page<Document> expected = new PageImpl<>(documents, pageable, documents.size());
        List<String> strings = new ArrayList<>();
        strings.add("test");
        when(observationDocumentLiteRepositoryMock.getDatasetOrProducerIds(anyList())).thenReturn(strings);
        when(observationDocumentRepositoryMock.getDatasetsPage(anyList(),any(Pageable.class))).thenReturn(expected);
        Page<Document> actual = service.getDatasetPage("{\n" +
                "\t\"filters\": {\n" +
                "\t\t\"payload\": \"test\"\n" +
                "\t},\n" +
                "\t\"pageSelected\": \"1\",\n" +
                "\t\"pageSize\":\"10\"\n" +
                "}");
        assertThat(actual).isEqualTo(expected);
        verify(observationDocumentLiteRepositoryMock,times(1)).getDatasetOrProducerIds(anyList());
        verify(observationDocumentRepositoryMock,times(1)).getDatasetsPage(anyList(),any(Pageable.class));
    }
}