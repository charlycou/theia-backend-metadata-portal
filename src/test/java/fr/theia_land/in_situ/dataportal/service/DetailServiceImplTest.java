package fr.theia_land.in_situ.dataportal.service;

import fr.theia_land.in_situ.dataportal.model.POJO.detail.producer.Producer;
import fr.theia_land.in_situ.dataportal.model.entity.ObservationDocument;
import fr.theia_land.in_situ.dataportal.repository.ObservationDocumentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class DetailServiceImplTest {

    @Mock
    ObservationDocumentRepository observationDocumentRepositoryMock;
    @InjectMocks
    DetailServiceImpl service;

    @Test
    void getProducerDetailed() {
        when(observationDocumentRepositoryMock.getProducerDetailed(anyString())).thenReturn(new Producer());
        Producer producer = service.getProducerDetailed("producerId");
        assertThat(producer).isExactlyInstanceOf(Producer.class);
        verify(observationDocumentRepositoryMock,times(1)).getProducerDetailed(anyString());
    }

    @Test
    void getDatasetDetailed() {
        when(observationDocumentRepositoryMock.getDatasetDetailed(anyString())).thenReturn(new ObservationDocument());
        ObservationDocument observationDocument = service.getDatasetDetailed("datasetId");
        assertThat(observationDocument).isExactlyInstanceOf(ObservationDocument.class);
        verify(observationDocumentRepositoryMock,times(1)).getDatasetDetailed(anyString());

    }
}