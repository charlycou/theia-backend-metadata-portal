package fr.theia_land.in_situ.dataportal.service;

import fr.theia_land.in_situ.dataportal.model.POJO.detail.producer.Producer;
import fr.theia_land.in_situ.dataportal.model.entity.ObservationDocumentLite;
import org.bson.Document;
import org.springframework.data.domain.Page;

public interface PageService {
    public Page<ObservationDocumentLite> getObservationsPage(String payload);
    public Page<Producer> getProducerPage(String payload);
    public Page<Document> getDatasetPage(String payload);

}
