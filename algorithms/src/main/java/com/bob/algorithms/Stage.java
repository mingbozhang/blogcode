package com.bob.algorithms;

import java.util.Arrays;
import java.util.Date;

import com.bob.algorithms.common.CommonUtil;
import com.bob.algorithms.sort.ForkJoinMergeSort;
import com.bob.algorithms.sort.SingleThreadMergeSort;

/**
 * 舞台类，专门用来测试算法的时间
 * 
 * @author bob
 *
 */
public class Stage {

	public static void main(String[] args) {

		// 变量定义
		long begintime = 0;
		long endtime = 0;

		// 生成排序数据
		int[] rawArr = generateIntArray(10000000);
		int[] rawArr2 = Arrays.copyOf(rawArr, rawArr.length);

		begintime = new Date().getTime();
		new SingleThreadMergeSort().sort(rawArr);
		//System.out.println(Arrays.toString(new SingleThreadMergeSort().sort(rawArr)));
		endtime = new Date().getTime();
		System.out.println("单线程归并排序花费时间：" + (endtime - begintime));
		System.out.println("是否升序："+CommonUtil.isSorted(rawArr, true));

		begintime = new Date().getTime();
		new ForkJoinMergeSort().sort(rawArr2);
		//System.out.println(Arrays.toString(new ForkJoinMergeSort().sort(rawArr2)));
		endtime = new Date().getTime();
		System.out.println("Fork/Join归并排序花费时间：" + (endtime - begintime));
		System.out.println("是否升序："+CommonUtil.isSorted(rawArr2, true));
	}

	/**
	 * 生成int类型的数组
	 * 
	 * @return
	 */
	private static int[] generateIntArray(int length) {
		int[] intArr = new int[length];
		for (int i = 0; i < length; i++) {
			intArr[i] = new Double(Math.random() * length).intValue();
		}
		return intArr;
	}
}
