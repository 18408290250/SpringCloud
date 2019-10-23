package com.junzhangjun.webfluxdemo.stream;

import java.util.stream.IntStream;

/**
 * 操作类型:
 * 1、首先分为中间操作和最终操作:在最终操作没有调用的情况下,所有的中间操作都不会执行。
 * 简单来说，返回stream流的就是中间操作，可以继续链式调用下去，不是返回stream的就是最终操作。
 *
 * 2、最终操作里面分为短路操作和非短路操作，短路操作就是limit/findxxx/xxxMatch这种，就是找了符合条件的就终止，其他的就是非短路操作。
 * 在无限流里面需要调用短路操作，否则像炫迈口香糖一样根本停不下来！
 *
 * 3、中间操作又分为有状态操作和 状态操作，状态就是和其他数据有关系。
 * 看方法的参数，如果是一个参数的，就是无状态操作，因为只和自己有关，其他的就是有状态操作。
 * 如map/filter方法，只有一个参数就是自己，就是无状态操作；
 * 而distinct/sorted就是有状态操作，因为去重和排序都需要和其他数据比较
 *
 * 在多个操作的时候，我们需要把无状态操作写在一起，有状态操作放到最后，这样效率会更加高
 */
public class StreamItrator {
    public static void main(String[] args) {
        int[] nums = {1,2,3};
        //外部迭代
        int sum = 0;
        for(int i:nums){
            sum += i;
        }
        System.out.println("sum:"+sum);

        //使用stream的外部迭代
        // map是中间操作（返回stream的操作）
        // sum是终止操作
        int num2 = IntStream.of(nums).map(StreamItrator::doubleNum).sum();
        System.out.println("num2:"+num2);

        System.out.println("惰性求值就是终止没有调用的情况下，中间操作不会执行");
        IntStream.of(nums).map(StreamItrator::doubleNum);
    }

    public static int doubleNum(int i) {
        System.out.println("执行了乘以2");
        return i * 2;
    }
}
