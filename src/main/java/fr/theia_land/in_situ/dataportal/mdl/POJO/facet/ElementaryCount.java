/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.facet;

import org.springframework.data.annotation.Id;

/**
 *
 * @author coussotc
 */
public class ElementaryCount {
    @Id
    private String name;
    private int count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
 
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
