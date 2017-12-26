/**
 * IK 中文分词  版本 5.0.1
 * IK Analyzer release 5.0.1
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * 源代码由林良益(linliangyi2005@gmail.com)提供
 * 版权声明 2012，乌龙茶工作室
 * provided by Linliangyi and copyright 2012 by Oolong studio
 * 
 */
package org.wltea.analyzer.lucene;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.wltea.analyzer.util.LogUtil;

public final class IKAnalyzer extends Analyzer {

	private boolean useSmart;// 是否使用智能分词
	private TokenStream tokenStream;

	public IKAnalyzer() {
		this(false, null);
	}

	public IKAnalyzer(boolean useSmart) {
		this(useSmart, null);
	}

	public IKAnalyzer(TokenStream tokenStrem) {
		this(false, tokenStrem);
	}

	public IKAnalyzer(boolean useSmart, TokenStream tokenStream) {
		super();
		this.useSmart = useSmart;
		this.tokenStream = tokenStream;
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName, final Reader in) {
		Tokenizer tokenizer = new IKTokenizer(in, this.useSmart);
		LogUtil.IK.info("==" + this + "创建：" + tokenizer + "," + this.useSmart);
		if (tokenStream == null) {
			return new TokenStreamComponents(tokenizer);
		} else {
			return new TokenStreamComponents(tokenizer, tokenStream);
		}

	}
}
