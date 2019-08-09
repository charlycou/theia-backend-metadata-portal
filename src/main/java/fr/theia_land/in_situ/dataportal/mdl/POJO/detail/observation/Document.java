/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation;


import fr.theia_land.in_situ.dataportal.mdl.POJO.enumerations.EnumDocumentTypes;
import java.util.List;

/**
 * Class representing the documents associated to the dataset
 * @author coussotc
 */
public class Document {
    /**
     * The type of the document accordiong to the EnumDocumentTypes
     * @see EnumDocumentTypes
     */
    public String type;
    /**
     * Ordered list of the document of  given type for the dataset
     * @see Document#type
     */
    public List<I18n> url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<I18n> getUrl() {
        return url;
    }

    public void setUrl(List<I18n> url) {
        this.url = url;
    }

    
}
