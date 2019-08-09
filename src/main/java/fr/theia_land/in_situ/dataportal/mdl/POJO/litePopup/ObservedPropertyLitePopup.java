/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.litePopup;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.I18n;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.TheiaVariable;
import java.util.List;

/**
 *
 * @author coussotc
 */
public class ObservedPropertyLitePopup {

    /**
     * Name of the variable
     */
    private List<I18n> name;
    private TheiaVariable theiaVariable ;

    public TheiaVariable getTheiaVariable() {
        return theiaVariable;
    }

    public void setTheiaVariable(TheiaVariable theiaVariable) {
        this.theiaVariable = theiaVariable;
    }


    public List<I18n> getName() {
        return name;
    }

    public void setName(List<I18n> name) {
        this.name = name;
    }
    
    
    
}
