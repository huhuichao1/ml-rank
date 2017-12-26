package com.huaban.analysis.jieba;

import com.huaban.analysis.jieba.viterbi.FinalSeg;

import java.nio.file.Paths;
import java.util.*;

public class JiebaSegmenter {
	private static WordDictionary wordDict = WordDictionary.getInstance();
	private static FinalSeg finalSeg = FinalSeg.getInstance();

	public static enum SegMode {
		INDEX, SEARCH
	}

	private Map<Integer, List<Integer>> createDAG(String sentence) {
		Map<Integer, List<Integer>> dag = new HashMap<Integer, List<Integer>>();
		DictSegment trie = wordDict.getTrie();
		char[] chars = sentence.toCharArray();
		int N = chars.length;
		int i = 0, j = 0;
		while (i < N) {
			Hit hit = trie.match(chars, i, j - i + 1);
			if (hit.isPrefix() || hit.isMatch()) {
				if (hit.isMatch()) {
					if (!dag.containsKey(i)) {
						List<Integer> value = new ArrayList<Integer>();
						dag.put(i, value);
						value.add(j);
					} else
						dag.get(i).add(j);
				}
				j += 1;
				if (j >= N) {
					i += 1;
					j = i;
				}
			} else {
				i += 1;
				j = i;
			}
		}
		for (i = 0; i < N; ++i) {
			if (!dag.containsKey(i)) {
				List<Integer> value = new ArrayList<Integer>();
				value.add(i);
				dag.put(i, value);
			}
		}
		return dag;
	}

	private Map<Integer, Pair<Integer>> calc(String sentence, Map<Integer, List<Integer>> dag) {
		int N = sentence.length();
		HashMap<Integer, Pair<Integer>> route = new HashMap<Integer, Pair<Integer>>();
		route.put(N, new Pair<Integer>(0, 0.0));
		for (int i = N - 1; i > -1; i--) {
			Pair<Integer> candidate = null;
			for (Integer x : dag.get(i)) {
				double freq = wordDict.getFreq(sentence.substring(i, x + 1)) + route.get(x + 1).freq;
				if (null == candidate) {
					candidate = new Pair<Integer>(x, freq);
				} else if (candidate.freq < freq) {
					candidate.freq = freq;
					candidate.key = x;
				}
			}
			route.put(i, candidate);
		}
		return route;
	}

	public List<SegToken> process(String paragraph, SegMode mode) {
		List<SegToken> tokens = new ArrayList<SegToken>();
		StringBuilder sb = new StringBuilder();
		int offset = 0;
		for (int i = 0; i < paragraph.length(); ++i) {
			char ch = CharacterUtil.regularize(paragraph.charAt(i));
			if (CharacterUtil.ccFind(ch))
				sb.append(ch);
			else {
				if (sb.length() > 0) {
					// process
					if (mode == SegMode.SEARCH) {
						for (String word : sentenceProcess(sb.toString())) {
							tokens.add(new SegToken(word, offset, offset += word.length()));
						}
					} else {
						for (String token : sentenceProcess(sb.toString())) {
							if (token.length() > 2) {
								String gram2;
								int j = 0;
								for (; j < token.length() - 1; ++j) {
									gram2 = token.substring(j, j + 2);
									if (wordDict.containsWord(gram2))
										tokens.add(new SegToken(gram2, offset + j, offset + j + 2));
								}
							}
							if (token.length() > 3) {
								String gram3;
								int j = 0;
								for (; j < token.length() - 2; ++j) {
									gram3 = token.substring(j, j + 3);
									if (wordDict.containsWord(gram3))
										tokens.add(new SegToken(gram3, offset + j, offset + j + 3));
								}
							}
							tokens.add(new SegToken(token, offset, offset += token.length()));
						}
					}
					sb = new StringBuilder();
					offset = i;
				}
				if (wordDict.containsWord(paragraph.substring(i, i + 1)))
					tokens.add(new SegToken(paragraph.substring(i, i + 1), offset, ++offset));
				else
					tokens.add(new SegToken(paragraph.substring(i, i + 1), offset, ++offset));
			}
		}
		if (sb.length() > 0)
			if (mode == SegMode.SEARCH) {
				for (String token : sentenceProcess(sb.toString())) {
					tokens.add(new SegToken(token, offset, offset += token.length()));
				}
			} else {
				for (String token : sentenceProcess(sb.toString())) {
					if (token.length() > 2) {
						String gram2;
						int j = 0;
						for (; j < token.length() - 1; ++j) {
							gram2 = token.substring(j, j + 2);
							if (wordDict.containsWord(gram2))
								tokens.add(new SegToken(gram2, offset + j, offset + j + 2));
						}
					}
					if (token.length() > 3) {
						String gram3;
						int j = 0;
						for (; j < token.length() - 2; ++j) {
							gram3 = token.substring(j, j + 3);
							if (wordDict.containsWord(gram3))
								tokens.add(new SegToken(gram3, offset + j, offset + j + 3));
						}
					}
					tokens.add(new SegToken(token, offset, offset += token.length()));
				}
			}

		return tokens;
	}

