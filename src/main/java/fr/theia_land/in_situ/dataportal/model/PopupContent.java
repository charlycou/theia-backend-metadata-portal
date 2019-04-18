/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.model;

import fr.theia_land.in_situ.dataportal.mdl.POJO.I18n;
import java.util.List;

/**
 *
 * @author coussotc
 */
public class PopupContent {
    
    public static class VariableNameAndId {
        private List<I18n> variableName;
        private List<String> ids;

        public List<I18n> getVariableName() {
            return variableName;
        }

        public void setVariableName(List<I18n> variableName) {
            this.variableName = variableName;
        }

        public List<String> getIds() {
            return ids;
        }

        public void setIds(List<String> ids) {
            this.ids = ids;
        } 
    }
    
    private List<I18n> producerName;
    private  List<I18n> stationName;
    List<VariableNameAndId> variableNameAndIds;

    public List<I18n> getProducerName() {
        return producerName;
    }

    public void setProducerName(List<I18n> producerName) {
        this.producerName = producerName;
    }

    public List<I18n> getStationName() {
        return stationName;
    }

    public void setStationName(List<I18n> stationName) {
        this.stationName = stationName;
    }

    public List<VariableNameAndId> getVariableNameAndIds() {
        return variableNameAndIds;
    }

    public void setVariableNameAndIds(List<VariableNameAndId> variableNameAndIds) {
        this.variableNameAndIds = variableNameAndIds;
    }
}
