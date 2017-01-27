package com.kelvinapps.rxfirebase;

import android.support.annotation.NonNull;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;

import rx.Emitter;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Cancellable;

/**
 * Created by Nick Moskalenko on 15/05/2016.
 */
public class RxFirebaseAuth {

    @NonNull
    public static Observable<AuthResult> signInAnonymously(@NonNull final FirebaseAuth firebaseAuth) {
        return Observable.fromEmitter(new Action1<Emitter<AuthResult>>() {
            @Override
            public void call(Emitter<AuthResult> authResultEmitter) {
                RxHandler.assignOnTask(authResultEmitter, firebaseAuth.signInAnonymously());
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<AuthResult> signInWithEmailAndPassword(@NonNull final FirebaseAuth firebaseAuth,
                                                                    @NonNull final String email,
                                                                    @NonNull final String password) {
        return Observable.fromEmitter(new Action1<Emitter<AuthResult>>() {
            @Override
            public void call(Emitter<AuthResult> authResultEmitter) {
                RxHandler.assignOnTask(authResultEmitter, firebaseAuth.signInWithEmailAndPassword(email, password));
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<AuthResult> signInWithCredential(@NonNull final FirebaseAuth firebaseAuth,
                                                              @NonNull final AuthCredential credential) {
        return Observable.fromEmitter(new Action1<Emitter<AuthResult>>() {
            @Override
            public void call(Emitter<AuthResult> authResultEmitter) {
                RxHandler.assignOnTask(authResultEmitter, firebaseAuth.signInWithCredential(credential));
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<AuthResult> signInWithCustomToken(@NonNull final FirebaseAuth firebaseAuth,
                                                               @NonNull final String token) {
        return Observable.fromEmitter(new Action1<Emitter<AuthResult>>() {
            @Override
            public void call(Emitter<AuthResult> authResultEmitter) {
                RxHandler.assignOnTask(authResultEmitter, firebaseAuth.signInWithCustomToken(token));
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<AuthResult> createUserWithEmailAndPassword(@NonNull final FirebaseAuth firebaseAuth,
                                                                        @NonNull final String email,
                                                                        @NonNull final String password) {
        return Observable.fromEmitter(new Action1<Emitter<AuthResult>>() {
            @Override
            public void call(Emitter<AuthResult> authResultEmitter) {
                RxHandler.assignOnTask(authResultEmitter, firebaseAuth.createUserWithEmailAndPassword(email, password));
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<ProviderQueryResult> fetchProvidersForEmail(@NonNull final FirebaseAuth firebaseAuth,
                                                                         @NonNull final String email) {
        return Observable.fromEmitter(new Action1<Emitter<ProviderQueryResult>>() {
            @Override
            public void call(Emitter<ProviderQueryResult> providerQueryResultEmitter) {
                RxHandler.assignOnTask(providerQueryResultEmitter, firebaseAuth.fetchProvidersForEmail(email));
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<Void> sendPasswordResetEmail(@NonNull final FirebaseAuth firebaseAuth,
                                                                @NonNull final String email) {
        return Observable.fromEmitter(new Action1<Emitter<Void>>() {
            @Override
            public void call(Emitter<Void> voidEmitter) {
                RxHandler.assignOnTask(voidEmitter, firebaseAuth.sendPasswordResetEmail(email));
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<FirebaseUser> observeAuthState(@NonNull final FirebaseAuth firebaseAuth) {

        return Observable.fromEmitter(new Action1<Emitter<FirebaseUser>>() {
            @Override
            public void call(final Emitter<FirebaseUser> firebaseUserEmitter) {
                final FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        firebaseUserEmitter.onNext(firebaseAuth.getCurrentUser());
                    }
                };
                firebaseAuth.addAuthStateListener(authStateListener);

                firebaseUserEmitter.setCancellation(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        firebaseAuth.removeAuthStateListener(authStateListener);
                    }
                });
            }
        }, Emitter.BackpressureMode.BUFFER);
    }
}
