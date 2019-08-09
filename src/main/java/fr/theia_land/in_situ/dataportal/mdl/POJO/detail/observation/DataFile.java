/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation;

import java.util.List;

/**
 * A file containing Result
 * @author coussotc
 */
public class DataFile {
    /**
     * The name of the file with the extension
     * ex: temperature_time_serie.csv
     */
    private List<I18n> name;

    public List<I18n> getName() {
        return name;
    }

    public void setName(List<I18n> name) {
        this.name = name;
    }
    

}
