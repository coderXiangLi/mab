package com.opensource.leo.mab;

import com.opensource.leo.Algo;
import com.opensource.leo.mab.model.MabRoundVar;
import com.opensource.leo.mab.model.MabVar;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MabCalculater implements Algo<Map<String, Double>, MabVar, Map<String, Double>> {

    @Override
    public MabVar init(Map<String, Double> weight) {
        Map<String, MabRoundVar> init = new HashMap<String, MabRoundVar>();
        for (Map.Entry<String, Double> entry : weight.entrySet()) {
            MabRoundVar var = new MabRoundVar();
            var.setWeight(entry.getValue());
        }
        double max_w = findMaxWeight(init.values());
        double sum_w = sumWeight(init.values(), max_w);
        updateProbability(init.values(), max_w, sum_w, MabConst.GAMA);
        return new MabVar(init);
    }

    @Override
    public MabVar iterate(MabVar mabVar, Map<String, Double> rewards) {
        Map<String, MabRoundVar> mabRoundVarMap = mabVar.getMabRoundVarMap();
        Map<String, MabRoundVar> out = new HashMap<String, MabRoundVar>();
        for (Map.Entry<String, MabRoundVar> varEntry : mabRoundVarMap.entrySet()) {
            String name = varEntry.getKey();
            MabRoundVar var = varEntry.getValue();
            // fetch reward
            Double reward = rewards.get(name);
            MabRoundVar outVar = new MabRoundVar();
            outVar.setWeight(var.getWeight());
            outVar.setProbability(var.getProbability());
            // cal g
            Double g = outVar.calG(reward, MabConst.BETA);
            // cal w
            Double w = outVar.calWeight(g, MabConst.ETA);
            outVar.setWeight(w);
            out.put(name, outVar);
        }
        Collection<MabRoundVar> vars = out.values();
        // max weight
        double maxWeight = findMaxWeight(vars);
        // sum w
        double sumWeight = sumWeight(vars, maxWeight);
        // update p
        updateProbability(vars, maxWeight, sumWeight, MabConst.GAMA);
        return new MabVar(out);
    }

    /**
     * ******************
     * alg functions below
     * ******************
     */
    private double findMaxWeight(Collection<MabRoundVar> vars) {
        double maxWeight = 0;
        for (MabRoundVar var : vars) {
            double w = var.getWeight();
            if (w > maxWeight) {
                maxWeight = w;
            }
        }
        return maxWeight;
    }

    private double sumWeight(Collection<MabRoundVar> vars, double maxWeight) {
        double sumWeight = 0;
        for (MabRoundVar var : vars) {
            double exp_w = Math.exp(var.getWeight() - maxWeight);
            sumWeight += exp_w;
        }
        return sumWeight;
    }

    private void updateProbability(Collection<MabRoundVar> vars, double maxWeight, double sumWeight, double gama) {
        for (MabRoundVar var : vars) {
            double p = (1 - gama) * Math.exp(var.getWeight() - maxWeight)
                    / sumWeight + gama / vars.size();
            var.setProbability(p);
        }
    }
}
