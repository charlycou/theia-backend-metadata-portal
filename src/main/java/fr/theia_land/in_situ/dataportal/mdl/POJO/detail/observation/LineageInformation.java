/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.I18n;
import java.util.Date;
import java.util.List;

/**
 *
 * @author coussotc
 */
public class LineageInformation {
    /**
     * Description of the process applied to the observation
     */
    private List<I18n> processDescription;
    /**
     * Date when the process has been applid to the observation
     */
    private Date processDate;

    public List<I18n> getProcessDescription() {
        return processDescription;
    }

    public void setProcessDescription(List<I18n> processDescription) {
        this.processDescription = processDescription;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }
    
    
}
