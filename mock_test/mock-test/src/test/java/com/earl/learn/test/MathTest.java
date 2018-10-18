package com.earl.learn.test;

import com.earl.learn.mock.MyMath;
import org.junit.*;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author wenchaochen
 * @Title: MathTest
 * @ProjectName mock-test
 * @Description: TODO
 * @date 2018/10/18  13:30
 */
public class MathTest {

    public MathTest(){
        System.out.println("构造方法");
    }

    @BeforeClass
    public static void beforeClass(){
        System.out.println("before test class");
    }

    @Before
    public void before(){
        System.out.println("before test");
    }

    @After
    public void after(){
        System.out.println("after test");
    }

    @AfterClass
    public static void afterClass(){
        System.out.println("after test class");
    }

    /*expected后面是预期如果报错 报什么错*/
    @Test (expected = Exception.class)
    public void factorial() throws Exception{
        assertEquals(120,new MyMath().factorial(-1));
        System.out.println("test1");
    }

    @Test
    public void factorial2() throws Exception{
        assertEquals(120,new MyMath().factorial(5));
        System.out.println("test2");
    }

    @Test(timeout = 2000)
    public  void  testSort()throws Exception{
        int[] arr = new int[5000];
        int arrlength = arr.length;
        //生成随机数组
        Random r = new Random();
        for (int i=0;i<arrlength;i++){
            arr[i]=r.nextInt(arrlength);
        }
        new MyMath().sort(arr);
    }


}