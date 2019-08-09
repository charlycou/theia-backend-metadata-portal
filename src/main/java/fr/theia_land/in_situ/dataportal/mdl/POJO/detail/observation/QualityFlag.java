/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation;

import java.util.List;

/**
 *
 * @author coussotc
 */
public class QualityFlag {
    
    private List<I18n> code, description;

    public List<I18n> getCode() {
        return code;
    }

    public void setCode(List<I18n> code) {
        this.code = code;
    }

    public List<I18n> getDescription() {
        return description;
    }

    public void setDescription(List<I18n> description) {
        this.description = description;
    }

    
}
