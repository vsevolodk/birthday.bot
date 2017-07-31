package com.birthday.bot.skype.holder;

import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.exceptions.InvalidCredentialsException;
import com.samczsun.skype4j.exceptions.NotParticipatingException;

/**
 * Holder for skype instance
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

    public static void updateToken() {
        try {
            System.out.println("Start updating of skype token via skype4j login...");
            instance.login();
            System.out.println("Token is updated");
        } catch (ConnectionException | NotParticipatingException | InvalidCredentialsException e) {
            System.out.println("Updating of skype toke is failed");
            e.printStackTrace();
            System.exit(0);
        }
    }
}
