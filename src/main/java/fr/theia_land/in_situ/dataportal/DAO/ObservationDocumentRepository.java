/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.DAO;

import fr.theia_land.in_situ.dataportal.model.ObservationDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface used by Spring IoC to inject ObservationDocumentRepository implementation when needed
 * simple CRUD operations are defined in MongoRepository interface
 * Spring data will defined ObservationDocumentRepositoryImpl.class that will be injected and instanciated by classes 
 * needing it
 */
public interface ObservationDocumentRepository extends MongoRepository<ObservationDocument, ObjectId>, CustomObservationDocumentRepository{
    
    /**
     * 
     * @param documentIds String - Id of the document such as '["id","id","id"]'
     * @return ObservationDocument.class
     */
    ObservationDocument findByDocumentId(String documentIds);
}
