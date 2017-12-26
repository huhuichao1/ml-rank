package org.elasticsearch.plugins.analysis.ik;

import org.elasticsearch.plugins.AbstractPlugin;

/**
 * Ik分词器插件
 * 
 * 每个index启动会调用IndexNameModule-IndexModule，配置文件：es-plugin.properties
 * 
 * @author linshouyi
 *
 */
public class IKAnalysisPlugin extends AbstractPlugin {

	@Override
	public String name() {
		return "ik";
	}

	@Override
	public String description() {
		return "ik analysis plugin";
	}

	// public void onModule(AnalysisModule module) {
	// LogUtil.IK.info("===" + this + "创建" + module);
	// AnalysisModule analysisModule = (AnalysisModule) module;
	// analysisModule.addProcessor(new IKAnalysisBinderProcessor());
	// }

	// public void processModule(Module module) {
	// LogUtil.IK.info(===" + this+module);
	// if (module instanceof AnalysisModule) {
	// AnalysisModule analysisModule = (AnalysisModule) module;
	// analysisModule.addProcessor(new IKAnalysisBinderProcessor());
	// }
	// }
}
