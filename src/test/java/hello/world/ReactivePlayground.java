package hello.world;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ReactivePlayground {
    @Test
    public void testGenerator() {
        Flowable<String> f = Flowable.generate(() -> new AtomicInteger(0),
                (integer, output) -> {
                    if (integer.addAndGet(1) < 500_000) {
                        output.onNext("new number: " + integer);
                    } else {
                        output.onComplete();
                    }
                });
        f = f
                .concatMap(item -> Flowable.just(item)
                        .delay(500, TimeUnit.MILLISECONDS));
        Single<List<String>> s = f
                .collect(ArrayList::new, List::add);

        Flowable<List<String>> results =
                s.doOnSuccess(item -> System.out.println(new Date() + " Got item: " + item)).toFlowable();

        TestSubscriber t = new TestSubscriber<>();
        results.subscribe(t);
        t.awaitTerminalEvent();
        t.assertNoErrors();
    }
    @Test
    public void test() {
        Flowable<String> f = Flowable.fromArray(1, 2, 3, 5, 6, 7)
            .map(item -> "number " + item)
            .concatMap(item -> Flowable.just(item)
                .delay(500, TimeUnit.MILLISECONDS));
        Single<List<String>> s = f
                .collect(ArrayList::new, List::add);

        Flowable<List<String>> results =
                s.doOnSuccess(item -> System.out.println(new Date() + " Got item: " + item)).toFlowable();

        TestSubscriber t = new TestSubscriber<>();
        results.subscribe(t);
        t.awaitTerminalEvent();
        t.assertNoErrors();
    }
}
