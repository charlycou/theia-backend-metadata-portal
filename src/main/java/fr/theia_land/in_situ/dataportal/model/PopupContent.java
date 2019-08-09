/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.model;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.I18n;
import java.util.List;

/**
 *
 * @author coussotc
 */
public class PopupContent {
    
    public static class VariableNameAndId {
        private List<I18n> theiaVariableName;
        private List<List<I18n>> producerVariableNames;

        public List<List<I18n>> getProducerVariableNames() {
            return producerVariableNames;
        }

        public void setProducerVariableNames(List<List<I18n>> producerVariableNames) {
            this.producerVariableNames = producerVariableNames;
        }
        private List<String> ids;

        public List<I18n> getTheiaVariableName() {
            return theiaVariableName;
        }

        public void setTheiaVariableName(List<I18n> theiaVariableName) {
            this.theiaVariableName = theiaVariableName;
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
