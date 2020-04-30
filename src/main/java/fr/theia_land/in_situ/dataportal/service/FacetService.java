package fr.theia_land.in_situ.dataportal.service;

import fr.theia_land.in_situ.dataportal.model.POJO.detail.observation.I18n;
import fr.theia_land.in_situ.dataportal.model.POJO.detail.producer.Producer;
import fr.theia_land.in_situ.dataportal.model.POJO.facet.FacetClassification;

import java.util.List;

public interface FacetService {
    public FacetClassification initFacets();
    public List<List<List<I18n>>> getCategoryHierarchies(List<String> payload);
    List<Producer> getProducerInfo();
}
