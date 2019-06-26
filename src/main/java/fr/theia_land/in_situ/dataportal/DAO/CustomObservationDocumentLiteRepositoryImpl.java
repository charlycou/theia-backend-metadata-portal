/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.DAO;

import fr.theia_land.in_situ.dataportal.mdl.POJO.I18n;
import fr.theia_land.in_situ.dataportal.model.MapItem;
import fr.theia_land.in_situ.dataportal.model.ObservationLiteId;
import fr.theia_land.in_situ.dataportal.model.ObservationDocumentLite;
import fr.theia_land.in_situ.dataportal.mdl.POJO.facet.FacetClassification;
import fr.theia_land.in_situ.dataportal.model.PopupDocument;
import fr.theia_land.in_situ.dataportal.model.PopupContent;
import fr.theia_land.in_situ.dataportal.model.ResponseDocument;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.facet;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.skip;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import static org.springframework.data.mongodb.core.aggregation.ArrayOperators.Filter.filter;
import static org.springframework.data.mongodb.core.aggregation.ComparisonOperators.Eq.valueOf;
import org.springframework.data.mongodb.core.aggregation.FacetOperation;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;

/**
 * Implementation of the interface defining custom methods. The repository infrastructure tries to autodetect custom
 * implementations by looking up classes in the package we found a repository using the naming conventions appending the
 * namespace element's attribute repository-impl-postfix to the classname. This suffix defaults to Impl. Then, Spring
 * pick up the custom bean by name rather than creating an instance.
 */
public class CustomObservationDocumentLiteRepositoryImpl implements CustomObservationDocumentLiteRepository {

    //Indicate that mongoTemplate must be injected by Spring IoC
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Facet aggregation operation that is used to create the facet of the application
     */
    FacetOperation facetOperation = facet(
            unwind("producer.fundings"),
            project().and("producer.fundings.type").as("type").and("producer.fundings.acronym").as("name"),
            group("name", "type").count().as("count"),
            project("count").and("_id.name").as("name").and("_id.type").as("type").andExclude("_id")
    ).as("fundingAcronymsFacet")
            .and(unwind("producer.fundings"),
                    project().and("producer.fundings.type").as("type")
                            .and(filter("producer.fundings.name").as("item")
                                    .by(valueOf("item.lang")
                                            .equalToValue("en")))
                            .as("name"),
                    project("type").and(ArrayOperators.ArrayElemAt.arrayOf("name.text").elementAt(0)).as("name"),
                    group("type", "name").count().as("count"),
                    project("count").and("_id.name").as("name").and("_id.type").as("type").andExclude("_id")
            ).as("fundingNamesFacet")
            .and(unwind("dataset.metadata.portalSearchCriteria.climates"),
                    project().and("dataset.metadata.portalSearchCriteria.climates").as("name"),
                    group("name").count().as("count")
            ).as("climatesFacet")
            .and(unwind("dataset.metadata.portalSearchCriteria.geologies"),
                    project().and("dataset.metadata.portalSearchCriteria.geologies").as("name"),
                    group("name").count().as("count")
            ).as("geologiesFacet")
            .and(project().and(filter("producer.name").as("item")
                    .by(valueOf("item.lang")
                            .equalToValue("en")))
                    .as("name"),
                    project().and(ArrayOperators.ArrayElemAt.arrayOf("name.text").elementAt(0)).as("name"),
                    group("name").count().as("count")
            ).as("producerNamesFacet")
            .and(group().count().as("count"))
            .as("totalCount");

