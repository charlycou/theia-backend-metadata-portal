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
public enum EnumFundingTypes {
    ORGANISATION("Organisation"),
    FEDERATIVE_STRUCTURE("Federative structure"),
    RESEARCH_PROGRAM("Research program"),
    RESEARCH_UNIT("Research unit"),
    OTHER("Other");
    
    String enumFundingTypes;

    private EnumFundingTypes(String enumFunfingTypes) {
        this.enumFundingTypes = enumFunfingTypes;
    }
    
    @Override
    public String toString() {
        return this.enumFundingTypes;
    }
    
    
    public static EnumFundingTypes enumValueOf(String test) {
        EnumFundingTypes en = null;
        try {
            for (EnumFundingTypes e : EnumFundingTypes.values()) {
                if (e.name().equals(test)) {
                    en = e;
                }
            }
        } catch (EnumConstantNotPresentException e) {
            e.getMessage();
        }
        return en;
    }
}
