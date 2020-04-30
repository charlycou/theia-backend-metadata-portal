package fr.theia_land.in_situ.dataportal.service;

import fr.theia_land.in_situ.dataportal.model.POJO.detail.observation.I18n;
import fr.theia_land.in_situ.dataportal.model.POJO.detail.producer.Producer;
import fr.theia_land.in_situ.dataportal.model.POJO.facet.FacetClassification;
import fr.theia_land.in_situ.dataportal.model.POJO.facet.TheiaCategoryTree;
import fr.theia_land.in_situ.dataportal.repository.ObservationDocumentLiteRepository;
import fr.theia_land.in_situ.dataportal.repository.ObservationDocumentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class FacetServiceImplTest {
    @Mock
    ObservationDocumentLiteRepository observationDocumentLiteRepositoryMock;

    @Mock
    ObservationDocumentRepository observationDocumentRepositoryMock;

    @InjectMocks
    FacetServiceImpl service;

    @Test
    void initFacets() {
        when(observationDocumentLiteRepositoryMock.initFacets()).thenReturn(new FacetClassification());
        FacetClassification facetClassification = service.initFacets();
        assertThat(facetClassification).isExactlyInstanceOf(FacetClassification.class);
        verify(observationDocumentLiteRepositoryMock, times(1)).initFacets();
    }

    @Test
    void getCategoryHierarchies() {
        I18n en = new I18n();
        en.setLang("en");
        en.setText("test");
        List<I18n> prefLabel = new ArrayList<>();
        prefLabel.add(en);
        TheiaCategoryTree theiaCategoryTree = new TheiaCategoryTree();
        theiaCategoryTree.setPrefLabel(prefLabel);
        List<TheiaCategoryTree> trees = new ArrayList<TheiaCategoryTree>(){
                {
                    add(theiaCategoryTree);
                }
        };
        when(observationDocumentRepositoryMock.getCategoryHierarchies(anyList()))
                .thenReturn(trees);
        List<String> payload = new ArrayList<>();
        payload.add("category");
        List<List<List<I18n>>> categoryHierarchies = this.service.getCategoryHierarchies(payload);
        assertThat(categoryHierarchies.get(0).get(0).get(0).getLang()).isEqualTo("en");
        assertThat(categoryHierarchies.get(0).get(0).get(0).getText()).isEqualTo("test");
        verify(observationDocumentRepositoryMock,times(1)).getCategoryHierarchies(anyList());

    }

    @Test
    void getProducerInfo() {
        List<Producer> producers = new ArrayList<>();
        producers.add(new Producer());
        when(observationDocumentRepositoryMock.getProducersInfo()).thenReturn(producers);
        List<Producer> producerInfo = service.getProducerInfo();
        assertThat(producerInfo).hasSize(1).hasOnlyElementsOfType(Producer.class);
        verify(observationDocumentRepositoryMock,times(1)).getProducersInfo();
    }
}