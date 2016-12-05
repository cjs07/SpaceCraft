package com.deepwelldevelopment.spacecraft.api.facts;

import java.util.Collection;

public class FactHelper {

    public static FactList cullTags(FactList list) {
        FactList list2 = new FactList();
        for (Fact tag : list.getFacts()) {
            if (tag != null) {
                list2.add(tag, list.getAmount(tag));
            }
        }
        while (list2 != null && list.size() > 6) {
            Fact lowest = null;
            float low = 32767.0f;
            for (Fact tag : list2.getFacts()) {
                if (tag == null) {
                    continue;
                }
                float ta = list2.getAmount(tag);
                if (tag.isBasic()) {
                    ta *= 0.9f;
                } else {
                    if (!tag.getComponents()[0].isBasic()) {
                        ta *= 1.1f;
                        if (!tag.getComponents()[0].getComponents()[0].isBasic()) {
                            ta *= 1.05f;
                        }
                        if (!tag.getComponents()[0].getComponents()[1].isBasic()) {
                            ta *= 1.05f;
                        }
                    }
                    if (!tag.getComponents()[1].isBasic()) {
                        ta *= 1.1f;
                        if (!tag.getComponents()[0].getComponents()[0].isBasic()) {
                            ta *= 1.05f;
                        }
                        if (!tag.getComponents()[0].getComponents()[1].isBasic()) {
                            ta *= 1.05f;
                        }
                    }
                }
                if (ta >= low) {
                    continue;
                }
                low = ta;
                lowest = tag;
            }
            list2.facts.remove(lowest);
        }
        return list2;
    }

    public static Fact getCombinationresult(Fact fact1, Fact fact2) {
        Collection<Fact> facts = Fact.facts.values();
        for (Fact fact : facts) {
            if (fact.getComponents() == null || (fact.getComponents()[0] != fact1 || fact.getComponents()[1] != fact2) && (fact.getComponents()[0] != fact2 || fact.getComponents()[1] != fact1)) {
                continue;
            }
            return fact;
        }
        return null;
    }
}
