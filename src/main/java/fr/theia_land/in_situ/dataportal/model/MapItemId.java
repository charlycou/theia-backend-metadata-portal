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

    private List<String> documentId = new ArrayList<>();

    public MapItemId() {
    }

    public void setDocumentId(List<String> documentId) {
        this.documentId = documentId;
    }

    public List<String> getDocumentId() {
        return documentId;
    }

}
