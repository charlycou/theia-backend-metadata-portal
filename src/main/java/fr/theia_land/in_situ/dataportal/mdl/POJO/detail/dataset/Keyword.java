/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.I18n;
import java.util.List;

/**
 * Class representing keyword of the dataset
 * @author coussotc
 */
public class Keyword {
    /**
     * The keyword
     */
    private List<I18n> keyword;
    /**
     * URI of the keyword in a published thesaurus
     */
    private String uri;

    public List<I18n> getKeyword() {
        return keyword;
    }

    public void setKeyword(List<I18n> keyword) {
        this.keyword = keyword;
    }



    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }    
}
