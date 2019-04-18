/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.enumerations;


/**
 * Enumeration of climate possibilities for PortalSearchCriteria Object
 * @see PortalSearchCriteria
 * @author coussotc
 */
public enum EnumClimates {
    OCEANIC("Oceanic climate"),
    TROPICAL("Tropical climate"),
    MEDITERRANEAN("Mediterranean climate"),
    MOUNTAIN("Mountain climate"),
    CONTINENTAL("Continental climate"),
    POLAR("Polar climate"),
    ARID("Arid climate"),
    EQUATORIAL("Equatorial climate");
    
    private String enumClimate; 
   /**
    * Climate constructor
    * @param enumClimate String
    */
    private EnumClimates(String enumClimate) {
        this.enumClimate = enumClimate;
    }
    
    /**
     * String value of Enumeration
     * @return vlaue of the enumeration
     */
    @Override
    public String toString() {
        return this.enumClimate;
    }
    
    /**
     * Method to instanciate a EnumClimate value if the parameter corresponds to one of the enumeration
     * @param test a string that must correspond or not to a vlaue of the enumeration
     * @return Enumclimate value
     */
    public static EnumClimates enumValueOf(String test) {
        EnumClimates en = null;
        try {
            for (EnumClimates e : EnumClimates.values()) {
                if (e.name().equals(test)) {
                    en = e;
                }
            }
        }
        catch (EnumConstantNotPresentException e) {
            e.getMessage();
        }
        return en;
    }
}
