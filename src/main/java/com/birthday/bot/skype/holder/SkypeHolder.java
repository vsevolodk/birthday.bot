package com.birthday.bot.skype.holder;

import com.samczsun.skype4j.Skype;

/**
 * Holder for skype instance
 * Created by Vsevolod Kaimashnikov on 05.03.2016.
 */
public class SkypeHolder {
    private static Skype instance;

    public static void setInstance(final Skype skype) {
        synchronized (SkypeHolder.class) {
            instance = skype;
        }
    }

    public static Skype getSkype() {
        return instance;
    }
}
