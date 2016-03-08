package com.birthday.bot.skype.settings.loader;

import com.birthday.bot.skype.settings.BirthdayBot;
import org.xml.sax.SAXException;

import javax.annotation.Nonnull;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.birthday.bot.tools.Const.*;

public class BirthdayBotSettings {

    private static final BirthdayBotSettings INSTANCE = new BirthdayBotSettings();
    private BirthdayBot birthdayBot;

    private BirthdayBotSettings() {
    }

    public static BirthdayBotSettings getInstance() {
        return INSTANCE;
    }

    public static final String XSD_SCHEMA = "/settings/settings.xsd";
    public static final String DEFAULT_CONFIGURATION_PATH = USER_DIR+"\\settings\\settings.xml";

    /**
     * Configuration loads preconfigured values
     *
     * @return not null configuration
     */
    @Nonnull
    synchronized public BirthdayBot getConfiguration() {
        if (birthdayBot == null) {
            final InputStream inputStream;
            try {
                inputStream = new FileInputStream(DEFAULT_CONFIGURATION_PATH);
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Configuration can not find", e);
            }

            if (inputStream == null) {
                birthdayBot = new BirthdayBot();
            } else {
                birthdayBot = loadConfiguration(inputStream);
            }
        }

        return birthdayBot;
    }

    @Nonnull
    private BirthdayBot loadConfiguration(@Nonnull InputStream inputStream) {
        try {
            Unmarshaller unmarshaller = getJaxbContext().createUnmarshaller();
            unmarshaller.setSchema(getSchema());

            BirthdayBot unmarshalledResult = (BirthdayBot) unmarshaller.unmarshal(inputStream);

            return unmarshalledResult;
        } catch (Exception e) {
            throw new RuntimeException("Configuration " + DEFAULT_CONFIGURATION_PATH + " contains mistakes and cannot be parsed", e);
        }
    }

    private Schema getSchema() throws SAXException {
        final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        return factory.newSchema(this.getClass().getResource(XSD_SCHEMA));
    }

    private JAXBContext getJaxbContext() throws JAXBException {
        return JAXBContext.newInstance(BirthdayBot.class);
    }

}