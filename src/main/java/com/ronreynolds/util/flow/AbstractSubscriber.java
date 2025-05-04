package com.ronreynolds.util.flow;

import java.util.concurrent.Flow;

public abstract class AbstractSubscriber<T> implements Flow.Subscriber<T> {
    private Throwable error;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        subscription.request(Long.MAX_VALUE);   // give us everything (now would be great)
    }

    @Override
    public void onError(Throwable throwable) {
        error = throwable;
    }

    @Override
    public void onComplete() {
    }

    @Override
    public abstract void onNext(T item);

    public Throwable getError() {
        return error;
    }
}