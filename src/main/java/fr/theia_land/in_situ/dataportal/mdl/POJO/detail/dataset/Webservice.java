/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.I18n;
import java.util.List;

/**
 * Class representig a webservice for a dataset
 * @author coussotc
 */
public class Webservice {
    /**
     * Description of the service name
     */
    private List<I18n> description;
    /**
     * url of the service
     */
    private List<I18n> url;

    public List<I18n> getDescription() {
        return description;
    }

    public void setDescription(List<I18n> description) {
        this.description = description;
    }

    public List<I18n> getUrl() {
        return url;
    }

    public void setUrl(List<I18n> url) {
        this.url = url;
    }
    
    
}
