/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset.TemporalExtent;
import java.util.List;

/**
 *
 * @author coussotc
 */
public abstract class Sensor {

    private List<TemporalExtent> activityPeriods;

    public List<TemporalExtent> getActivityPeriods() {
        return activityPeriods;
    }

    public void setActivityPeriods(List<TemporalExtent> activityPeriods) {
        this.activityPeriods = activityPeriods;
    }
    
}
