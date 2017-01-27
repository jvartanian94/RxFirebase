package com.kelvinapps.rxfirebase;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import rx.Emitter;

/**
 * Created by Nick Moskalenko on 24/05/2016.
 */
public class RxHandler<T> implements OnSuccessListener<T>, OnFailureListener, OnCompleteListener<T> {

    private final Emitter<? super T> emitter;

    private RxHandler(Emitter<? super T> emitter) {
        this.emitter = emitter;
    }

    public static <T> void assignOnTask(Emitter<? super T> emitter, Task<T> task) {
        RxHandler handler = new RxHandler(emitter);
        task.addOnSuccessListener(handler);
        task.addOnFailureListener(handler);
        try {
            task.addOnCompleteListener(handler);
        } catch (Throwable t) {
            // ignore
        }
    }

    @Override
    public void onSuccess(T res) {
        emitter.onNext(res);
    }

    @Override
    public void onComplete(@NonNull Task<T> task) {
        emitter.onCompleted();
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        emitter.onError(e);
    }
}
