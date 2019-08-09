/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.I18n;
import java.util.List;

/**
 *  Class representing the data constraints for a given dataset
 * @author coussotc
 */
public class DataConstraint {
    /**
     * String mentionning the use constraint of a given dataset
     */
    private List<I18n> accessUseConstraint;
    /**
     * data policy of a given dataset 
     */
    private List<I18n> urlDataPolicy;
    /**
     * Licence of a given dataset 
     * @see Licence
     */
    private Licence licence;
    /**
     * embargo on the data of a given dataset
     * @see Embargo
     */
    private Embargo embargo;

    public List<I18n> getAccessUseConstraint() {
        return accessUseConstraint;
    }

    public void setAccessUseConstraint(List<I18n> accessUseConstraint) {
        this.accessUseConstraint = accessUseConstraint;
    }

    public List<I18n> getUrlDataPolicy() {
        return urlDataPolicy;
    }

    public void setUrlDataPolicy(List<I18n> urlDataPolicy) {
        this.urlDataPolicy = urlDataPolicy;
    }

    public Licence getLicence() {
        return licence;
    }

    public void setLicence(Licence licence) {
        this.licence = licence;
    }

    public Embargo getEmbargo() {
        return embargo;
    }

    public void setEmbargo(Embargo embargo) {
        this.embargo = embargo;
    }

}
