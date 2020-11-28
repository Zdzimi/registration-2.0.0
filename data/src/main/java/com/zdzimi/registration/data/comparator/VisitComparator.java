package com.zdzimi.registration.data.comparator;

import com.zdzimi.registration.data.entity.VisitEntity;

import java.util.Comparator;

public class VisitComparator implements Comparator<VisitEntity> {

    @Override
    public int compare(VisitEntity o1, VisitEntity o2) {
        return o1.getVisitStart().compareTo(o2.getVisitStart());
    }
}
