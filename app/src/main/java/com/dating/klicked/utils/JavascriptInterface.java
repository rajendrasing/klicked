package com.dating.klicked.utils;

import com.dating.klicked.Activity.CallActivity;

import org.jetbrains.annotations.NotNull;

import kotlin.jvm.internal.Intrinsics;

public final class JavascriptInterface {
    @NotNull
    private final CallActivity callActivity;

    @android.webkit.JavascriptInterface
    public final void onPeerConnected() {
        this.callActivity.onPeerConnected();
    }

    @NotNull
    public final CallActivity getCallActivity() {
        return this.callActivity;
    }

    public JavascriptInterface(@NotNull CallActivity callActivity) {
        Intrinsics.checkParameterIsNotNull(callActivity, "callActivity");

        this.callActivity = callActivity;
    }
}