    /**
     * Method used to query the database using query filters. The method query the database, generate the new facets
     * depending on the result, return items to be printed on the map and paginated lite results.
     *
     * @param queryElements String that can be parsed into json defining the query filters
     * @return ResponseDocument document containing the facet classification, the MapItems to be mapped and the
     * paginated results
     */
    @Override
    public ResponseDocument searchObservations(String queryElements) {
        //ResponseDocument Object to be returned
        ResponseDocument responseDocument = new ResponseDocument();

        /**
         * The aggregation pipeline is executed to obtain the list of ObservationDocument lite corresponding to the
         * query. The following result is Paginated and stored into the ResponseDocument object
         */
        AggregationOptions options = AggregationOptions.builder().allowDiskUse(true).build();
        responseDocument.setObservationDocumentLitePage(getObservationsPage(queryElements, PageRequest.of(0, 10)));

        /**
         * Aggregation pipeline to be executed to find the observation matching the query. The result is stored as
         * MapItem.class, that are very light weight object to be added onto the map
         */
        List<AggregationOperation> aggregationOperations = setMatchOperationUsingFilters(queryElements);

        /**
         * Get the list of the observation ids that are resulting the query.
         */
        Set<List<String>> documentIdsFromObservationLite = new HashSet<>();
        //Get the list of "documentIds" of ObservationLite collection
        List<ObservationLiteId> observationLiteIds = mongoTemplate.aggregate(Aggregation.newAggregation(aggregationOperations)
                .withOptions(options), "observationsLite", ObservationLiteId.class).getMappedResults();
        //Store each "documentIds" in documentIdsFromObservationLite Set object
        observationLiteIds.forEach(item -> {
            documentIdsFromObservationLite.add(item.getDocumentIds());
        });
        /**
         * From the list of ids resulting the query on "observationLite" collection, the list of station mesuring the
         * observtions is queried from "mapItems" collection. The document of the collection "mapItems" that have a
         * field "doucmentIds" containing at least one element of the "documentIdsFromObservationLite" Set object are
         * queried.
         */
        List<MapItem> mapItems = mongoTemplate.find(Query.query(Criteria.where("documentIds").in(documentIdsFromObservationLite)), MapItem.class, "mapItems");
        mapItems.forEach(item -> {
            /**
             * For each document queried from the "mapItems" collection, the ids from the field documentIds that are not
             * present in "documentIdsFromObservationLite" Set object are removed.
             */
            Set<List<String>> documentIdsFromMapItems = new HashSet<>(item.getDocumentIds());
            documentIdsFromMapItems.retainAll(documentIdsFromObservationLite);
            item.setDocumentIds(new ArrayList<>(documentIdsFromMapItems));
        });
        responseDocument.setMapItems(mapItems);
        /**
         * Add the Facet aggregation operation to the pipeline to generate the relative facet. The following result is
         * stored into the ResponseDocument object
         */
        aggregationOperations.add(facetOperation);
        responseDocument.setFacetClassification(mongoTemplate.aggregate(
                Aggregation.newAggregation(aggregationOperations).withOptions(options), "observationsLite", FacetClassification.class)
                .getMappedResults());

        return responseDocument;
    }

    /**
     * Method to calculate the facet from the whole database
     *
     * @return FacetClassification object containing the facet element to be printed on UI
     */
    @Override
    public FacetClassification initFacets() {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        AggregationOptions options = AggregationOptions.builder().allowDiskUse(true).build();
        aggregationOperations.add(facetOperation);
        FacetClassification facet = mongoTemplate.aggregate(
                Aggregation.newAggregation(aggregationOperations).withOptions(options), "observationsLite", FacetClassification.class)
                .getUniqueMappedResult();
        return facet;
    }

    /**
     * Method used to change the page of the paginated result.
     *
     * @param queryElements String that can be parsed into json containing the filter to be queried
     * @param pageable Pageable object containing the number and the length of the page to be returned
     * @return Page object containing the results
     */
    @Override
    public Page<ObservationDocumentLite> getObservationsPage(String queryElements, Pageable pageable) {
        //Aggregation pipeline to be executed to find the observation matching the query
        List<AggregationOperation> aggregationOperations = setMatchOperationUsingFilters(queryElements);
        //Add aggregationOperation to pipeline to set pagination
        aggregationOperations.add(skip((long) pageable.getPageNumber() * pageable.getPageSize()));
        aggregationOperations.add(limit(pageable.getPageSize()));
        AggregationOptions options = AggregationOptions.builder().allowDiskUse(true).build();
        List<ObservationDocumentLite> result = mongoTemplate.aggregate(Aggregation.newAggregation(aggregationOperations)
                .withOptions(options), "observationsLite", ObservationDocumentLite.class).getMappedResults();
        return new PageImpl<>(result, pageable, result.size());
    }

