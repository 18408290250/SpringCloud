package com.junzhangjun.webfluxdemo.stream;

import java.util.function.BiFunction;
import java.util.function.IntUnaryOperator;

/**
 * https://www.cnblogs.com/xwjie/p/8995150.html
 *
 * 方法的引用
 */
public class LambdaMethod {

    public static void main(String[] args) {

        // 1、静态方法的引用
      //  Function<Integer, Integer> staticMethod = DemoClass::staticMethod;
        IntUnaryOperator staticMethod = DemoClass::staticMethod; // IntUnaryOperator参数同为类型int,返回值类型也为int
        System.out.println(staticMethod.applyAsInt(111));

        // 2、实例方法的引用
            // 实例方法normalMethod的方法引用
        DemoClass demo = new DemoClass();
        IntUnaryOperator methodRefrence2 = demo::normalMethod;
        System.out.println(methodRefrence2.applyAsInt(111));

            // 对同一个实例方法normalMethod也可以使用静态引用
            // 代码上normalMethod虽然只有一个参数,但实际上有一个隐含的this函数
            // 所以使用的是2个参数bifunction函数接口
        BiFunction<DemoClass, Integer, Integer> methodRefrence3 = DemoClass::normalMethod;
        System.out.println(methodRefrence3.apply(demo, 111));


    }
}

class DemoClass{
    // 静态方法
    public static int staticMethod(int i) {
        return i * 2;
    }

   // 实例方法
    public int normalMethod(int i) {
        System.out.println("实例方法可以访问this:" + this);
        return i * 3;
    }
//    public int normalMethod(DemoClass this,int i) {
//        System.out.println("实例方法可以访问this:" + this);
//        return i * 3;
//    }


}
