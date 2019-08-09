/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.DAO;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset.SpatialExtent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ReplaceRootOperation;
import org.springframework.data.mongodb.core.query.Criteria;

/**
 * Implementation of the interface defining custom methods. The repository infrastructure tries to autodetect custom
 * implementations by looking up classes in the package we found a repository using the naming conventions appending the
 * namespace element's attribute repository-impl-postfix to the classname. This suffix defaults to Impl. Then, Spring
 * pick up the custom bean by name rather than creating an instance.
 */
public class CustomObservationDocumentRepositoryImpl implements CustomObservationDocumentRepository {

    //Indicate that mongoTemplate must be injected by Spring IoC
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public SpatialExtent findDatasetSpatialExtent(String datasetId) {
        MatchOperation m1 = Aggregation.match(Criteria.where("dataset.datasetId").is(datasetId));
        ReplaceRootOperation r1 = Aggregation.replaceRoot("dataset.metadata.spatialExtent");
        LimitOperation l1 = Aggregation.limit(1);
        return mongoTemplate.aggregate(Aggregation.newAggregation(m1,r1,l1),"observations", SpatialExtent.class).getUniqueMappedResult();
        
    }

}
