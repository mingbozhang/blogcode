package com.bob.algorithms.common;

/**
 * 工具类
 * 
 * @author bob
 *
 */
public class CommonUtil {

	/**
	 * 判断传入目标数组是否有序
	 * 
	 * @param destArray
	 * @param isAsc
	 *            是否为升序，true 升序，false 降序
	 * @return
	 */
	public static boolean isSorted(int[] destArray, boolean isAsc) {
		for (int i = 0; i < destArray.length - 1; i++) {
			if (isAsc) {
				if (destArray[i] > destArray[i + 1])
					return false;
			} else {
				if (destArray[i] < destArray[i + 1])
					return false;
			}
		}
		return true;
	}

}
