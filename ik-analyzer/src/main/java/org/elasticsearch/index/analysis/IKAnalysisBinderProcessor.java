package org.elasticsearch.index.analysis;

import org.elasticsearch.index.analysis.AnalysisModule.AnalysisBinderProcessor;

public class IKAnalysisBinderProcessor extends AnalysisBinderProcessor {

	@Override
	public void processTokenizers(TokenizersBindings tokenizersBindings) {
		// tokenizersBindings.processTokenizer("ik", IKTokenizerFactory.class);
	}

	@Override
	public void processAnalyzers(AnalyzersBindings analyzersBindings) {
		// analyzersBindings.processAnalyzer("ik", IKAnalyzerProvider.class);
	}

	@Override
	public void processCharFilters(CharFiltersBindings charFiltersBindings) {
	}

	@Override
	public void processTokenFilters(TokenFiltersBindings tokenFiltersBindings) {
		// tokenFiltersBindings.processTokenFilter("synonyms", SynonymsTokenFilterFactory.class);
	}
}