    /**
     * Method used to parse the String containing the filters into aggregation operation
     *
     * @param queryElements String that can be parsed into json object.
     * @return List of AggregationOperation composed of MatchOperation of each filter of the queryElements parameter
     */
    private List<AggregationOperation> setMatchOperationUsingFilters(String queryElements) {
        //Aggregation pipeline to be executed to find the observation matching the query
        List<AggregationOperation> aggregationOperations = new ArrayList<>();

        //Json object containing the text and the facet element to be queried 
        JSONObject jsonQueryElement = new JSONObject(queryElements);
        /**
         * For each element to be queried, a match aggregation operation is created and added to the aggregation
         * pipeline
         * -------------------------------------------------------------------------------------------------------------
         */
        /**
         * Match operation for the full text query parameter
         */
        if (!jsonQueryElement.isNull("fullText")) {
            aggregationOperations.add(match(new TextCriteria().matchingAny(jsonQueryElement.getString("fullText"))));
        }

        /**
         * Match operation for the temporal extent parameters Document will not be returned only if the temporal extent
         * queried is outside the temporal extent of the document
         */
        if (jsonQueryElement.getJSONArray("temporalExtents").length() > 0) {
            List<Criteria> temporalExtentCriterias = new ArrayList<>();
            jsonQueryElement.getJSONArray("temporalExtents").forEach(item -> {
                JSONObject tmpExtent = (JSONObject) item;
                Instant from = Instant.parse(tmpExtent.getString("fromDate"));
                Instant to = Instant.parse(tmpExtent.getString("toDate"));
                temporalExtentCriterias.add(
                        Criteria.where("observation.temporalExtent").elemMatch(
                                new Criteria().orOperator(
                                        new Criteria().andOperator(
                                                Criteria.where("dateBeg")
                                                        .gte(from)
                                                        .lte(to),
                                                Criteria.where("dateEnd")
                                                        .gte(from)
                                                        .lte(to)
                                        ),
                                        new Criteria().andOperator(
                                                Criteria.where("dateBeg")
                                                        .lte(from)
                                                        .lte(to),
                                                Criteria.where("dateEnd")
                                                        .gte(from)
                                                        .gte(to)
                                        ),
                                        new Criteria().andOperator(
                                                Criteria.where("dateBeg")
                                                        .lte(from)
                                                        .lte(to),
                                                Criteria.where("dateEnd")
                                                        .gte(from)
                                                        .lte(to)
                                        ),
                                        new Criteria().andOperator(
                                                Criteria.where("dateBeg")
                                                        .gte(from)
                                                        .lte(to),
                                                Criteria.where("dateEnd")
                                                        .gte(from)
                                                        .gte(to)
                                        )
                                )
                        )
                );
            });
            aggregationOperations.add(Aggregation.match(new Criteria().orOperator(temporalExtentCriterias.toArray(new Criteria[temporalExtentCriterias.size()]))));
        }

        if (!jsonQueryElement.isNull("spatialExtent")) {
            List<Criteria> spatialExtentCriterias = new ArrayList<>();
            JSONArray features = jsonQueryElement.getJSONObject("spatialExtent").getJSONArray("features");
            features.forEach(item1 -> {
                JSONObject feature = (JSONObject) item1;
                List<Point> points = new ArrayList<>();
                feature.getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(0).forEach(item2 -> {
                    JSONArray point = (JSONArray) item2;
                    points.add(new Point(point.getDouble(0), point.getDouble(1)));
                });
                spatialExtentCriterias.add(
                        Criteria.where("observation.featureOfInterest.samplingFeature.geometry").within(new GeoJsonPolygon(points)));
            });
            aggregationOperations.add(Aggregation.match(new Criteria().orOperator(spatialExtentCriterias.toArray(new Criteria[spatialExtentCriterias.size()]))));
        }

        /**
         * Match operation for each bucket element query parameters
         */
        jsonQueryElement.getJSONArray("climates").forEach(item -> {
            aggregationOperations.add(
                    Aggregation.match(
                            Criteria.where("dataset.metadata.portalSearchCriteria.climates")
                                    .is(item)));
        });
        jsonQueryElement.getJSONArray("geologies").forEach(item -> {
            aggregationOperations.add(
                    Aggregation.match(
                            Criteria.where("dataset.metadata.portalSearchCriteria.geologies")
                                    .is(item)));
        });
        jsonQueryElement.getJSONArray("producerNames").forEach(item -> {
            aggregationOperations.add(match(Criteria.where("producer.name").elemMatch(
                    Criteria.where("lang").is("en").and("text").is(item))));
        });
        jsonQueryElement.getJSONArray("fundingNames").forEach(item -> {
            aggregationOperations.add(match(Criteria.where("producer.fundings").elemMatch(
                    Criteria.where("name").elemMatch(
                            Criteria.where("lang").is("en").and("text").is(item)))));
        });
        jsonQueryElement.getJSONArray("fundingAcronyms").forEach(item -> {
            aggregationOperations.add(match(Criteria.where("producer.fundings").elemMatch(
                    Criteria.where("acronym").elemMatch(
                            Criteria.where("lang").is("en").and("text").is(item)))));
        });
        return aggregationOperations;
    }

