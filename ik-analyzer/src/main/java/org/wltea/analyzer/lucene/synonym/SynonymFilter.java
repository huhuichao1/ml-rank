package org.wltea.analyzer.lucene.synonym;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.AttributeSource;

public final class SynonymFilter extends TokenFilter {

	public static final String TYPE_SYNONYM = "SYNONYM";
	private List<String> buffer;
	private AttributeSource.State current;
	private final CharTermAttribute termAtt;
	private final PositionIncrementAttribute posIncrAtt;
	// private final OffsetAttribute offsetAtt;
	private final TypeAttribute typeAtt;
	private SynonymDict dict;

	public SynonymFilter(TokenStream input, SynonymDict dict) {
		super(input);
		buffer = new ArrayList<String>();
		this.termAtt = addAttribute(CharTermAttribute.class);
		this.posIncrAtt = addAttribute(PositionIncrementAttribute.class);
		// this.offsetAtt = addAttribute(OffsetAttribute.class);
		this.typeAtt = addAttribute(TypeAttribute.class);
		this.dict = dict;
	}

	@Override
	public boolean incrementToken() throws IOException {
		if (!buffer.isEmpty()) {
			String syn = buffer.remove(0);
			restoreState(current);
			termAtt.setEmpty();
			termAtt.append(syn);
			posIncrAtt.setPositionIncrement(0);
			typeAtt.setType(TYPE_SYNONYM);
			return true;
		}
		if (!input.incrementToken()) {
			return false;
		}
		String term = termAtt.toString();
		String[] synonyms = dict != null ? dict.get(term) : null;
		if (synonyms != null && synonyms.length > 0) {
			current = captureState();
			for (String synonym : synonyms) {
				buffer.add(synonym);
			}
		}
		return true;
	}
}
