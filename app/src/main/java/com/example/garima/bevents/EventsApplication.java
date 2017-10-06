package com.example.garima.bevents;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources.Theme;

/**
 * Created by Garima on 06-10-2017.
 */

public class EventsApplication extends Application {
    private static EventsApplication eventsApplication;

    private EventsApplication() {
    }

    public static EventsApplication getInstance() {
        if (eventsApplication == null) {
            eventsApplication = new EventsApplication();
        }
        return eventsApplication;
    }

    public static Theme getApplicationTheme() {
        return getInstance().getApplicationContext().getTheme();
    }
}
