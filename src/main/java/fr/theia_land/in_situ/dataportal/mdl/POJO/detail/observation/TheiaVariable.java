/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation;

import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 *
 * @author coussotc
 */
public class TheiaVariable {
    private String uri;
    private List<I18n> prefLabel;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<I18n> getPrefLabel() {
        return prefLabel;
    }

    public void setPrefLabel(List<I18n> prefLabel) {
        this.prefLabel = prefLabel;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.uri).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TheiaVariable other = (TheiaVariable) obj;
        if (!Objects.equals(this.uri, other.uri)) {
            return false;
        }
        return true;
    }  
}
