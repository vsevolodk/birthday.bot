package com.birthday.bot.tools.serialization;

import com.thoughtworks.xstream.XStream;

/**
 * Created by Vsevolod Kaimashnikov on 28.02.2016.
 */
public class XStreamSerializationHelper<T> implements SerializationHelper<T> {

    private final XStream xStream = new XStream();

    @Override
    public String toXML(T object) {
        return xStream.toXML(object);
    }

    @Override
    public T fromXML(final String xml) {
        return (T) xStream.fromXML(xml);
    }
}
