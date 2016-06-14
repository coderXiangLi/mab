package com.opensource.leo.mab.model;

import java.util.Map;

public class MabVar {
    private Map<String, MabRoundVar> mabRoundVarMap;

    public MabVar(Map<String, MabRoundVar> mabRoundVarMap) {
        this.mabRoundVarMap = mabRoundVarMap;
    }

    public Map<String, MabRoundVar> getMabRoundVarMap() {
        return mabRoundVarMap;
    }

    public void setMabRoundVarMap(Map<String, MabRoundVar> mabRoundVarMap) {
        this.mabRoundVarMap = mabRoundVarMap;
    }
}
