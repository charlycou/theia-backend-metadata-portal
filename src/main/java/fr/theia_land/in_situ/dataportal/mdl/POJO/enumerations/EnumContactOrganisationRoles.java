/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.enumerations;


/**
 *
 * @author coussotc
 */
public enum EnumContactOrganisationRoles {
    RESEARCH_GROUP("Research group");
    
    String enumContactOrganisationRoles;

    private EnumContactOrganisationRoles(String enumContactOrganisationRoles) {
        this.enumContactOrganisationRoles = enumContactOrganisationRoles;
    }

@Override
public String toString() {
    return this.enumContactOrganisationRoles;
}
    
}
