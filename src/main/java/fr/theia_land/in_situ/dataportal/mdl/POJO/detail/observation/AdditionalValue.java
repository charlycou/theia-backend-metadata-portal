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
public class AdditionalValue {

    /**
     * additional value attributes
     */
    private List<I18n> name, columnName, unit, description;

    public List<I18n> getName() {
        return name;
    }

    public void setName(List<I18n> name) {
        this.name = name;
    }

    public List<I18n> getColumnName() {
        return columnName;
    }

    public void setColumnName(List<I18n> columnName) {
        this.columnName = columnName;
    }

    public List<I18n> getUnit() {
        return unit;
    }

    public void setUnit(List<I18n> unit) {
        this.unit = unit;
    }

    public List<I18n> getDescription() {
        return description;
    }

    public void setDescription(List<I18n> description) {
        this.description = description;
    }

}
