package fr.theia_land.in_situ.dataportal.service;

import fr.theia_land.in_situ.dataportal.model.POJO.ResponseDocument;
import fr.theia_land.in_situ.dataportal.model.POJO.detail.dataset.SpatialExtent;
import fr.theia_land.in_situ.dataportal.model.POJO.detail.observation.TheiaVariable;
import fr.theia_land.in_situ.dataportal.model.entity.ObservationDocument;
import fr.theia_land.in_situ.dataportal.repository.ObservationDocumentLiteRepository;
import fr.theia_land.in_situ.dataportal.repository.ObservationDocumentRepository;
import org.bson.Document;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    private final ObservationDocumentRepository observationDocumentRepository;
    private final ObservationDocumentLiteRepository observationDocumentLiteRepository;

    @Autowired
    public SearchServiceImpl(ObservationDocumentRepository observationDocumentRepository, ObservationDocumentLiteRepository observationDocumentLiteRepository) {
        this.observationDocumentRepository = observationDocumentRepository;
        this.observationDocumentLiteRepository = observationDocumentLiteRepository;
    }

    @Override
    public List<ObservationDocument> findByDocumentId(String payload) {
        List<ObservationDocument> observationDocuments = new ArrayList<>();
        JSONArray jsonDocumentIds = new JSONArray(payload);
        jsonDocumentIds.forEach(item -> {
            observationDocuments.add(this.observationDocumentRepository.findByDocumentId((String) item));
        });
        return observationDocuments;
    }

    @Override
    public List<String> getObservationIdsOfOtherTheiaVariableAtLocation(String payload) {
        return this.observationDocumentLiteRepository.getObservationIdsOfOtherTheiaVariableAtLocation(payload);
    }

    @Override
    public List<TheiaVariable> getVariablesAtOneLocation(String payload) {
        return this.observationDocumentLiteRepository.getVariablesAtOneLocation(payload);
    }

    @Override
    public List<Document> getObservationsOfADataset(String datasetId) {
        return this.observationDocumentLiteRepository.getObservationsOfADataset(datasetId);
    }

    @Override
    public SpatialExtent getBBOXOfOfADataset(String datasetId) {
        return this.observationDocumentRepository.findDatasetSpatialExtent(datasetId);
    }

    @Override
    public ResponseDocument searchObservations(String payload) {
        return this.observationDocumentLiteRepository.searchObservations(payload);
    }
}
