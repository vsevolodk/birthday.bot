package com.birthday.bot.skype.chat;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity for serialization.
 * Created by Vsevolod Kaimashnikov on 01.03.2016.
 */
public class ChatForBDayState {

    private String identity;
    private String bDayHuman;
    private List<String> options;
    private String fileName;

    public ChatForBDayState(final String identity, final String bDayHuman) {
        this.identity = identity;
        this.bDayHuman = bDayHuman;
        options = new ArrayList<String>();
    }

    public List<String> getOptions() {
        return options;
    }

    public void addOption(final String option) {
        options.add(option);
    }

    public void clearOptions() {
        options.clear();
    }

    public ChatForBDayState() {}

    public String getbDayHuman() {
        return bDayHuman;
    }

    public String getIdentity() {
        return identity;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
