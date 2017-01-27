package com.kelvinapps.rxfirebase;

import android.support.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kelvinapps.rxfirebase.exceptions.RxFirebaseDataException;

import rx.Emitter;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Cancellable;
import rx.functions.Func1;

/**
 * Created by Nick Moskalenko on 15/05/2016.
 */
public class RxFirebaseDatabase {

    @NonNull
    public static Observable<DataSnapshot> observeValueEvent(final Query query) {
        return Observable.fromEmitter(new Action1<Emitter<DataSnapshot>>() {
            @Override
            public void call(final Emitter<DataSnapshot> dataSnapshotEmitter) {
                final ValueEventListener valueEventListener = query.addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                dataSnapshotEmitter.onNext(dataSnapshot);
                            }

                            @Override
                            public void onCancelled(final DatabaseError error) {
                                dataSnapshotEmitter.onError(new RxFirebaseDataException(error));
                            }
                        });

                dataSnapshotEmitter.setCancellation(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        query.removeEventListener(valueEventListener);
                    }
                });
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<DataSnapshot> observeSingleValueEvent(@NonNull final Query query) {
        return Observable.fromEmitter(new Action1<Emitter<DataSnapshot>>() {
            @Override
            public void call(final Emitter<DataSnapshot> dataSnapshotEmitter) {
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSnapshotEmitter.onNext(dataSnapshot);
                        dataSnapshotEmitter.onCompleted();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        dataSnapshotEmitter.onError(new RxFirebaseDataException(error));
                    }
                });
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<RxFirebaseChildEvent<DataSnapshot>> observeChildEvent(
            @NonNull final Query query) {
        return Observable.fromEmitter(new Action1<Emitter<RxFirebaseChildEvent<DataSnapshot>>>() {
            @Override
            public void call(final Emitter<RxFirebaseChildEvent<DataSnapshot>> rxFirebaseChildEventEmitter) {
                final ChildEventListener childEventListener = query.addChildEventListener(
                        new ChildEventListener() {

                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                                rxFirebaseChildEventEmitter.onNext(
                                        new RxFirebaseChildEvent<DataSnapshot>(dataSnapshot.getKey(), dataSnapshot, previousChildName,
                                                RxFirebaseChildEvent.EventType.ADDED));
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                                rxFirebaseChildEventEmitter.onNext(
                                        new RxFirebaseChildEvent<DataSnapshot>(dataSnapshot.getKey(), dataSnapshot, previousChildName,
                                                RxFirebaseChildEvent.EventType.CHANGED));
                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {
                                rxFirebaseChildEventEmitter.onNext(new RxFirebaseChildEvent<DataSnapshot>(dataSnapshot.getKey(), dataSnapshot,
                                        RxFirebaseChildEvent.EventType.REMOVED));
                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                                rxFirebaseChildEventEmitter.onNext(
                                        new RxFirebaseChildEvent<DataSnapshot>(dataSnapshot.getKey(), dataSnapshot, previousChildName,
                                                RxFirebaseChildEvent.EventType.MOVED));
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                rxFirebaseChildEventEmitter.onError(new RxFirebaseDataException(error));
                            }
                        });

                rxFirebaseChildEventEmitter.setCancellation(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        query.removeEventListener(childEventListener);
                    }
                });
            }
        }, Emitter.BackpressureMode.BUFFER);
    }

    @NonNull
    public static <T> Observable<T> observeValueEvent(@NonNull final Query query,
                                                      @NonNull final Class<T> clazz) {
        return observeValueEvent(query, DataSnapshotMapper.of(clazz));
    }

    @NonNull
    public static <T> Observable<T> observeSingleValueEvent(@NonNull final Query query,
                                                            @NonNull final Class<T> clazz) {
        return observeSingleValueEvent(query, DataSnapshotMapper.of(clazz));
    }

    @NonNull
    public static <T> Observable<RxFirebaseChildEvent<T>> observeChildEvent(
            @NonNull final Query query, @NonNull final Class<T> clazz) {
        return observeChildEvent(query, DataSnapshotMapper.ofChildEvent(clazz));
    }

    @NonNull
    public static <T> Observable<T> observeValueEvent(@NonNull final Query query,
                                                      @NonNull final Func1<? super DataSnapshot, ? extends T> mapper) {
        return observeValueEvent(query).map(mapper);
    }

    @NonNull
    public static <T> Observable<T> observeSingleValueEvent(@NonNull final Query query,
                                                            @NonNull final Func1<? super DataSnapshot, ? extends T> mapper) {
        return observeSingleValueEvent(query).map(mapper);
    }

    @NonNull
    public static <T> Observable<RxFirebaseChildEvent<T>> observeChildEvent(
            @NonNull final Query query, @NonNull final Func1<? super RxFirebaseChildEvent<DataSnapshot>, ? extends RxFirebaseChildEvent<T>> mapper) {
        return observeChildEvent(query).map(mapper);
    }
}
