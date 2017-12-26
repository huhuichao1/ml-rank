package hhc.common.utils;

import java.util.*;

public class MapUtil {
	
	public static void main(String[] args) {
		Map map=new HashMap();
		map.put("a", "1");map.put("b", "5");map.put("cc", "2");map.put("e", "0");map.put("d", "4");
		Map map1=MapUtil.sortByValue(map);
		System.out.println(map1);
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return -(o1.getValue()).compareTo(o2.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	/**
	 * 按照value的值排序后，取前top n个。
	 * @param map
	 * @param n
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueTopN(Map<K, V> map,int n) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return -(o1.getValue()).compareTo(o2.getValue());
			}
		});

		int i=0;
		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			i++;
			result.put(entry.getKey(), entry.getValue());
			if(i==n){
				break;
			}
		}
		return result;
	}


	/**
	 * 按照value的值排序后，取value大于n个的值。
	 * @param map
	 * @param n
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> getSortByValueRange(Map<K, V> map,Double n) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return -(o1.getValue()).compareTo(o2.getValue());
			}
		});

		int i=0;
		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			if((Double)entry.getValue()<n){
				break;
			}
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}


	/**
	 *  对map 进行截断。
	 * @param map
	 * @param n ：要截断的位子
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> getTopN(Map<K, V> map,int n) {
		int i=0;
		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : map.entrySet()) {
			result.put(entry.getKey(), entry.getValue());
			i++;
			if(i==n){
				break;
			}
		}
		return result;
	}
}
