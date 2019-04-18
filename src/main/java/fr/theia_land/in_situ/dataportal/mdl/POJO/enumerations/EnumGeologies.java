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
public enum EnumGeologies {
    VOLCANIC("Volcanic rocks"),
    PLUTONIC("Plutonic rocks"),
    METAMORPHIC("Metamorphic rocks"),
    CARBONATES("Carbonate rocks"),
    QUATERNARY("Quartenary soils"),
    OTHER_SEDIMENTARY_ROCKS("Other sedimentary rocks");

    private String enumGeologies;

    private EnumGeologies(String enumGeologies) {
        this.enumGeologies = enumGeologies;
    }

    @Override
    public String toString() {
        return this.enumGeologies;
    }
    
    /**
     * Check if a String is a value of the Enumeration and return the value of the enumration if true
     * @param test String to test
     * @return value of the enumeration
     */
    public static EnumGeologies enumValueOf(String test) {
        EnumGeologies en = null;
        try {
            for (EnumGeologies e : EnumGeologies.values()) {
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
