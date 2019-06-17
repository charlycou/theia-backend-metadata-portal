/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author coussotc
 */
public class MapItemId {

    private List<String> documentIds = new ArrayList<>();

    public MapItemId() {
    }

    public void setDocumentIds(List<String> documentIds) {
        this.documentIds = documentIds;
    }

    public List<String> getDocumentIds() {
        return documentIds;
    }

}
