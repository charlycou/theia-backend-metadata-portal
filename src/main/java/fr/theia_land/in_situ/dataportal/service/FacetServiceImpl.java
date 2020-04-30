package fr.theia_land.in_situ.dataportal.service;

import fr.theia_land.in_situ.dataportal.model.POJO.detail.observation.I18n;
import fr.theia_land.in_situ.dataportal.model.POJO.detail.producer.Producer;
import fr.theia_land.in_situ.dataportal.model.POJO.facet.FacetClassification;
import fr.theia_land.in_situ.dataportal.model.POJO.facet.TheiaCategoryTree;
import fr.theia_land.in_situ.dataportal.repository.ObservationDocumentLiteRepository;
import fr.theia_land.in_situ.dataportal.repository.ObservationDocumentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FacetServiceImpl implements FacetService {
    private final ObservationDocumentLiteRepository observationDocumentLiteRepository;
    private final ObservationDocumentRepository observationDocumentRepository;

    public FacetServiceImpl(ObservationDocumentLiteRepository observationDocumentLiteRepository, ObservationDocumentRepository observationDocumentRepository) {
        this.observationDocumentLiteRepository = observationDocumentLiteRepository;
        this.observationDocumentRepository = observationDocumentRepository;
    }

    @Override
    public FacetClassification initFacets() {
        return this.observationDocumentLiteRepository.initFacets();
    }

    @Override
    public List<List<List<I18n>>> getCategoryHierarchies(List<String> payload) {
        List<TheiaCategoryTree> theiaCategoryTrees = this.observationDocumentRepository.getCategoryHierarchies(payload);
        List<List<List<I18n>>> categoriesHierarchies = new ArrayList();
        for (TheiaCategoryTree tct : theiaCategoryTrees) {
            categoriesHierarchies.addAll(setCategoryBranches(tct));
        }
        return categoriesHierarchies;
    }

    @Override
    public List<Producer> getProducerInfo() {
       return this.observationDocumentRepository.getProducersInfo();
    }


    private List<List<List<I18n>>> setCategoryBranches(TheiaCategoryTree tct) {
        List<List<List<I18n>>> resultList = new ArrayList();
        if (tct.getBroaders() != null && tct.getBroaders().size() > 0) {
            for (TheiaCategoryTree broader : tct.getBroaders()) {
                resultList.addAll(setCategoryBranches(broader));
            }
        }
        if (resultList.size() > 0) {
            List<List<List<I18n>>> tmpResultList = new ArrayList<>();
            for (List<List<I18n>> list : resultList) {
                list.add(tct.getPrefLabel());
                tmpResultList.add(list);
            }
            return tmpResultList;
        } else {
            List<List<I18n>> tmp = new ArrayList();
            tmp.add(tct.getPrefLabel());
            resultList.add(tmp);
            return resultList;
        }
    }
}
