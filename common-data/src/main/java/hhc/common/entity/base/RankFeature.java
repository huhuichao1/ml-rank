package hhc.common.entity.base;

import java.util.HashMap;
import java.util.Map;

public abstract class RankFeature {

	public double[] features;
	public Map<FeatureType, double[]> featureMap = new HashMap<FeatureType, double[]>();

	// public RecordBase record;
	public RankFeature() {
	}

}
