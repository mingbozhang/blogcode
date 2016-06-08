package com.bob.algorithms.sort;

import java.util.Arrays;

import com.bob.algorithms.SortStrategy;

/**
 * 归并排序
 * 
 * @author bob
 *
 */
public class SingleThreadMergeSort implements SortStrategy {

	public int[] sort(int[] rawArray) {
		mergeSort(rawArray);
		return rawArray;
	}

	/**
	 * 分解并合并排序,升序
	 * 
	 * @param intArr
	 */
	private void mergeSort(int[] intArr) {
		if (intArr.length > 1) {
			// 如果数组长度大于1就分解称两份
			int[] leftArray = Arrays.copyOfRange(intArr, 0, intArr.length / 2);
			int[] rightArray = Arrays.copyOfRange(intArr, intArr.length / 2, intArr.length);
			mergeSort(leftArray);
			mergeSort(rightArray);

			// 合并且排序
			merge(leftArray, rightArray, intArr);
		}
	}

	/**
	 * 合并排序
	 * 
	 * @param leftArray
	 * @param rightArray
	 * @param intArr
	 */
	private void merge(int[] leftArray, int[] rightArray, int[] intArr) {

		// i：leftArray数组索引，j：rightArray数组索引，k：intArr数组索引
		int i = 0, j = 0, k = 0;
		while (i < leftArray.length && j < rightArray.length) {
			// 当两个数组中都有值的时候，比较当前元素进行选择
			if (leftArray[i] < rightArray[j]) {
				intArr[k] = leftArray[i];
				i++;
			} else {
				intArr[k] = rightArray[j];
				j++;
			}
			k++;
		}

		// 将还剩余元素没有遍历完的数组直接追加到intArr后面
		if (i == leftArray.length) {
			for (; j < rightArray.length; j++, k++) {
				intArr[k] = rightArray[j];
			}
		} else {
			for (; i < leftArray.length; i++, k++) {
				intArr[k] = leftArray[i];
			}
		}
	}
}
