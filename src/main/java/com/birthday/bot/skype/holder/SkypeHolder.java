package com.birthday.bot.skype.holder;

import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.SkypeBuilder;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.exceptions.InvalidCredentialsException;
import com.samczsun.skype4j.exceptions.NotParticipatingException;

/**
 * Holder for skype instance
 */
public class SkypeHolder {

  private static Skype instance;
  private static String login;
  private static String password;

  public static void reBuildSkype() {
    synchronized (SkypeHolder.class) {
      checkLoginPassword();
      logout();
      final Skype skype = new SkypeBuilder(login, password).withAllResources().build();
      try {
        System.out.println("try skype login...");
        skype.login();
        System.out.println("Login is success");
      } catch (InvalidCredentialsException | ConnectionException | NotParticipatingException e) {
        System.out.println("Skype login failed");
        e.printStackTrace();
        System.exit(0);
      }
      instance = skype;
      System.out.println("Skype rebuild is success");
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
      System.out.println("Try logout");
      instance.logout();
      System.out.println("Logout is success");
    } catch (ConnectionException e) {
      e.printStackTrace();
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

  public static Skype getSkype() {
    return instance;
  }
}
