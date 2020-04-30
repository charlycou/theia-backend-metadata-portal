package fr.theia_land.in_situ.dataportal.service;

import fr.theia_land.in_situ.dataportal.model.POJO.popup.litePopup.PopupContent;
import fr.theia_land.in_situ.dataportal.repository.ObservationDocumentLiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PopupServiceImpl implements PopupService {
    private final ObservationDocumentLiteRepository observationDocumentLiteRepository;

    @Autowired
    public PopupServiceImpl(ObservationDocumentLiteRepository observationDocumentLiteRepository) {
        this.observationDocumentLiteRepository = observationDocumentLiteRepository;
    }

    @Override
    public PopupContent getPopupContent(List<String> payload) {
        return this.observationDocumentLiteRepository.loadPopupContent(payload);
    }
}
