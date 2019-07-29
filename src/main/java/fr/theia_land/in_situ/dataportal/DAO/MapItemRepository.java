/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.DAO;

import fr.theia_land.in_situ.dataportal.model.MapItem;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author coussotc
 */
public interface MapItemRepository extends MongoRepository<MapItem, ObjectId>{
    List<MapItem> findByDatasetId(String datasetId);
}
