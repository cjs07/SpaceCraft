package com.deepwelldevelopment.spacecraft.api.facts;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class FactList implements Serializable {
    public LinkedHashMap<Fact, Integer> facts = new LinkedHashMap<Fact, Integer>();

    public FactList() {
    }

    public FactList copy() {
        FactList out = new FactList();
        for (Fact f : this.getFacts()) {
            out.add(f, this.getAmount(f));
        }
        return out;
    }

    public int size() {
        return this.facts.size();
    }

    public int studySize() {
        int i = 0;
        for (Fact f : this.facts.keySet()) {
            i += this.getAmount(f);
        }
        return i;
    }

    public Fact[] getFacts() {
        return this.facts.keySet().toArray(new Fact[0]);
    }

    public Fact[] getFactsSortedByName() {
        try {
            Fact[] out = this.facts.keySet().toArray(new Fact[0]);
            boolean change = false;
            block:
            do {
                change = false;
                for (int i = 0; i < out.length; i++) {
                    Fact f1 = out[i];
                    Fact f2 = out[i + 1];
                    if (f1 == null || f2 == null || f1.getTag().compareTo(f2.getTag()) <= 0) {
                        continue;
                    }
                    out[i] = f2;
                    out[i + 1] = f1;
                    change = true;
                    continue block;
                }
            } while (change);
            return out;
        } catch (Exception e) {
            return this.getFacts();
        }
    }

    public Fact[] getFactsSortedByAmount() {
        try {
            Fact[] out = this.facts.keySet().toArray(new Fact[0]);
            boolean change = false;
            block:
            do {
                change = false;
                for (int i = 0; i < out.length; i++) {
                    Fact f;
                    int i1 = this.getAmount(out[i]);
                    int i2 = this.getAmount(out[i + 1]);
                    if (i1 <= 0 || i2 <= 0 || i2 <= i1) {
                        continue;
                    }
                    Fact f2 = out[i];
                    out[i] = f = out[i + 1];
                    out[i + 1] = f2;
                    change = true;
                    continue block;
                }
            } while (change);
            return out;
        } catch (Exception e) {
            return this.getFacts();
        }
    }

    public int getAmount(Fact key) {
        return this.facts.get(key) == null ? 0 : this.facts.get(key);
    }

    public boolean reduce(Fact key, int amount) {
        if (this.getAmount(key) >= amount) {
            int i = this.getAmount(key) - amount;
            this.facts.put(key, i);
            return true;
        }
        return false;
    }

    public FactList remove(Fact key, int amount) {
        int i = this.getAmount(key) - amount;
        if (i <= 0) {
            this.facts.remove(key);
        } else {
            this.facts.put(key, i);
        }
        return this;
    }

    public FactList remove(Fact key) {
        this.facts.remove(key);
        return this;
    }

    public FactList add(Fact fact, int amount) {
        if (this.facts.containsKey(fact)) {
            int oldAmount = this.facts.get(fact);
            amount += oldAmount;
        }
        this.facts.put(fact, amount);
        return this;
    }

    public FactList merge(Fact fact, int amount) {
        int oldAmount = this.facts.get(fact);
        if (this.facts.containsKey(fact) && amount < oldAmount) {
            amount = oldAmount;
        }
        this.facts.put(fact, amount);
        return this;
    }

    public FactList add(FactList list) {
        for (Fact f : list.getFacts()) {
            this.add(f, list.getAmount(f));
        }
        return this;
    }

    public FactList merge(FactList list) {
        for (Fact f : list.getFacts()) {
            this.merge(f, list.getAmount(f));
        }
        return this;
    }

    public void readfromNBT(NBTTagCompound compound) {
        this.facts.clear();
        NBTTagList tagList = compound.getTagList("Facts", 10);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound factData = tagList.getCompoundTagAt(i);
            if (factData.hasKey("key")) {
                this.add(Fact.getFact(factData.getString("key")), factData.getInteger("amount"));
            }
        }
    }

    public void readfromNBT(NBTTagCompound compound, String label) {
        this.facts.clear();
        NBTTagList tagList = compound.getTagList(label, 10);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound factData = tagList.getCompoundTagAt(i);
            if (factData.hasKey("key")) {
                this.add(Fact.getFact(factData.getString("key")), factData.getInteger("amount"));
            }
        }
    }

    public void writeToNBT(NBTTagCompound compunt) {
        NBTTagList tagList = new NBTTagList();
        compunt.setTag("Facts", tagList);
        for (Fact f : this.getFacts()) {
            if (facts != null) {
                NBTTagCompound factData = new NBTTagCompound();
                factData.setString("key", f.getTag());
                factData.setInteger("amount", this.getAmount(f));
                tagList.appendTag(factData);
            }
        }
    }

    public void writeToNBT(NBTTagCompound compunt, String label) {
        NBTTagList tagList = new NBTTagList();
        compunt.setTag(label, tagList);
        for (Fact f : this.getFacts()) {
            if (facts != null) {
                NBTTagCompound factData = new NBTTagCompound();
                factData.setString("key", f.getTag());
                factData.setInteger("amount", this.getAmount(f));
                tagList.appendTag(factData);
            }
        }
    }
}
