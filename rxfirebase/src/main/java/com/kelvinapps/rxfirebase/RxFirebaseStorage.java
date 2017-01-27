package com.kelvinapps.rxfirebase;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.InputStream;

import rx.Emitter;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Nick Moskalenko on 24/05/2016.
 */
public class RxFirebaseStorage {

    @NonNull
    public static Observable<byte[]> getBytes(@NonNull final StorageReference storageRef,
                                              final long maxDownloadSizeBytes) {
        return Observable.fromEmitter(new Action1<Emitter<byte[]>>() {
            @Override
            public void call(Emitter<byte[]> emitter) {
                RxHandler.assignOnTask(emitter, storageRef.getBytes(maxDownloadSizeBytes));
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<Uri> getDownloadUrl(@NonNull final StorageReference storageRef) {
        return Observable.fromEmitter(new Action1<Emitter<Uri>>() {
            @Override
            public void call(Emitter<Uri> uriEmitter) {
                RxHandler.assignOnTask(uriEmitter, storageRef.getDownloadUrl());
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<FileDownloadTask.TaskSnapshot> getFile(@NonNull final StorageReference storageRef,
                                                                    @NonNull final File destinationFile) {
        return Observable.fromEmitter(new Action1<Emitter<FileDownloadTask.TaskSnapshot>>() {
            @Override
            public void call(Emitter<FileDownloadTask.TaskSnapshot> taskSnapshotEmitter) {
                RxHandler.assignOnTask(taskSnapshotEmitter, storageRef.getFile(destinationFile));
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<FileDownloadTask.TaskSnapshot> getFile(@NonNull final StorageReference storageRef,
                                                                    @NonNull final Uri destinationUri) {
        return Observable.fromEmitter(new Action1<Emitter<FileDownloadTask.TaskSnapshot>>() {
            @Override
            public void call(Emitter<FileDownloadTask.TaskSnapshot> taskSnapshotEmitter) {
                RxHandler.assignOnTask(taskSnapshotEmitter, storageRef.getFile(destinationUri));
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<StorageMetadata> getMetadata(@NonNull final StorageReference storageRef) {
        return Observable.fromEmitter(new Action1<Emitter<StorageMetadata>>() {
            @Override
            public void call(Emitter<StorageMetadata> storageMetadataEmitter) {
                RxHandler.assignOnTask(storageMetadataEmitter, storageRef.getMetadata());
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<StreamDownloadTask.TaskSnapshot> getStream(@NonNull final StorageReference storageRef) {
        return Observable.fromEmitter(new Action1<Emitter<StreamDownloadTask.TaskSnapshot>>() {
            @Override
            public void call(Emitter<StreamDownloadTask.TaskSnapshot> taskSnapshotEmitter) {
                RxHandler.assignOnTask(taskSnapshotEmitter, storageRef.getStream());
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<StreamDownloadTask.TaskSnapshot> getStream(@NonNull final StorageReference storageRef,
                                                                        @NonNull final StreamDownloadTask.StreamProcessor processor) {
        return Observable.fromEmitter(new Action1<Emitter<StreamDownloadTask.TaskSnapshot>>() {
            @Override
            public void call(Emitter<StreamDownloadTask.TaskSnapshot> taskSnapshotEmitter) {
                RxHandler.assignOnTask(taskSnapshotEmitter, storageRef.getStream(processor));
            }
        }, Emitter.BackpressureMode.LATEST);
    }


    @NonNull
    public static Observable<UploadTask.TaskSnapshot> putBytes(@NonNull final StorageReference storageRef,
                                                               @NonNull final byte[] bytes) {
        return Observable.fromEmitter(new Action1<Emitter<UploadTask.TaskSnapshot>>() {
            @Override
            public void call(Emitter<UploadTask.TaskSnapshot> taskSnapshotEmitter) {
                RxHandler.assignOnTask(taskSnapshotEmitter, storageRef.putBytes(bytes));
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<UploadTask.TaskSnapshot> putBytes(@NonNull final StorageReference storageRef,
                                                               @NonNull final byte[] bytes,
                                                               @NonNull final StorageMetadata metadata) {
        return Observable.fromEmitter(new Action1<Emitter<UploadTask.TaskSnapshot>>() {
            @Override
            public void call(Emitter<UploadTask.TaskSnapshot> taskSnapshotEmitter) {
                RxHandler.assignOnTask(taskSnapshotEmitter, storageRef.putBytes(bytes, metadata));
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<UploadTask.TaskSnapshot> putFile(@NonNull final StorageReference storageRef,
                                                              @NonNull final Uri uri) {
        return Observable.fromEmitter(new Action1<Emitter<UploadTask.TaskSnapshot>>() {
            @Override
            public void call(Emitter<UploadTask.TaskSnapshot> taskSnapshotEmitter) {
                RxHandler.assignOnTask(taskSnapshotEmitter, storageRef.putFile(uri));
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<UploadTask.TaskSnapshot> putFile(@NonNull final StorageReference storageRef,
                                                              @NonNull final Uri uri,
                                                              @NonNull final StorageMetadata metadata) {
        return Observable.fromEmitter(new Action1<Emitter<UploadTask.TaskSnapshot>>() {
            @Override
            public void call(Emitter<UploadTask.TaskSnapshot> taskSnapshotEmitter) {
                RxHandler.assignOnTask(taskSnapshotEmitter, storageRef.putFile(uri, metadata));
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<UploadTask.TaskSnapshot> putFile(@NonNull final StorageReference storageRef,
                                                              @NonNull final Uri uri,
                                                              @NonNull final StorageMetadata metadata,
                                                              @NonNull final Uri existingUploadUri) {
        return Observable.fromEmitter(new Action1<Emitter<UploadTask.TaskSnapshot>>() {
            @Override
            public void call(Emitter<UploadTask.TaskSnapshot> taskSnapshotEmitter) {
                RxHandler.assignOnTask(taskSnapshotEmitter, storageRef.putFile(uri, metadata, existingUploadUri));
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<UploadTask.TaskSnapshot> putStream(@NonNull final StorageReference storageRef,
                                                                @NonNull final InputStream stream,
                                                                @NonNull final StorageMetadata metadata) {
        return Observable.fromEmitter(new Action1<Emitter<UploadTask.TaskSnapshot>>() {
            @Override
            public void call(Emitter<UploadTask.TaskSnapshot> taskSnapshotEmitter) {
                RxHandler.assignOnTask(taskSnapshotEmitter, storageRef.putStream(stream, metadata));
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<UploadTask.TaskSnapshot> putStream(@NonNull final StorageReference storageRef,
                                                                @NonNull final InputStream stream) {
        return Observable.fromEmitter(new Action1<Emitter<UploadTask.TaskSnapshot>>() {
            @Override
            public void call(Emitter<UploadTask.TaskSnapshot> taskSnapshotEmitter) {
                RxHandler.assignOnTask(taskSnapshotEmitter, storageRef.putStream(stream));
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<StorageMetadata> updateMetadata(@NonNull final StorageReference storageRef,
                                                             @NonNull final StorageMetadata metadata) {
        return Observable.fromEmitter(new Action1<Emitter<StorageMetadata>>() {
            @Override
            public void call(Emitter<StorageMetadata> storageMetadataEmitter) {
                RxHandler.assignOnTask(storageMetadataEmitter, storageRef.updateMetadata(metadata));
            }
        }, Emitter.BackpressureMode.LATEST);
    }

    @NonNull
    public static Observable<Void> delete(@NonNull final StorageReference storageRef) {
        return Observable.fromEmitter(new Action1<Emitter<Void>>() {
            @Override
            public void call(Emitter<Void> voidEmitter) {
                RxHandler.assignOnTask(voidEmitter, storageRef.delete());
            }
        }, Emitter.BackpressureMode.LATEST);
    }


}
