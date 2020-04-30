package fr.theia_land.in_situ.dataportal.service;

import fr.theia_land.in_situ.dataportal.model.POJO.detail.producer.Producer;
import fr.theia_land.in_situ.dataportal.model.entity.ObservationDocument;

public interface DetailService {
    public Producer getProducerDetailed(String producerId);
    public ObservationDocument getDatasetDetailed(String datasetId);
}
