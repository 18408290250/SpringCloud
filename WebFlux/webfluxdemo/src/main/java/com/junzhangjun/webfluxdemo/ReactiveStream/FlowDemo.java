package com.junzhangjun.webfluxdemo.ReactiveStream;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class FlowDemo {
    public static void main(String[] args) throws InterruptedException {
        //1、定义发布者，发布的数据类型是Integer
        // 直接使用jdk自带的SubmissionPublisher,它实现了Publisher接口
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();

        //2、定义订阅者
        Flow.Subscriber<Integer> subscriber = new Flow.Subscriber<Integer>() {

            private Flow.Subscription subscription;

            // 建立订阅关系的时候调用
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                // 保存订阅关系，需要同它来给发布者响应
                this.subscription = subscription;

                // 请求一个数据
                this.subscription.request(1);
            }

            @Override
            public void onNext(Integer item) {
                // 接收一个数据，处理
                System.out.println("接收到数据："+item);

                // 处理完再调用request再请求一个数据
                this.subscription.request(1);

                // 或者已经达到了目标，调用cancel告诉发布者不再接收数据了
               //  this.subscription.cancel();
            }

            @Override
            public void onError(Throwable throwable) {
                // 出现异常(列如处理数据的时候产生了异常)
                throwable.printStackTrace();

                //高度发布者，后面不再接收数据
                this.subscription.cancel();
            }

            @Override
            public void onComplete() {
                // 全部数据处理完了（发布者关闭的时候出发）
                System.out.println("处理完了");
            }
        };

        // 3、发布者和订阅者建立订阅关系
        publisher.subscribe(subscriber);

        // 4、生产数据并发布
        publisher.submit(111);
        publisher.submit(222);
        publisher.submit(333);


        //5、结束后 关闭发布者
        // 正式环境应该放在finall或者使用try-resource确保关闭
        publisher.close();

        //同步，主线程需要等待子线程执行完成之后再结束
        Thread.currentThread().join(1000);
    }
}
