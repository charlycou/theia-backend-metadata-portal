/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.model.POJO.popup.litePopup;

import fr.theia_land.in_situ.dataportal.model.POJO.detail.observation.I18n;

import java.util.List;

/**
 *
 * @author coussotc
 */
public class ProducerLitePopup {
    /**
     * Acronym of the Producer. ex AMMA-CATCH
     */
    private List<I18n> name;
   

    public List<I18n> getName() {
        return name;
    }

    public void setName(List<I18n> name) {
        this.name = name;
    }
    
}
