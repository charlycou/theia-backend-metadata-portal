package fr.theia_land.in_situ.dataportal.service;

import fr.theia_land.in_situ.dataportal.model.POJO.detail.producer.Producer;
import fr.theia_land.in_situ.dataportal.model.entity.ObservationDocument;
import fr.theia_land.in_situ.dataportal.repository.ObservationDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailServiceImpl implements DetailService {
    private final ObservationDocumentRepository observationDocumentRepository;

    @Autowired
    public DetailServiceImpl(ObservationDocumentRepository observationDocumentRepository) {
        this.observationDocumentRepository = observationDocumentRepository;
    }

    @Override
    public Producer getProducerDetailed(String producerId) {
        return this.observationDocumentRepository.getProducerDetailed(producerId);
    }

    @Override
    public ObservationDocument getDatasetDetailed(String datasetId) {
        return this.observationDocumentRepository.getDatasetDetailed(datasetId);
    }
}