	/*
	 * 
	 */
	public List<String> sentenceProcess(String sentence) {
		List<String> tokens = new ArrayList<String>();
		int N = sentence.length();
		Map<Integer, List<Integer>> dag = createDAG(sentence);
		Map<Integer, Pair<Integer>> route = calc(sentence, dag);

		int x = 0;
		int y = 0;
		String buf;
		StringBuilder sb = new StringBuilder();
		while (x < N) {
			y = route.get(x).key + 1;
			String lWord = sentence.substring(x, y);
			if (y - x == 1)
				sb.append(lWord);
			else {
				if (sb.length() > 0) {
					buf = sb.toString();
					sb = new StringBuilder();
					if (buf.length() == 1) {
						tokens.add(buf);
					} else {
						if (wordDict.containsWord(buf)) {
							tokens.add(buf);
						} else {
							finalSeg.cut(buf, tokens);
						}
					}
				}
				if (!WordDictionary.getInstance().isStopWord(lWord)) {
					tokens.add(lWord);
				}
			}
			x = y;
		}
		buf = sb.toString();
		if (buf.length() > 0) {
			if (buf.length() == 1) {
				tokens.add(buf);
			} else {
				if (wordDict.containsWord(buf)) {
					tokens.add(buf);
				} else {
					finalSeg.cut(buf, tokens);
				}
			}

		}

		for (int index = 0; index < tokens.size(); index++) {
			if (WordDictionary.getInstance().isStopWord(tokens.get(index))) {
				tokens.remove(index);
				index--;
			}
		}
		return tokens;
	}

	public List<String> processContent(String paragraph, Boolean type) {
		if(type){
			return processContent(paragraph,SegMode.SEARCH);
		}else {
			return processContent(paragraph,SegMode.INDEX);
		}
	}
	public List<String> processContent(String paragraph, SegMode mode) {
		List<SegToken> tokens = new ArrayList<SegToken>();
		StringBuilder sb = new StringBuilder();
		int offset = 0;
		for (int i = 0; i < paragraph.length(); ++i) {
			char ch = CharacterUtil.regularize(paragraph.charAt(i));
			if (CharacterUtil.ccFind(ch))
				sb.append(ch);
			else {
				if (sb.length() > 0) {
					// process
					if (mode == SegMode.SEARCH) {
						for (String word : sentenceProcess(sb.toString())) {
							tokens.add(new SegToken(word, offset, offset += word.length()));
						}
					} else {
						for (String token : sentenceProcess(sb.toString())) {
							if (token.length() > 2) {
								String gram2;
								int j = 0;
								for (; j < token.length() - 1; ++j) {
									gram2 = token.substring(j, j + 2);
									if (wordDict.containsWord(gram2))
										tokens.add(new SegToken(gram2, offset + j, offset + j + 2));
								}
							}
							if (token.length() > 3) {
								String gram3;
								int j = 0;
								for (; j < token.length() - 2; ++j) {
									gram3 = token.substring(j, j + 3);
									if (wordDict.containsWord(gram3))
										tokens.add(new SegToken(gram3, offset + j, offset + j + 3));
								}
							}
							tokens.add(new SegToken(token, offset, offset += token.length()));
						}
					}
					sb = new StringBuilder();
					offset = i;
				}
				if (wordDict.containsWord(paragraph.substring(i, i + 1)))
					tokens.add(new SegToken(paragraph.substring(i, i + 1), offset, ++offset));
				else
					tokens.add(new SegToken(paragraph.substring(i, i + 1), offset, ++offset));
			}
		}
		if (sb.length() > 0)
			if (mode == SegMode.SEARCH) {
				for (String token : sentenceProcess(sb.toString())) {
					tokens.add(new SegToken(token, offset, offset += token.length()));
				}
			} else {
				for (String token : sentenceProcess(sb.toString())) {
					if (token.length() > 2) {
						String gram2;
						int j = 0;
						for (; j < token.length() - 1; ++j) {
							gram2 = token.substring(j, j + 2);
							if (wordDict.containsWord(gram2))
								tokens.add(new SegToken(gram2, offset + j, offset + j + 2));
						}
					}
					if (token.length() > 3) {
						String gram3;
						int j = 0;
						for (; j < token.length() - 2; ++j) {
							gram3 = token.substring(j, j + 3);
							if (wordDict.containsWord(gram3))
								tokens.add(new SegToken(gram3, offset + j, offset + j + 3));
						}
					}
					tokens.add(new SegToken(token, offset, offset += token.length()));
				}
			}
		List<String> strings=new ArrayList<>();
		for(SegToken token:tokens){
			if (WordDictionary.getInstance().isStopWord(token.word)) {
				continue;
			}
			strings.add(token.word);
		}
		return strings;
	}

