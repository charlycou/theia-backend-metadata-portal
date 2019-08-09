/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.I18n;
import java.util.List;


/**
 *
 * @author coussotc
 */
public class Organisation extends Contact {
    private List<I18n> name;
    private String acronym;
    private String role;

//    public Organisation(I18n name, String acronym, String role) {
//        this.name = name;
//        this.acronym = acronym;
//        this.role = role;
//    }

    public Organisation() {
    }

    public List<I18n> getName() {
        return name;
    }

    public void setName(List<I18n> name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return this.acronym + this.role;
    }
}
