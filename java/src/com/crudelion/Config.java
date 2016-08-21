package com.crudelion;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by guliuzhong on 2016/8/21.
 */
public class Config {
    private String source = null;
    private String target = null;
    private Boolean targetClean = null;
    private Set<String> targetExclude = new HashSet<String>();

    public Set<String> getSourceExclude() {
        return sourceExclude;
    }

    public void setSourceExclude(Set<String> sourceExclude) {
        this.sourceExclude = sourceExclude;
    }

    private Set<String> sourceExclude = new HashSet<String>();


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Boolean getTargetClean() {
        return targetClean;
    }

    public void setTargetClean(Boolean targetClean) {
        this.targetClean = targetClean;
    }

    public Set<String> getTargetExclude() {
        return targetExclude;
    }

    public void setTargetExclude(Set<String> targetExclude) {
        this.targetExclude = targetExclude;
    }
}
