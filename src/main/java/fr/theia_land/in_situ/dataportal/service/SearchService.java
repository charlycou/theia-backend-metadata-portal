package fr.theia_land.in_situ.dataportal.service;

import fr.theia_land.in_situ.dataportal.model.POJO.ResponseDocument;
import fr.theia_land.in_situ.dataportal.model.POJO.detail.dataset.SpatialExtent;
import fr.theia_land.in_situ.dataportal.model.POJO.detail.observation.TheiaVariable;
import fr.theia_land.in_situ.dataportal.model.entity.ObservationDocument;
import org.bson.Document;

import java.util.List;

public interface SearchService {
    public List<ObservationDocument> findByDocumentId(String payload);
    public List<String> getObservationIdsOfOtherTheiaVariableAtLocation(String payload);
    public List<TheiaVariable> getVariablesAtOneLocation(String payload);
    public List<Document> getObservationsOfADataset(String datasetId);
    public SpatialExtent getBBOXOfOfADataset(String datasetId);
    public ResponseDocument searchObservations(String payload);
}
