/**
 * 
 */
package com.ambow.trainingengine.util;

/**
 * Levenshtein Distance(LD)：最先是由俄国科学家Vladimir Levenshtein在1965年发明，用他的名字命名。
 * 计算两个字符串的相似度。一个字符串转换成另外一个字符串过程中的添加、删除、修改的最小数值min
 * 相似度：1-min/两个字符串的最大值
 * @author yuanjunqi
 *
 */
public class Similarity {
	/**
	 * 获取最小值
	 * @param one
	 * @param two
	 * @param three
	 * @return
	 */
    private int min(int one, int two, int three) {   
        int min = one;   
        if(two < min) {   
            min = two;   
        }   
        if(three < min) {   
            min = three;   
        }   
        return min;   
    }   
     
    /**
     * 获取两个字符串的最短距离数
     * @param str1
     * @param str2
     * @return
     */
    public int ld(String str1, String str2) {   
        int d[][];  //矩阵   
        int n = str1.length();   
        int m = str2.length();   
        int i;  //遍历str1的   
        int j;  //遍历str2的   
        char ch1;   //str1的   
        char ch2;   //str2的   
        int temp;   //记录相同字符,在某个矩阵位置值的增量,不是0就是1   
        if(n == 0) {   
            return m;   
        }   
        if(m == 0) {   
            return n;   
        }   
        d = new int[n+1][m+1];   
        for(i=0; i<=n; i++) {    //初始化第一列   
            d[i][0] = i;   
        }   
        for(j=0; j<=m; j++) {    //初始化第一行   
            d[0][j] = j;   
        }   
        for(i=1; i<=n; i++) {    //遍历str1   
            ch1 = str1.charAt(i-1);   
            //去匹配str2   
            for(j=1; j<=m; j++) { 
                ch2 = str2.charAt(j-1);   
                if(ch1 == ch2) {   
                    temp = 0;   
                } else {   
                    temp = 1;   
                }   
                //左边+1,上边+1, 左上角+temp取最小   
                d[i][j] = min(d[i-1][j]+1, d[i][j-1]+1, d[i-1][j-1]+temp); 
            }   
        }
        return d[n][m];   
    }   
      
    /**
     * 相似度计算
     * @param str1
     * @param str2
     * @return
     */
    public double sim(String str1, String str2) {   
        int ld = ld(str1, str2);   
        return 1 - (double) ld / Math.max(str1.length(), str2.length());    
    }
    
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		String str1 = "Now since the assessment of intelligence is [A] a comparative matter [B] we must be sure [C] that the scale with which we are";
//		String str2 = "Now since  of intelligence is [A] a comparative matter      [B] we must be sure [C] that the scale with which we are";
//		
//		Similarity sim = new Similarity();
//		System.out.println(sim.sim(str1, str2));
//	}
}
