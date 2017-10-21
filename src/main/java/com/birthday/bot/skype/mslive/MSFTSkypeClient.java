package com.birthday.bot.skype.mslive;

import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.exceptions.InvalidCredentialsException;
import com.samczsun.skype4j.exceptions.NotParticipatingException;
import com.samczsun.skype4j.exceptions.handler.ErrorHandler;
import com.samczsun.skype4j.exceptions.handler.ErrorSource;
import com.samczsun.skype4j.internal.Endpoints;
import com.samczsun.skype4j.internal.SkypeThreadFactory;
import com.samczsun.skype4j.internal.client.FullClient;
import com.samczsun.skype4j.internal.threads.AuthenticationChecker;
import com.samczsun.skype4j.internal.threads.ServerPingThread;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class MSFTSkypeClient extends FullClient {
  public MSFTSkypeClient(String skypeToken, String skypeId,
                         Set<String> resources, Logger customLogger,
                         List<ErrorHandler> errorHandlers) {
    super(skypeId, null, resources, customLogger, errorHandlers);

    setSkypeToken(skypeToken);
  }

  @Override
  public void login() {
    try {
      /*HttpURLConnection asmResponse = null;
      try {
        asmResponse = this.getAsmToken();
      } catch (ConnectionException e) {
        throw new RuntimeException(e);
      }

      String[] setCookie = asmResponse.getHeaderField("Set-Cookie").split(";")[0].split("=");
      this.cookies.put(setCookie[0], setCookie[1]);*/

      loadAllContacts();

      try {
        this.getContactRequests(false);
      } catch (Exception var2) {
        this.handleError(ErrorSource.UPDATING_CONTACT_LIST, var2, false);
      }

      try {
        this.registerWebSocket();
      } catch (Exception var2) {
        this.handleError(ErrorSource.REGISTERING_WEBSOCKET, var2, false);
      }

      registerEndpoint();

      Endpoints.ELIGIBILITY_CHECK.open(this, new Object[0]).expect(200, "You are not eligible to use Skype for Web!").get();
      this.loggedIn.set(true);
      if(this.serverPingThread != null) {
        this.serverPingThread.kill();
        this.serverPingThread = null;
      }

      if(this.reauthThread != null) {
        this.reauthThread.kill();
        this.reauthThread = null;
      }

      if(this.scheduler != null) {
        this.scheduler.shutdownNow();

        while(true) {
          if(!this.scheduler.isTerminated()) {
            continue;
          }
        }
      }

      this.shutdownThread = Executors.newSingleThreadExecutor(new SkypeThreadFactory(this, "Shutdown"));
      this.scheduler = Executors.newFixedThreadPool(4, new SkypeThreadFactory(this, "Poller"));
      (this.serverPingThread = new ServerPingThread(this)).start();
      (this.reauthThread = new AuthenticationChecker(this)).start();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void reauthenticate() throws ConnectionException, InvalidCredentialsException, NotParticipatingException {
    doShutdown();

    login();

    if(subscribed.get())
      subscribe();
  }

  public static class Builder {
    private final String skypeToken;
    private final String skypeId;

    private Set<String> resources = new HashSet();
    private List<ErrorHandler> errorHandlers = new ArrayList();
    private Logger customLogger;

    public Builder(String skypeToken, String skypeId) {
      this.skypeToken = skypeToken;
      this.skypeId = skypeId;
    }

    public Builder withAllResources() {
      this.resources.addAll(Arrays.asList(new String[]{"/v1/users/ME/conversations/ALL/properties", "/v1/users/ME/conversations/ALL/messages", "/v1/users/ME/contacts/ALL", "/v1/threads/ALL"}));
      return this;
    }

    public Builder withResource(String resource) {
      this.resources.add(resource);
      return this;
    }

    public Builder withLogger(Logger logger) {
      this.customLogger = logger;
      return this;
    }

    public Builder withExceptionHandler(ErrorHandler errorHandler) {
      this.errorHandlers.add(errorHandler);
      return this;
    }

    public Skype build() {
      if(this.resources.isEmpty()) {
        throw new IllegalArgumentException("No resources selected");
      } else if(this.skypeToken != null) {
        return new MSFTSkypeClient(this.skypeToken, this.skypeId, this.resources, this.customLogger, this.errorHandlers);
      } else {
        throw new IllegalArgumentException("No skype token specified");
      }
    }
  }
}
