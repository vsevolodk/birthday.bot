package com.birthday.bot.tools.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Vsevolod Kaimashnikov on 28.02.2016.
 */
public class FileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    public static void write(final String fileName, final String text) {
        if (fileName == null) {
            LOGGER.error("Null file name", new NullPointerException());
        }

        final File file = new File(fileName);

        try {
            if (!file.exists()) {
                boolean newFileSuccess = file.createNewFile();
                if (newFileSuccess) {
                    LOGGER.info("New file {} creation is success", file.getName());
                } else {
                    LOGGER.error("New file {} creation is failed", file.getName());
                }
            }
            final PrintWriter out = new PrintWriter(file.getAbsoluteFile());

            try {
                out.print(text);
            } finally {
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, Charset.defaultCharset());
    }

    public static void delete(final String fileName) {

        final File file = new File(fileName);

        if (file.exists()) {
            boolean success = file.delete();
            if (success) {
                LOGGER.info("File {} deletion is success", file.getName());
            } else {
                LOGGER.error("File {} deletion is failed", file.getName());
            }
        }
    }
}
