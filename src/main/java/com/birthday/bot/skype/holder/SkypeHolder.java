package com.birthday.bot.skype.holder;

import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.SkypeBuilder;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.exceptions.InvalidCredentialsException;
import com.samczsun.skype4j.exceptions.NotParticipatingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Holder for skype instance
 */
public class SkypeHolder {

  private static final Logger LOGGER = LoggerFactory.getLogger(SkypeHolder.class);

  private static Skype instance;
  private static String login;
  private static String password;

  public static void reBuildSkype() {
    synchronized (SkypeHolder.class) {
      checkLoginPassword();
      logout();
      final Skype skype = new SkypeBuilder(login, password).withAllResources().build();
      try {
        LOGGER.info("try skype login...");
        skype.login();
        LOGGER.info("Login is success");
      } catch (InvalidCredentialsException | ConnectionException | NotParticipatingException e) {
        LOGGER.error("Skype login failed, application wil be shutdown", e);
        System.exit(0);
      }
      instance = skype;
      LOGGER.info("Skype rebuild is success");
    }
  }

  private static void checkLoginPassword() {
    if (login == null || password == null) {
      throw new IllegalStateException("Skype login or password do not set." +
              " May be you didn'r call init method");
    }
  }

  private static void logout() {
    if (instance == null) {
      return;
    }
    try {
      LOGGER.info("Try logout");
      instance.logout();
      LOGGER.info("Logout is success");
    } catch (Exception e) {
      LOGGER.error("Logout failed, but skype instance will be rebuild", e);
    } finally {
      instance = null;
    }
  }

  public static void init(final String pLogin, final String pPassword) {
    synchronized (SkypeHolder.class) {
      login = pLogin;
      password = pPassword;
      reBuildSkype();
    }
  }

  public static synchronized Skype getSkype() {
    return instance;
  }
}
