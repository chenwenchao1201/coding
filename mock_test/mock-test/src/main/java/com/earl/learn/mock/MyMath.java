package com.earl.learn.mock;

/**
 * @author wenchaochen
 * @Title: Math
 * @ProjectName mock-test
 * @Description: TODO
 * @date 2018/10/18  11:34
 */
public class MyMath {
    /**
    　　* @Description: 阶乘方法
    　　* @param n 乘数
    　　* @return
    　　* @throws
    　　* @author earl
    　　* @date 2018/10/18 11:36
    　*/
    public int factorial (int n) throws  Exception{
        if (n<0){
            throw  new Exception("负数没有阶乘");
        }else if (n<=1){
            return  1;
        }else{
            return  n*factorial(n-1);
        }
    }

    /**
    　　* @Description: 冒泡排序
    　　* @param
    　　* @return
    　　* @throws
    　　* @author earl
    　　* @date 2018/10/18 15:36
    　*/
    public void sort(int[] arr){
        for(int i=0;i<arr.length-1;i++){
            for (int j=0;j<arr.length-1-i;j++){
                if(arr[j]>arr[j+1]){
                    int temp =arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }
            }
        }
    }
}
