/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.DAO;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset.SpatialExtent;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.producer.Producer;
import fr.theia_land.in_situ.dataportal.mdl.POJO.facet.TheiaCategoryTree;
import fr.theia_land.in_situ.dataportal.model.ObservationDocument;
import java.util.List;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface for custom method definition
 */
public interface CustomObservationDocumentRepository {

    SpatialExtent findDatasetSpatialExtent(String datasetId);

    List<TheiaCategoryTree> getCategoryHierarchies(List<String> uri);

    List<Producer> getProducersInfo();

    Page<Producer> getProducersPage(List<String> producerIds, Pageable pageable);
    
    Page<Document> getDatasetsPage(List<String> datasetIds, Pageable pageable);
    
    Producer getProducerDetailed(String producerId);
    
    ObservationDocument getDatasetDetailed(String datasetId);
}
