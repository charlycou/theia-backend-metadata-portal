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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
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
        List<Producer> producers = mongoTemplate.aggregate(Aggregation.newAggregation(p1, g1, p2), "observations", Producer.class).getMappedResults();
        return producers;
    }
}
