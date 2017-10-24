package com.birthday.bot.skype;

import com.birthday.bot.skype.run.mode.RunMode;
import com.birthday.bot.skype.run.RunModeResolver;

/**
 * Main class for running bot
 * Created by Vsevolod on 19.02.2016.
 */
public class BirthDaySkypeBotRunner {

    public static void main(String[] args) {
        RunMode runMode = RunModeResolver.resolveRunMode(args);
        runMode.run(args);
    }
}
