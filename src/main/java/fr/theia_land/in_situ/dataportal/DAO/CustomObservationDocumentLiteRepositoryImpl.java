/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.DAO;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.I18n;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.TheiaVariable;
import fr.theia_land.in_situ.dataportal.mdl.POJO.facet.FacetClassification;
import fr.theia_land.in_situ.dataportal.model.MapItem;
import fr.theia_land.in_situ.dataportal.model.ObservationDocumentLite;
import fr.theia_land.in_situ.dataportal.mdl.POJO.facet.FacetClassificationTmp;
import fr.theia_land.in_situ.dataportal.mdl.POJO.facet.TheiaCategoryTree;
import fr.theia_land.in_situ.dataportal.mdl.POJO.facet.TheiaCategoryFacetElement;
import fr.theia_land.in_situ.dataportal.model.PopupDocument;
import fr.theia_land.in_situ.dataportal.model.PopupContent;
import fr.theia_land.in_situ.dataportal.model.ResponseDocument;
import fr.theia_land.in_situ.import_module.CustomConfig.GenericAggregationOperation;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Box;
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
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.ReplaceRootOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
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
    FacetOperation facetOperation = facet()
            .and(
                    unwind("observations"),
                    Aggregation.graphLookup("variableCategories")
                            .startWith("observations.observedProperty.theiaCategories")
                            .connectFrom("broaders")
                            .connectTo("uri")
                            .as("categoryHierarchy"),
                    project("categoryHierarchy").and("observations.observedProperty.theiaVariable").as("theiaVariable"),
                    unwind("categoryHierarchy"),
                    group("categoryHierarchy")
                            .count().as("count")
                            .push("theiaVariable").as("theiaVariables"),
                    project().and("_id.uri").as("uri")
                            .and("_id.broaders").as("broaders")
                            .and("_id.narrowers").as("narrowers")
                            .and("_id.prefLabel").as("prefLabel")
                            .and("count").as("count")
                            .and("theiaVariables").as("theiaVariables")
            )
            .as("theiaCategorieFacetElements")
            .and(unwind("producer.fundings"),
                    project().and("producer.fundings.type").as("type")
                            .and("producer.fundings.acronym").as("acronym")
                            .and(filter("producer.fundings.name").as("item")
                                    .by(valueOf("item.lang")
                                            .equalToValue("en")))
                            .as("name"),
                    project("type")
                            .and(ArrayOperators.ArrayElemAt.arrayOf("name.text").elementAt(0)).as("name")
                            .and("acronym").as("acronym"),
                    group("type", "name", "acronym").count().as("count"),
                    project("count").and("_id.name").as("name").and("_id.acronym").as("acronym").and("_id.type").as("type").andExclude("_id"),
                     Aggregation.sort(Sort.Direction.ASC, "name")
            ).as("fundingNamesFacet")
            .and(unwind("dataset.metadata.portalSearchCriteria.climates"),
                    project().and("dataset.metadata.portalSearchCriteria.climates").as("name"),
                    group("name").count().as("count"),
                    Aggregation.sort(Sort.Direction.ASC, "_id")
            ).as("climatesFacet")
            .and(unwind("dataset.metadata.portalSearchCriteria.geologies"),
                    project().and("dataset.metadata.portalSearchCriteria.geologies").as("name"),
                    group("name").count().as("count"),
                    Aggregation.sort(Sort.Direction.ASC, "_id")
            ).as("geologiesFacet")
            .and(project().and(filter("producer.name").as("item")
                    .by(valueOf("item.lang")
                            .equalToValue("en")))
                    .as("name"),
                    project().and(ArrayOperators.ArrayElemAt.arrayOf("name.text").elementAt(0)).as("name"),
                    group("name").count().as("count"),
                    Aggregation.sort(Sort.Direction.ASC, "_id")
            ).as("producerNamesFacet")
            .and(group().count().as("count"))
            .as("totalCount");

    /**
     * Method used to query the database using query filters. The method query the database, generate the new facets
     * depending on the result, return items to be printed on the map and paginated on results list.
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
         * Aggregation pipeline to be executed to find the observation matching the query in "observationsLite"
         * collection.
         */
        List<AggregationOperation> aggregationOperations = setMatchOperationUsingFilters(queryElements);

        /**
         * The aggregation pipeline is executed to obtain the list of ObservationLiteDocument corresponding to the
         * query. The following result is Paginated and stored into the ResponseDocument object
         */
        AggregationOptions options = AggregationOptions.builder().allowDiskUse(true).build();
        responseDocument.setObservationDocumentLitePage(getObservationsPage(aggregationOperations, PageRequest.of(0, 10)));

        /**
         * Add Unwind and project aggregation operation to the aggregation pipeline in order to return all the
         * observationId matching the query
         */
        UnwindOperation u1 = Aggregation.unwind("observations");
        ProjectionOperation p1 = Aggregation.project().and("observations.observationId").as("observationId").andExclude("_id");
        aggregationOperations.add(u1);
        aggregationOperations.add(p1);

        /**
         * Get the list of the observation ids that are resulting the query.
         */
        //Get the list of "observationId" of ObservationLite collection
        List<String> observationLiteIds = new ArrayList();
        mongoTemplate.aggregate(Aggregation.newAggregation(aggregationOperations)
                .withOptions(options), "observationsLite", Document.class).getMappedResults().forEach((t) -> {
            observationLiteIds.add(t.get("observationId").toString());
        });

        /**
         * Remove the two last aggregation operation that are not used for the following operations.
         */
        aggregationOperations.remove(u1);
        aggregationOperations.remove(p1);

        /**
         * From the list of ids resulting the query on "observationLite" collection, the list of station mesuring the
         * observtions is queried from "mapItems" collection. The document of the collection "mapItems" that have a
         * field "doucmentIds" containing at least one element of the "documentIdsFromObservationLite" Set object are
         * queried.
         */
        List<MapItem> mapItems = mongoTemplate.find(Query.query(Criteria.where("observationIds").in(observationLiteIds)), MapItem.class, "mapItems");
        mapItems.forEach(item -> {
            /**
             * For each document queried from the "mapItems" collection, the ids from the field documentIds that are not
             * present in "documentIdsFromObservationLite" Set object are removed.
             */
            Set<String> observationIdsFromMapItems = new HashSet<>(item.getObservationIds());
            observationIdsFromMapItems.retainAll(observationLiteIds);
            item.setObservationIds(new ArrayList<>(observationIdsFromMapItems));
        });

        /**
         * Store the mapItems and the facets in the ResponseDocument
         */
        responseDocument.setMapItems(mapItems);
        responseDocument.setFacetClassification(setFacetClassification(facetOperation, aggregationOperations));;
        return responseDocument;
    }

    /**
     * Calculate the facet for a given set of filters defined by user.
     *
     * @param facetOperation FacetOperation aggregation operation to be executed to calculate the facets
     * @param aggregationOperations The aggregation pipeline generated for the set of filter defined by the user
     * @return FacetClassification object corresponding to the filters
     */
    private FacetClassification setFacetClassification(FacetOperation facetOperation, List<AggregationOperation> aggregationOperations) {
        /**
         * Add the Facet aggregation operation to the pipeline to generate the relative facet. The following result is
         * stored into the ResponseDocument object
         */
        aggregationOperations.add(facetOperation);
        AggregationOptions options = AggregationOptions.builder().allowDiskUse(true).build();
        FacetClassificationTmp facetClassificationTmp = mongoTemplate.aggregate(Aggregation.newAggregation(aggregationOperations).withOptions(options), "observationsLite", FacetClassificationTmp.class)
                .getUniqueMappedResult();

        /**
         * Post-processing of the FacetClassificationTmp class before returning to the front end client.
         */
        /**
         * 1 - Build the category tree 2 - Build the collection of Theia Variable
         */
        List<TheiaCategoryTree> categoryTrees = new ArrayList<>();
        Set<TheiaVariable> theiaVariablesTmp = new HashSet<>();
        facetClassificationTmp.getTheiaCategorieFacetElements().stream().filter((t) -> {
            return t.getBroaders().contains("https://w3id.org/ozcar-theia/variableCategories"); //To change body of generated lambdas, choose Tools | Templates.
        }).forEach((t) -> {
            /**
             * Recursivly build the category tree
             */
            categoryTrees.add(TheiaCategoryTree.withNarrowers(t.getUri(), t.getPrefLabel(), populateNarrowers(t.getNarrowers(), facetClassificationTmp.getTheiaCategorieFacetElements()), t.getCount()));
            /**
             * Build the list of Theia variable
             */
            theiaVariablesTmp.addAll(t.getTheiaVariables());
        });
        //Sort in alphabetical order
        List<TheiaVariable> theiaVariables = theiaVariablesTmp.stream().sorted((object1, object2) -> object1.getUri().compareTo(object2.getUri())).collect(Collectors.toList());
        Collections.sort(categoryTrees, Comparator.comparing(TheiaCategoryTree::getUri));
        return new FacetClassification(
                theiaVariables,
                categoryTrees,
                facetClassificationTmp.getFundingNamesFacet(),
                facetClassificationTmp.getClimatesFacet(),
                facetClassificationTmp.getGeologiesFacet(),
                facetClassificationTmp.getProducerNamesFacet(),
                facetClassificationTmp.getTotalCount());
    }

    private Set<TheiaCategoryTree> populateNarrowers(List<String> uriNarrowers, List<TheiaCategoryFacetElement> facetElements) {
        //List to return
        Set<TheiaCategoryTree> narrowers = new HashSet<>();

        //For each uri of the list uriNarrowers, a new TheiaCategoryTree object is added to the narrowers list.
        uriNarrowers.forEach(uri -> {
            TheiaCategoryFacetElement facetElement = facetElements.stream().filter((t) -> {
                return t.getUri().equals(uri);
            }).findFirst().orElse(null);

            //If the narrowers uri is not present in the facetElements collection, nothing happens
            if (facetElement != null) {

                //The limit case: the FacetElement object found does not have narrowers attribute
                if (facetElement.getNarrowers() == null) {
                    narrowers.add(TheiaCategoryTree.withTheiaVariables(
                            facetElement.getUri(),
                            facetElement.getPrefLabel(),
                            new HashSet(facetElement.getTheiaVariables()),
                            facetElement.getCount()));
                } //The recursive case: the FacetElement object found does have narrowers attribute, populateNarrowers methods
                // is recursivly used
                else {
                    if (facetElement.getNarrowers().size() > 0) {
                        narrowers.add(TheiaCategoryTree.withNarrowers(
                                facetElement.getUri(),
                                facetElement.getPrefLabel(),
                                populateNarrowers(facetElement.getNarrowers(), facetElements),
                                facetElement.getCount()));
                    }
                }
            }
        });
        return narrowers;
    }

    /**
     * Method to calculate the facet from the whole "observationLite" collection
     *
     * @return FacetClassification object of the entire "observationLite" collection
     */
    @Override
    public FacetClassification initFacets() {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        return setFacetClassification(facetOperation, aggregationOperations);
    }

    /**
     * Query the observationLiteDocument to be printed on a given result page in the user interface. This method is used
     * when user is changing the page of the paginated result or when the first page of results is generated.
     *
     *
     * @param aggregationOperations List of MatchOperation defined filters form user interface
     * @param pageable Pageable object containing the number and the length of the page to be returned
     * @return Page object containing the results
     */
    @Override
    public Page<ObservationDocumentLite> getObservationsPage(List<AggregationOperation> aggregationOperations, Pageable pageable) {
        //Add aggregationOperation to pipeline to set pagination
        List<AggregationOperation> aggregationOperationsPage = new ArrayList(aggregationOperations);
        aggregationOperationsPage.add(Aggregation.sort(Sort.by(Sort.Order.desc("textScore"))).and(Sort.by(Sort.Order.asc("theiaVariableEn"))));
        aggregationOperationsPage.add(skip((long) pageable.getPageNumber() * pageable.getPageSize()));
        aggregationOperationsPage.add(limit(pageable.getPageSize()));
        AggregationOptions options = AggregationOptions.builder().allowDiskUse(true).build();
        List<ObservationDocumentLite> result = mongoTemplate.aggregate(Aggregation.newAggregation(aggregationOperationsPage)
                .withOptions(options), "observationsLite", ObservationDocumentLite.class).getMappedResults();
        return new PageImpl<>(result, pageable, result.size());
    }

    /**
     * Method used to parse the String containing the filters into aggregation operation
     *
     * @param queryElements String that can be parsed into json object.
     * @return List of AggregationOperation composed of MatchOperation of each filter of the queryElements parameter
     */
    public static List<AggregationOperation> setMatchOperationUsingFilters(String queryElements) {
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
            aggregationOperations.add(new GenericAggregationOperation("$addFields", "{ \"textScore\": { \"$meta\": \"textScore\" }}"));
        }
        /**
         * -------------------------------------------------------------------------------------------------------------
         * Match operation at the observation level. In "observationsLite" collection observation level is grouped by
         * variable at a given location. Hence it is needed to unwind the "observations" fields before to perform the
         * match operation and to group the documents back to the initial form after the match operation on
         * "observations" level
         */
        /**
         * 1 - unwind stage Need to unwind "observations" array fields of ObservationsLite collection before to perform
         * the Match operation using the defined temporal extents. This allow to filter grouped observation that does
         * not match the defined temporal extents
         */
        UnwindOperation u1 = unwind("observations");
        aggregationOperations.add(u1);

        /**
         * Add the fileds TheiaVariableEn after the unwind operation. This field will be used to sort the result
         * document by alphabetical order at the end of the aggregation operation.
         */
        aggregationOperations.add(new GenericAggregationOperation("$addFields", "{ \"theiaVariableEn\" : { \"$filter\" : { \"input\" : \"$observations.observedProperty.theiaVariable.prefLabel\" , \"as\" : \"var\" , \"cond\" : { \"$eq\":[\"$$var.lang\", \"en\"]}}}}"));

        /**
         * 2 - MatchOperations stage Match operation for the temporal extent parameters Document will not be returned
         * only if the temporal extent queried is outside the temporal extent of the document
         */
        /**
         * 2 - a ) Temporal extent match operation
         */
        if (jsonQueryElement.getJSONArray("temporalExtents").length() > 0) {

            List<Criteria> temporalExtentCriterias = new ArrayList<>();
            jsonQueryElement.getJSONArray("temporalExtents").forEach(item -> {
                JSONObject tmpExtent = (JSONObject) item;
                Instant from = Instant.parse(tmpExtent.getString("fromDate"));
                Instant to = Instant.parse(tmpExtent.getString("toDate"));
                temporalExtentCriterias.add(
                        new Criteria().orOperator(
                                new Criteria().andOperator(
                                        Criteria.where("observations.temporalExtent.dateBeg")
                                                .gte(from)
                                                .lte(to),
                                        Criteria.where("observations.temporalExtent.dateEnd")
                                                .gte(from)
                                                .lte(to)
                                ),
                                new Criteria().andOperator(
                                        Criteria.where("observations.temporalExtent.dateBeg")
                                                .lte(from)
                                                .lte(to),
                                        Criteria.where("observations.temporalExtent.dateEnd")
                                                .gte(from)
                                                .gte(to)
                                ),
                                new Criteria().andOperator(
                                        Criteria.where("observations.temporalExtent.dateBeg")
                                                .lte(from)
                                                .lte(to),
                                        Criteria.where("observations.temporalExtent.dateEnd")
                                                .gte(from)
                                                .lte(to)
                                ),
                                new Criteria().andOperator(
                                        Criteria.where("observations.temporalExtent.dateBeg")
                                                .gte(from)
                                                .lte(to),
                                        Criteria.where("observations.temporalExtent.dateEnd")
                                                .gte(from)
                                                .gte(to)
                                )
                        )
                );
            });

            aggregationOperations.add(Aggregation.match(new Criteria().orOperator(temporalExtentCriterias.toArray(new Criteria[temporalExtentCriterias.size()]))));

        }

        /**
         * 2 - b ) Theia categories match operation
         */
        if (jsonQueryElement.getJSONArray("theiaCategories").length() > 0) {

            List<Criteria> theiaCategoriesCriterias = new ArrayList<>();
            jsonQueryElement.getJSONArray("theiaCategories").forEach(item -> {
                String tmpCategory = (String) item;
                theiaCategoriesCriterias.add(
                        //                        Criteria.where("observations.observedProperty.theiaCategories").elemMatch(new Criteria().is(item))
                        Criteria.where("observations.observedProperty.theiaCategories").is(item)
                );
            });
            aggregationOperations.add(Aggregation.match(new Criteria().orOperator(theiaCategoriesCriterias.toArray(new Criteria[theiaCategoriesCriterias.size()]))));
        }

        /**
         * 2 - c ) Theia variable match operations
         */
        if (jsonQueryElement.getJSONArray("theiaVariables").length() > 0) {

            List<Criteria> theiaVariableCriterias = new ArrayList<>();
            jsonQueryElement.getJSONArray("theiaVariables").forEach(item -> {
                String tmpVariable = (String) item;

                theiaVariableCriterias.add(
                        Criteria.where("observations.observedProperty.theiaVariable.uri").is(item)
                );
            });
            aggregationOperations.add(Aggregation.match(new Criteria().orOperator(theiaVariableCriterias.toArray(new Criteria[theiaVariableCriterias.size()]))));
        }

        /**
         * 2 - d ) observation spatial extent match operations
         */
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
                        Criteria.where("observations.featureOfInterest.samplingFeature.geometry").within(new GeoJsonPolygon(points)));
            });
            aggregationOperations.add(Aggregation.match(new Criteria().orOperator(spatialExtentCriterias.toArray(new Criteria[spatialExtentCriterias.size()]))));
        }
        /**
         * 3 - Group Operation stage GroupOperation to return the document according to the initial form (before unwind
         * of "observations")
         */
        GroupOperation g1 = group("_id")
                .push("observations").as("observations")
                .first("producer").as("producer")
                .first("dataset").as("dataset")
                .first("textScore").as("textScore")
                .first("theiaVariableEn").as("theiaVariableEn");
        aggregationOperations.add(g1);

        /**
         * Match operation at the dataset or producer level -------------------------------------------------
         */
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
     * Method used to query the popup content depending of the document ids parameter. Popup need the producer name, the
     * station name and the variable names of the observation at the given sampling feature.
     *
     * @param ids String that can be parsed inot JSON object containing and array of documentIds
     * @return PopupContent object
     */
    @Override
    public PopupContent loadPopupContent(List<String> ids) {
        Set<String> observationIdsFromMarker = new HashSet<>();
        ids.forEach(item1 -> {
            observationIdsFromMarker.add(item1.toString());
        });
        //Query the "observationsLite" collection using the newly created documentIds Set object
        List<PopupDocument> result = mongoTemplate.aggregate(Aggregation.newAggregation(
                match(Criteria.where("observations.observationId").in(observationIdsFromMarker))), "observationsLite", PopupDocument.class).getMappedResults();

        //Set the PopupContent Object to be returned
        PopupContent popupContent = new PopupContent();
        popupContent.setProducerName(result.get(0).getProducer().getName());
        //Condition on station name since it is not a mandatory field
        if (!result.get(0).getObservations().get(0).getFeatureOfInterest().getSamplingFeature().getName().isEmpty()) {
            popupContent.setStationName(result.get(0).getObservations().get(0).getFeatureOfInterest().getSamplingFeature().getName());
        }
        /**
         * List of variable name and Ids to print in the popup window
         */
        List<PopupContent.VariableNameAndId> variableNameAndIds = new ArrayList<>();

        result.forEach(item -> {
            PopupContent.VariableNameAndId nameAndId = new PopupContent.VariableNameAndId();
            /**
             * Lists of Ids pointed by a given variable name
             */
            List<String> observationIdsFromObservationsLite = new ArrayList();
            List<List<I18n>> producerVariableNamesFromObservationsLite = new ArrayList();
            /**
             * Theia variable name if it exists
             */
            if (item.getObservations().get(0).getObservedProperty().getTheiaVariable() != null) {
                nameAndId.setTheiaVariableName(item.getObservations().get(0).getObservedProperty().getTheiaVariable().getPrefLabel());
            }

            item.getObservations().forEach((t) -> {
                //List to store all the observationId of one document from observationsLite collection
                observationIdsFromObservationsLite.add(t.getObservationId());

                //List to store all the producerVariableName of one document from observationsLite collection if theia variable
                // is not defined
                producerVariableNamesFromObservationsLite.add(t.getObservedProperty().getName());
            });
            nameAndId.setIds(observationIdsFromObservationsLite);
            nameAndId.setProducerVariableNames(producerVariableNamesFromObservationsLite);
            variableNameAndIds.add(nameAndId);
        });

        popupContent.setVariableNameAndIds(variableNameAndIds);
        return popupContent;
    }

    /**
     * Get all the variables measured at the location of a station. The location can either be a point or a bbox.
     *
     * @param coordinatesString String representation of the Json Array contenaining the coordinantes of the geojson
     * object representing the location to be queried
     * @return A list of TheiaVariable
     */
    @Override
    public List<TheiaVariable> getVariablesAtOneLocation(String coordinatesString) {
        /**
         * Parse the String representation of the Json array into a JSONArray
         */
        JSONArray coordinates = new JSONArray(coordinatesString);

        /**
         * Get the list of point of the coordinates if the location in order to calculate the BBOX containg the
         * observation object
         */
        List<Number[]> latLngs = new ArrayList<>();
        getPointRecursivly(coordinates, latLngs);

        /**
         * Calculate the BBOX
         */
        List<Double> lat = new ArrayList<>();
        List<Double> lon = new ArrayList<>();

        latLngs.forEach(item -> {
            lon.add(item[0].doubleValue());
            lat.add(item[1].doubleValue());
        });
        Double minLong = lon.stream().min(Comparator.comparing(Double::valueOf)).get();
        Double maxLong = lon.stream().max(Comparator.comparing(Double::valueOf)).get();
        Double minLat = lat.stream().min(Comparator.comparing(Double::valueOf)).get();
        Double maxLat = lat.stream().max(Comparator.comparing(Double::valueOf)).get();

        /**
         * Create the aggregation pipeline used to query the TheiaVariable at a given location. If the location is a
         * Point or a BBOX, different MatchOperation are generated.
         */
        MatchOperation m1;
        Criteria andCriteria = new Criteria();
        if (Objects.equals(minLong, maxLong) && Objects.equals(minLat, maxLat)) {
            Point bottomLeft = new Point(minLong, minLat);
            Point upperRight = new Point(maxLong, maxLat);
            andCriteria.andOperator(Criteria.where("observations.0.observedProperty.theiaVariable").exists(true), Criteria.where("observations.0.featureOfInterest.samplingFeature.geometry").within(new Box(bottomLeft, upperRight)));
            m1 = Aggregation.match(andCriteria);
        } else {
            andCriteria.andOperator(Criteria.where("observations.0.observedProperty.theiaVariable").exists(true), Criteria.where("observations.0.featureOfInterest.samplingFeature.geometry").is(new Point(minLong, minLat)));
            m1 = Aggregation.match(andCriteria);
        }
        ProjectionOperation p1 = Aggregation.project().and("observations.observedProperty.theiaVariable").as("theiaVariable");
        ReplaceRootOperation rp1 = Aggregation.replaceRoot().withValueOf(ArrayOperators.ArrayElemAt.arrayOf("theiaVariable").elementAt(0));
        return mongoTemplate.aggregate(Aggregation.newAggregation(m1, p1, rp1), "observationsLite", TheiaVariable.class).getMappedResults();
    }

    /**
     * Query a list of observation from the "observationsLite" collection using the datasetId
     *
     * @param datasetId datasetId to be queried
     * @return A list of Document. Each document is an list of ObservationLite object: {"observations":[ObservationLite,
     * ObservationLite, ObservationLite]}
     */
    @Override
    public List<Document> getObservationsOfADataset(String datasetId) {
        Criteria andCriteria = new Criteria();
        andCriteria.andOperator(Criteria.where("dataset.datasetId").is(datasetId));
        MatchOperation m1 = Aggregation.match(andCriteria);
        ProjectionOperation p1 = Aggregation.project().and("observations").as("observations");
        return mongoTemplate.aggregate(Aggregation.newAggregation(m1, p1), "observationsLite", Document.class).getMappedResults();
    }

    /**
     * Get the observationId from the "observationsLite" collection of the document corresponding to a TheiaVariable at
     * a given location.
     *
     * @param queryFilter String representation of a Json object containing the query parameter. ex:
     * {\"uri\":\"https://w3id.org/ozcar-theia/variables/organicCarbon\",\"coordinates\":[6.239739,47.04832,370]}
     * @return List of String corresponding to the ids queried
     */
    @Override
    public List<String> getObservationIdsOfOtherTheiaVariableAtLocation(String queryFilter) {
        /**
         * Parse the json string into a JSONObject
         */
        JSONObject queryFilterJson = new JSONObject(queryFilter);
        List<Number[]> latLngs = new ArrayList<>();
        /**
         * Store the point of the "coordinates" of the location into an array
         */
        getPointRecursivly(queryFilterJson.getJSONArray("coordinates"), latLngs);

        /**
         * Calculate the BBOX of the location
         */
        List<Double> lat = new ArrayList<>();
        List<Double> lon = new ArrayList<>();

        latLngs.forEach(item -> {
            lon.add(item[0].doubleValue());
            lat.add(item[1].doubleValue());
        });
        Double minLong = lon.stream().min(Comparator.comparing(Double::valueOf)).get();
        Double maxLong = lon.stream().max(Comparator.comparing(Double::valueOf)).get();
        Double minLat = lat.stream().min(Comparator.comparing(Double::valueOf)).get();
        Double maxLat = lat.stream().max(Comparator.comparing(Double::valueOf)).get();

        /**
         * Query the observation ids according to the BBOX or the Point of the location and using the uri of the Theia
         * Variable.
         */
        MatchOperation m1;
        Criteria andCriteria = new Criteria();
        if (Objects.equals(minLong, maxLong) && Objects.equals(minLat, maxLat)) {
            Point bottomLeft = new Point(minLong, minLat);
            Point upperRight = new Point(maxLong, maxLat);
            andCriteria.andOperator(Criteria.where("observations.0.observedProperty.theiaVariable.uri").is(queryFilterJson.getString("uri")),
                    Criteria.where("observations.0.featureOfInterest.samplingFeature.geometry").within(new Box(bottomLeft, upperRight)));
            m1 = Aggregation.match(andCriteria);
        } else {
            andCriteria.andOperator(Criteria.where("observations.0.observedProperty.theiaVariable.uri").is(queryFilterJson.getString("uri")),
                    Criteria.where("observations.0.featureOfInterest.samplingFeature.geometry").is(new Point(minLong, minLat)));
            m1 = Aggregation.match(andCriteria);
        }
        UnwindOperation u1 = Aggregation.unwind("observations");
        GroupOperation g1 = Aggregation.group().push("observations.observationId").as("observationId");
        ProjectionOperation p1 = Aggregation.project("observationId").andExclude("_id");
        Document doc = mongoTemplate.aggregate(Aggregation.newAggregation(m1, u1, g1, p1), "observationsLite", Document.class).getUniqueMappedResult();
        return doc.get("observationId", List.class);
    }

    /**
     * Recursive method used to store all value of a GeoJSON coordinantes fields into an array
     *
     * @param coordinates JSONArray representation of the 'coordinantes' fields of a GEoJSON object
     * @param latLngs The List that will be filled using all the position of the 'coordinates' field.
     */
    private void getPointRecursivly(JSONArray coordinates, List<Number[]> latLngs) {
        for (int i = 0; i < coordinates.length(); i++) {
            if (coordinates.optJSONArray(i) != null) {
                getPointRecursivly(coordinates.optJSONArray(i), latLngs);
            } else {
                List<Number> intList = (List<Number>) (List<?>) coordinates.toList();
                Number[] intArray = new Number[intList.size()];
                latLngs.add(intList.toArray(intArray));
            }
        }
    }
}
