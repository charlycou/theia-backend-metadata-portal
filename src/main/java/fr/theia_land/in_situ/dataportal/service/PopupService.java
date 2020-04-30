package fr.theia_land.in_situ.dataportal.service;

import fr.theia_land.in_situ.dataportal.model.POJO.popup.litePopup.PopupContent;

import java.util.List;


public interface PopupService {
    public PopupContent getPopupContent(List<String> payload);
}
