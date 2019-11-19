/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.DAO;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset.SpatialExtent;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.producer.Producer;
import fr.theia_land.in_situ.dataportal.mdl.POJO.facet.TheiaCategoryFacetElement;
import fr.theia_land.in_situ.dataportal.mdl.POJO.facet.TheiaCategoryTree;
import fr.theia_land.in_situ.dataportal.model.ObservationDocument;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.ReplaceRootOperation;
import org.springframework.data.mongodb.core.aggregation.SkipOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
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
        return mongoTemplate.aggregate(Aggregation.newAggregation(m1, r1, l1), "observations", SpatialExtent.class).getUniqueMappedResult();
    }

    /**
     * Get the the category branches corresponding to a given variable
     *
     * @param uris List of uri with whom the variable is associated
     * @return List of Theia category tree
     */
    @Override
    public List<TheiaCategoryTree> getCategoryHierarchies(List<String> uris) {
        List<TheiaCategoryTree> categoryTrees = new ArrayList<>();
        List<String> hierarchy = new ArrayList<>();
        List<TheiaCategoryFacetElement> theiaCategoryFacetElement = mongoTemplate.findAll(TheiaCategoryFacetElement.class, "variableCategories");
        for (String uri : uris) {
            TheiaCategoryFacetElement cat = theiaCategoryFacetElement.stream().filter((t) -> {
                return t.getUri().equals(uri);
            }).findFirst().orElse(null);

            categoryTrees.add(TheiaCategoryTree.withBroaders(cat.getUri(), cat.getPrefLabel(), populateBroaders(cat.getBroaders(), theiaCategoryFacetElement), 0));
        }
        return categoryTrees;
    }

    /**
     * Recursive method to populate the broaders of TheiaCategory in order to print the category branch in the info
     * panel
     *
     * @param uriBroaders uri of the broaders of a given concept category
     * @param facetElements The list of TheiaCategoryFacetElement corresponding the cateory of the variable queried
     * @return Set of TheiaCategory populated with broaders
     */
    private Set<TheiaCategoryTree> populateBroaders(List<String> uriBroaders, List<TheiaCategoryFacetElement> facetElements) {
        //List to return
        Set<TheiaCategoryTree> broaders = new HashSet<>();
        //For each uri of the list uriBroaders, a new TheiaCategoryTree object is added to the broaders list.
        uriBroaders.forEach(uri -> {
            TheiaCategoryFacetElement facetElement = facetElements.stream().filter((t) -> {
                return t.getUri().equals(uri);
            }).findFirst().orElse(null);

            if (facetElement != null && facetElement.getBroaders().size() > 0) {
                broaders.add(TheiaCategoryTree.withBroaders(
                        facetElement.getUri(),
                        facetElement.getPrefLabel(),
                        populateBroaders(facetElement.getBroaders(), facetElements),
                        facetElement.getCount()));
            }

        });
        return broaders;
    }

    /**
     * Method to get each producer information. To be displayed in producer facet search
     *
     * @return List of Producer
     */
    @Override
    public List<Producer> getProducersInfo() {
        ProjectionOperation p1 = Aggregation.project()
                .and("producer.producerId").as("producerId")
                .and("producer.name").as("name")
                .and("producer.description").as("description")
                .and("producer.objectives").as("objectives")
                .and("producer.measuredVariables").as("measuredVariables");
        GroupOperation g1 = Aggregation.group("producerId")
                .addToSet("description").as("description")
                .addToSet("objectives").as("objectives")
                .addToSet("measuredVariables").as("measuredVariables")
                .addToSet("name").as("name");
        ProjectionOperation p2 = Aggregation.project()
                .and("_id").as("producerId").andExclude("_id")
                .and(ArrayOperators.ArrayElemAt.arrayOf("description").elementAt(0)).as("description")
                .and(ArrayOperators.ArrayElemAt.arrayOf("objectives").elementAt(0)).as("objectives")
                .and(ArrayOperators.ArrayElemAt.arrayOf("measuredVariables").elementAt(0)).as("measuredVariables")
                .and(ArrayOperators.ArrayElemAt.arrayOf("name").elementAt(0)).as("name");
        AggregationOptions options = AggregationOptions.builder().allowDiskUse(true).build();
        List<Producer> producers = mongoTemplate.aggregate(Aggregation.newAggregation(p1, g1, p2).withOptions(options), "observations", Producer.class).getMappedResults();
        return producers;
    }

    /**
     * Method to query a page of producers to diplay on list items
     *
     * @param producerIds List of id of the producer to be displayed
     * @param pageable describe the page to be returned
     * @return Page of producer
     */
    @Override
    public Page<Producer> getProducersPage(List<String> producerIds, Pageable pageable) {
        MatchOperation m1 = Aggregation.match(Criteria.where("producer.producerId").in(producerIds));
        ProjectionOperation p1 = Aggregation.project()
                .and("producer.producerId").as("producerId")
                .and("producer.name").as("name")
                .and("producer.description").as("description")
                .and("producer.objectives").as("objectives")
                .and("producer.measuredVariables").as("measuredVariables")
                .and("producer.title").as("title");
        GroupOperation g1 = Aggregation.group("producerId")
                .addToSet("description").as("description")
                .addToSet("objectives").as("objectives")
                .addToSet("measuredVariables").as("measuredVariables")
                .addToSet("title").as("title")
                .addToSet("name").as("name");
        ProjectionOperation p2 = Aggregation.project()
                .and("_id").as("producerId").andExclude("_id")
                .and(ArrayOperators.ArrayElemAt.arrayOf("description").elementAt(0)).as("description")
                .and(ArrayOperators.ArrayElemAt.arrayOf("objectives").elementAt(0)).as("objectives")
                .and(ArrayOperators.ArrayElemAt.arrayOf("measuredVariables").elementAt(0)).as("measuredVariables")
                .and(ArrayOperators.ArrayElemAt.arrayOf("name").elementAt(0)).as("name");
        SortOperation s1 = Aggregation.sort(Sort.by(Sort.Order.asc("name")));
        SkipOperation sk1 = Aggregation.skip((long) pageable.getPageNumber() * pageable.getPageSize());
        LimitOperation l1 = Aggregation.limit(pageable.getPageSize());
        int resultSize = mongoTemplate.aggregate(Aggregation.newAggregation(m1, p1, g1), "observations", Document.class).getMappedResults().size();
        AggregationOptions options = AggregationOptions.builder().allowDiskUse(true).build();
        List<Producer> producers = mongoTemplate.aggregate(Aggregation.newAggregation(m1, p1, g1, p2, s1, sk1, l1).withOptions(options), "observations", Producer.class).getMappedResults();
        return new PageImpl<>(producers, pageable, resultSize);
    }

    /**
     * Method to query a page of datasets to diplay on list items
     *
     * @param datasetIds List of id of the producer to be displayed
     * @param pageable describe the page to be returned
     * @return Page of dataset
     */
    @Override
    public Page<Document> getDatasetsPage(List<String> datasetIds, Pageable pageable) {
        MatchOperation m1 = Aggregation.match(Criteria.where("dataset.datasetId").in(datasetIds));
        ProjectionOperation p1 = Aggregation.project()
                .and("producer.producerId").as("producerId")
                .and("producer.name").as("producerName")
                .and("dataset.datasetId").as("datasetId")
                .and("dataset.metadata.title").as("title")
                .and("dataset.metadata.description").as("description")
                .and("dataset.metadata.objective").as("objective")
                .and("dataset.metadata.temporalExtent").as("temporalExtent")
                .and("dataset.metadata.spatialExtent").as("spatialExtent")
                .and("dataset.metadata.keywords").as("keywords");
        GroupOperation g1 = Aggregation.group("datasetId")
                .first("producerName").as("producerName")
                .first("producerId").as("producerId")
                .first("title").as("title")
                .first("description").as("description")
                .first("objective").as("objective")
                .first("temporalExtent").as("temporalExtent")
                .first("spatialExtent").as("spatialExtent")
                .first("keywords").as("keywords");
        ProjectionOperation p2 = Aggregation.project("producerId", "producerName", "title", "description", "objective", "temporalExtent", "spatialExtent", "keywords")
                .and("_id").as("datasetId").andExclude("_id");
        SortOperation s1 = Aggregation.sort(Sort.by(Sort.Order.asc("title")));
        SkipOperation sk1 = Aggregation.skip((long) pageable.getPageNumber() * pageable.getPageSize());
        LimitOperation l1 = Aggregation.limit(pageable.getPageSize());
        int resultSize = mongoTemplate.aggregate(Aggregation.newAggregation(m1, p1, g1), "observations", Document.class).getMappedResults().size();
        AggregationOptions options = AggregationOptions.builder().allowDiskUse(true).build();
        List<Document> datasets = mongoTemplate.aggregate(Aggregation.newAggregation(m1, p1, g1, p2, s1, sk1, l1).withOptions(options), "observations", Document.class).getMappedResults();
        return new PageImpl<>(datasets, pageable, resultSize);
    }

    /**
     * Get the detailed producer object to be displayed in info panel
     *
     * @param producerId id of hte producer to be displayed
     * @return Producer object
     */
    @Override
    public Producer getProducerDetailed(String producerId) {
        MatchOperation m1 = Aggregation.match(Criteria.where("producer.producerId").is(producerId));
        ProjectionOperation p1 = Aggregation.project("producer");
        GroupOperation g1 = Aggregation.group("producer");
        AggregationOptions options = AggregationOptions.builder().allowDiskUse(true).build();
        return mongoTemplate.aggregate(Aggregation.newAggregation(m1, p1, g1).withOptions(options), "observations", Producer.class).getUniqueMappedResult();
    }

    /**
     * Fet the detailed dataset object to be displayed iin hte info panel
     * @param datasetId id of the dataset
     * @return ObservationDocument object with Producer and Dataset object properties
     */
    @Override
    public ObservationDocument getDatasetDetailed(String datasetId) {
        MatchOperation m1 = Aggregation.match(Criteria.where("dataset.datasetId").is(datasetId));
        ProjectionOperation p1 = Aggregation.project("producer", "dataset");
        GroupOperation g1 = Aggregation.group("producer", "dataset");
        AggregationOptions options = AggregationOptions.builder().allowDiskUse(true).build();
        return mongoTemplate.aggregate(Aggregation.newAggregation(m1, p1, g1).withOptions(options), "observations", ObservationDocument.class).getUniqueMappedResult();
    }
}
