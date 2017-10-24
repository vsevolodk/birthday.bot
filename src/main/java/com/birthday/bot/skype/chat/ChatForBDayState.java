package com.birthday.bot.skype.chat;

import org.dizitart.no2.IndexType;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entity for serialization.
 * Created by Vsevolod Kaimashnikov on 01.03.2016.
 */
@Indices({
        @Index(value = "identity", type = IndexType.Unique)
})
public class ChatForBDayState implements Serializable {

    @Id
    private String identity;
    private String bDayHuman;
    private List<String> options;
    private String fileName;

    public ChatForBDayState(final String identity, final String bDayHuman) {
        this.identity = identity;
        this.bDayHuman = bDayHuman;
        this.options = new ArrayList<>();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatForBDayState that = (ChatForBDayState) o;

        return identity.equals(that.identity);
    }

    @Override
    public int hashCode() {
        return identity.hashCode();
    }

    @Override
    public String toString() {
        return "ChatForBDayState{" +
                "identity='" + identity + '\'' +
                ", bDayHuman='" + bDayHuman + '\'' +
                ", options=" + options +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