    /**
     * Method used to query the popup content depending of the document ids parameter
     *
     * @param ids String that can be parsed inot JSON object containing and array of documentIds
     * @return PopupContent object
     */
    @Override
    public PopupContent loadPopupContent(String ids) {
        //parse the string ids parameter into JSON Object
        JSONObject jsonIds = new JSONObject(ids);
        //Parse json of ids into Set of List of String
        Set<List<String>> documentIds = new HashSet<>();
        jsonIds.getJSONArray("ids").forEach(item1 -> {
            JSONArray itemArray1 = (JSONArray) item1;
            List<String> itemArray2 = new ArrayList<>();
            itemArray1.forEach(item2 -> {
                itemArray2.add((String) item2);
            });
            documentIds.add(itemArray2);
            // documentIds.add(item1.toString());
        });
        //Query the "observationsLite" collection using the newly created documentIds Set object
        List<PopupDocument> result = mongoTemplate.aggregate(Aggregation.newAggregation(
                match(Criteria.where("documentIds").in(documentIds))), "observationsLite", PopupDocument.class).getMappedResults();

        //Set the PopupContent Object to be returned
        PopupContent popupContent = new PopupContent();
        popupContent.setProducerName(result.get(0).getProducer().getName());
        //Condition on station name since it is not a mandatory field
        if (!result.get(0).getObservation().getFeatureOfInterest().getSamplingFeature().getName().isEmpty()) {
            popupContent.setStationName(result.get(0).getObservation().getFeatureOfInterest().getSamplingFeature().getName());
        }
        List<PopupContent.VariableNameAndId> variableNameAndIds = new ArrayList<>();
        result.forEach(item -> {
            PopupContent.VariableNameAndId variableNameAndId = new PopupContent.VariableNameAndId();
            variableNameAndId.setIds(item.getDocumentIds());
            if (item.getObservation().getObservedProperties().get(0).getTheiaVariable() != null) {
                variableNameAndId.setTheiaVariableName(item.getObservation().getObservedProperties().get(0).getTheiaVariable());
            } else {
                List<List<I18n>> producerVariableNames = new ArrayList();
                item.getObservation().getObservedProperties().forEach(element -> {
                    producerVariableNames.add(element.getName());
                });
                variableNameAndId.setProducerVariableNames(producerVariableNames);
            }
                
            variableNameAndIds.add(variableNameAndId);
        });

        popupContent.setVariableNameAndIds(variableNameAndIds);
        return popupContent;
    }

}
