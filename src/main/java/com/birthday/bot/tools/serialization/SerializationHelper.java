package com.birthday.bot.tools.serialization;

/**
 * Created by Vsevolod Kaimashnikov on 28.02.2016.
 */
public interface SerializationHelper<T> {
    String toXML(final T object);

    T fromXML(final String xml);
}