	public static void main(String[] args) {
		String str = "胡慧超通州万达广场，张江高科，拳击运动原本就是一片黄金地段，而拳王梅威瑟钱似乎不用说大家也有目共睹，毕竟在INS或者推特上炫出来的可不少。2015年，梅威瑟大战帕奎奥的那场比赛，短短36分钟，就拿下了3亿美金。据说梅威瑟借鉴了泰森破产的教训，在自己的一张银行卡中定期存了1.23亿美金。|$|拳击运动原本就是一片黄金地段，而拳王梅威瑟钱似乎不用说大家也有目共睹，毕竟在INS或者推特上炫出来的可不少。2015年，梅威瑟大战帕奎奥的那场比赛，短短36分钟，就拿下了3亿美金。据说梅威瑟借鉴了泰森破产的教训，在自己的一张银行卡中定期存了1.23亿美金。而梅威瑟的女人，也是层出不穷，经常看见他换，那么问题来了，他究竟是怎么追到那些美女的？ 先去看看梅威瑟的女人，这些还只是梅威瑟的前女友，目前已经凑够一桌了。他们面对Showtime的镜头，有说有笑，似乎一点没有生气吃醋的状态。梅威瑟也站在他们之中，和他们谈笑风生。据Showtime官方报道，梅威瑟的女友有模特，有主持人，有真人秀演员，也有大牌明星。梅威瑟几乎给每个女友都要送点东西，才能博得好感。 梅威瑟要追女人其实也不是简单的事情，并不是每个女人都会有一见钟情的状况。不过即便第一次见到梅威瑟不喜欢，甚至是讨厌，也架不住梅威瑟的糖衣炮弹。有一次，梅威瑟出席舞会，见到了一个美女主持人，他毫不犹豫的上去搭讪，不过遭到了拒绝。舞会结束之后，梅威瑟在门口等她，约她出去兜风，尽管看到豪车，女主持人还是拒绝了。梅威瑟并没有死心，直接买下了一辆劳斯莱斯送给她。好吧，这回答应去“兜风”了。随后就被媒体爆出，";
		str="8月15日，网络大电影《悬赏令》在广州渔轮修造厂旧仓库内举行了隆重的开机仪式。发布会吸引了众多车友驾车到场参加此次活动。《悬赏令》是一部具有悬疑色彩的飞车动作大片，拥有众多实力派演员阵容和强大的拍摄制作团队，想必会是一部很燃的网络大片。香港知名影视编剧，乘梦飞翔（北京）文化传播有限公司董事长秋婷女士发表讲话。制作团队，中间的高个子便是导演邓宇澄。男一号张俊杰，同时也是出品人和执行监制，曾出演过《危城》、《拆弹专家》等电影。此次电影里的飞车指导，来自钱家班的有“香港漂移王”之称的吴海棠，电影《车手》中的“8000转2迈”就是他的创意，同时他也表示在《悬赏令》中会带来更多创新的飞车动作。主演代表，张俊杰、纪源、谭小雅、宋涛。大吉大利，开机顺利。这辆粉蓝色的宽体保时捷911也是十分骚气，青蛙眼，大尾翼，粗暴的宽体，这简直就是一只战斗青蛙。《悬赏令》已经拍摄了一段时间了，张俊杰表示该片将会在本月月底完成拍摄，并预计会在今年的年底在国内外各大平台上映，并且未来还会有续集，喜欢飞车电影的朋友们不妨期待下这部网络大电影吧。有奖征稿，欢迎来稿~";
		JiebaSegmenter segmenter = new JiebaSegmenter();
		WordDictionary.getInstance().init(Paths.get("conf"));
		System.out.println(Arrays.toString(segmenter.processContent(str,SegMode.INDEX).toArray()));
		System.out.println(Arrays.toString(segmenter.processContent(str,SegMode.SEARCH).toArray()));
		System.out.println(Arrays.toString(segmenter.sentenceProcess(str).toArray()));

	}
}
