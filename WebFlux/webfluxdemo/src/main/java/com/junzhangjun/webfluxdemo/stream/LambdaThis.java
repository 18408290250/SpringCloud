package com.junzhangjun.webfluxdemo.stream;


/**
 * lambda表达式的this
 *
 * lambda表达式最终会返回一个实现了指定接口的实例，
 * 看上去和内部匿名类很像，但有一个最大的区别就是代码里面的this，
 * 内部匿名类this指向的就是匿名类，而lambda表达式里面的this指向的当前类
 */
public class LambdaThis {

    private String name = "thisdemo";

    public void test(){
        // 匿名类
        new Thread(new Runnable() {
            private String name = "Runnable";
            @Override
            public void run() {
                System.out.println("匿名类里的this指向匿名类："+this.name);
            }
        }).start();

        /**
         *lambda表达式里面，会把lambda表达式在本类中生成一个以lambda$+数字的方法。
         * 关键点：该方法不一定是static的方法，是static还是非static，取决于lambda表达式里面是否引用了this。
         * 这就是为什么lambda表达式里面的this指向的是本地，
         * 因为他在本类里面创建了一个方法，然后把lambda表达式里面的代码放进去
         *
         * lambda表达式里面的this指向当前类的底层机制！因为代码就是在本类的一个方法里面执行的。
         */

        // lambda实现
        new Thread(()-> System.out.println("lambda里的this指向当前类："+this.name)).start();

        // lambda实现
        new Thread(()-> System.out.println("这里没有引用this,生成的lambda1方法是static的"));
    }

    public static void main(String[] args) {
        new LambdaThis().test();
    }

}
