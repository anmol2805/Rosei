package com.anmol.rosei.Services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class NotifyService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public NotifyService(String name) {
        super("NotifyService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
