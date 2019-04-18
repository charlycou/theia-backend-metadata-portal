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
public enum EnumContactPersonRoles {
    PRINCIPAL_INVESTIGATOR("Principal investigator"),
    PROJECT_LEADER("Project leader"),
    PROJECT_MEMBER("Project member"),
    DATA_MANAGER("Data manager"),
    DATA_COLLECTOR("Data collector");
    
    String enumContactPersonRoles;

    private EnumContactPersonRoles(String enumContactPersonRoles) {
        this.enumContactPersonRoles = enumContactPersonRoles;
    }

@Override
public String toString() {
    return this.enumContactPersonRoles;
}
    
}
