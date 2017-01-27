package com.kelvinapps.rxfirebase;

import android.support.annotation.NonNull;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.UserProfileChangeRequest;

import rx.Emitter;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Nick Moskalenko on 24/05/2016.
 */
public class RxFirebaseUser {

    @NonNull
    public static Observable<GetTokenResult> getToken(@NonNull final FirebaseUser firebaseUser,
                                                      final boolean forceRefresh) {
        return Observable.fromEmitter(new Action1<Emitter<GetTokenResult>>() {
            @Override
            public void call(Emitter<GetTokenResult> getTokenResultEmitter) {
                RxHandler.assignOnTask(getTokenResultEmitter, firebaseUser.getToken(forceRefresh));
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<Void> updateEmail(@NonNull final FirebaseUser firebaseUser,
                                               @NonNull final String email) {
        return Observable.fromEmitter(new Action1<Emitter<Void>>() {
            @Override
            public void call(Emitter<Void> voidEmitter) {
                RxHandler.assignOnTask(voidEmitter, firebaseUser.updateEmail(email));
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<Void> updatePassword(@NonNull final FirebaseUser firebaseUser,
                                                  @NonNull final String password) {
        return Observable.fromEmitter(new Action1<Emitter<Void>>() {
            @Override
            public void call(Emitter<Void> voidEmitter) {
                RxHandler.assignOnTask(voidEmitter, firebaseUser.updatePassword(password));
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<Void> updateProfile(@NonNull final FirebaseUser firebaseUser,
                                                 @NonNull final UserProfileChangeRequest request) {
        return Observable.fromEmitter(new Action1<Emitter<Void>>() {
            @Override
            public void call(Emitter<Void> voidEmitter) {
                RxHandler.assignOnTask(voidEmitter, firebaseUser.updateProfile(request));
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<Void> delete(@NonNull final FirebaseUser firebaseUser) {
        return Observable.fromEmitter(new Action1<Emitter<Void>>() {
            @Override
            public void call(Emitter<Void> voidEmitter) {
                RxHandler.assignOnTask(voidEmitter, firebaseUser.delete());
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<Void> reauthenticate(@NonNull final FirebaseUser firebaseUser,
                                                  @NonNull final AuthCredential credential) {
        return Observable.fromEmitter(new Action1<Emitter<Void>>() {
            @Override
            public void call(Emitter<Void> voidEmitter) {
                RxHandler.assignOnTask(voidEmitter, firebaseUser.reauthenticate(credential));
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<AuthResult> linkWithCredential(@NonNull final FirebaseUser firebaseUser,
                                                            @NonNull final AuthCredential credential) {
        return Observable.fromEmitter(new Action1<Emitter<AuthResult>>() {
            @Override
            public void call(Emitter<AuthResult> authResultEmitter) {
                RxHandler.assignOnTask(authResultEmitter, firebaseUser.linkWithCredential(credential));
            }
        }, Emitter.BackpressureMode.LATEST);
    }


}
