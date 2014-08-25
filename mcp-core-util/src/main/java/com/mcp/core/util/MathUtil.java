/**
 * 
 */
package com.mcp.core.util;

/**
 * @author ming.li
 * 
 */
public class MathUtil {

	/**
	 * 获得排列a(m,n)的值
	 * 
	 * @return
	 */
	public static int getA(int m, int n) {
		int value = 1;
		for (int i = 0; i < n; i++) {
			value = value * (m - i);
		}
		return value;
	}

	/**
	 * 获得组合c(m, n)的值
	 */
	public static int getC(int m, int n) {
		if ((m < n) || (n < 0)) {
			return 0;
		}
		return getA(m, n) / getA(n, n);
	}
	
	/**
	 * 获得所有的可能的组合，每一个位至少有>=1个数，返回的是所有记录的序号
	 * @param intArray
	 * @return
	 */
	public static int[][] getDetailMultiplier(int[] intArray)
	{
		int n = 1;
		for(int i = 0; i < intArray.length; i++)
		{
			n = n*intArray[i];
		}
		int[][] data = new int[n][intArray.length];
		
		int splitCount = 1;	
		for(int i = 0; i < intArray.length; i++)
		{
			int numIArray = intArray[i];
			splitCount = splitCount*numIArray;
			
			int countPerNum = n/splitCount;
			int index = 0;	//当前要填的索引
			while(index < n)
			{
				for(int j = 0; j < numIArray; j++)
				{
					for(int k = 0; k < countPerNum; k++)
					{
						data[index][i] = j;
						index++;
					}
				}
			}
		}
		return data;
	}
	
	/**
	 * 获得排列的所有的记录详情，各记录以序号顺序表示
	 * @param m
	 * @param n
	 * @return
	 */
	public static int[][] getDetailC(int m, int n)
	{
		int recordCount = getC(m, n);
		DetailC dc = new DetailC(recordCount, n);
		int[] data = new int[n];
		getDetailC(dc, data, m, n, 0);
		return dc.get();
	}
	
	private static void getDetailC(DetailC dc, int[] data, int m, int n, int level)
	{
		if(n == 0)
		{
			dc.put(data);
			return;
		}
		int startIndex = 0;	//第一层从第一个记录开始
		if(level > 0)
		{
			startIndex = data[level - 1] + 1;	//非第一层，则要从上一层的下一个记录开始
		}
		for(int i = startIndex; i < m; i++)
		{
			data[level] = i;	//记录当层的序号
			getDetailC(dc, data, m, n - 1, level + 1);
		}
	}
	
	private static class DetailC {
		
		DetailC(int count, int n)
		{
			this.data = new int[count][n];
		}
		
		private int index = 0;
		
		private int[][] data;
		
		public void put(int[] set)
		{
			System.arraycopy(set, 0, data[index], 0, set.length);
			index++;
		}
		
		public int[][] get()
		{
			return data;
		}
	}

    /**
     * 冒泡排序
     * @param array
     */
    public static void bubbleSort(int[] array)
    {
        for (int i = 0; i < array.length - 1; i++){    //最多做n-1趟排序
            for(int j = array.length - 1 ;j > i; j--){    //对当前无序区间score[0......length-i-1]进行排序(j的范围很关键，这个范围是在逐步缩小的)
                if(array[j] < array[j - 1]){    //把小的值交换到最前面
                    int temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                }
            }
        }
    }
	
	/*public static void main(String[] args)
	{
		int[] numArray = new int[]{2, 1, 2, 3};
		int[][] data = MathUtil.getDetailMultiplier(numArray);
		for(int i = 0; i < data.length; i++)
		{
			System.out.println(Arrays.toString(data[i]));
		}
        int[] numArray = new int[]{2};
        MathUtil.bubbleSort(numArray);
        for(int i = 0; i < numArray.length; i++)
        {
            System.out.println(numArray[i]);
        }
	}*/

}
