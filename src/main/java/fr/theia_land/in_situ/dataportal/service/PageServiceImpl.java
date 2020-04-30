package fr.theia_land.in_situ.dataportal.service;

import fr.theia_land.in_situ.dataportal.model.POJO.detail.producer.Producer;
import fr.theia_land.in_situ.dataportal.model.entity.ObservationDocumentLite;
import fr.theia_land.in_situ.dataportal.repository.ObservationDocumentLiteRepository;
import fr.theia_land.in_situ.dataportal.repository.ObservationDocumentRepository;
import org.bson.Document;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageServiceImpl implements PageService {
    private final ObservationDocumentLiteRepository observationDocumentLiteRepository;
    private final ObservationDocumentRepository observationDocumentRepository;

    @Autowired
    public PageServiceImpl(ObservationDocumentLiteRepository observationDocumentLiteRepository, ObservationDocumentRepository observationDocumentRepository) {
        this.observationDocumentLiteRepository = observationDocumentLiteRepository;
        this.observationDocumentRepository = observationDocumentRepository;
    }

    @Override
    public Page<ObservationDocumentLite> getObservationsPage(String payload) {
        JSONObject jsonPayload = new JSONObject(payload);
        return this.observationDocumentLiteRepository.getObservationsPage(
                this.observationDocumentLiteRepository.setMatchOperationUsingFilters(jsonPayload.getJSONObject("filters").toString(), null),
                PageRequest.of(jsonPayload.getInt("pageSelected") - 1,
                        jsonPayload.getInt("pageSize")));
    }

    @Override
    public Page<Producer> getProducerPage(String payload) {
        JSONObject jsonPayload = new JSONObject(payload);
        List<String> producerIds = this.observationDocumentLiteRepository.getDatasetOrProducerIds(
                this.observationDocumentLiteRepository.setMatchOperationUsingFilters(jsonPayload.getJSONObject("filters").toString(), "producer"));
        return this.observationDocumentRepository.getProducersPage(producerIds, PageRequest.of(jsonPayload.getInt("pageSelected") - 1,
                jsonPayload.getInt("pageSize")));
    }

    @Override
    public Page<Document> getDatasetPage(String payload) {
        JSONObject jsonPayload = new JSONObject(payload);
        List<String> datasetIds = this.observationDocumentLiteRepository.getDatasetOrProducerIds(
                this.observationDocumentLiteRepository.setMatchOperationUsingFilters(jsonPayload.getJSONObject("filters").toString(), "dataset"));
        return this.observationDocumentRepository.getDatasetsPage(datasetIds, PageRequest.of(jsonPayload.getInt("pageSelected") - 1,
                jsonPayload.getInt("pageSize")));
    }
}
