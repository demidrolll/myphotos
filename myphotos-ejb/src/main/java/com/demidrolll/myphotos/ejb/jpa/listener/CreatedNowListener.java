package com.demidrolll.myphotos.ejb.jpa.listener;

import com.demidrolll.myphotos.model.domain.AbstractDomain;

import javax.persistence.PrePersist;
import java.util.Date;

public class CreatedNowListener {

    @PrePersist
    public void setNow(AbstractDomain model) {
        model.setCreated(new Date());
    }
}
