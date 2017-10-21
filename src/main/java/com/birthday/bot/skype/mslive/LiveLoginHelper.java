package com.birthday.bot.skype.mslive;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class LiveLoginHelper {
  protected static class FailureReason extends Exception {
    private final String reason;
    private final String code;

    protected FailureReason(String reason, String code) {
      this.reason = reason;
      this.code = code;
    }

    public String getCode() {
      return code;
    }

    public String getReason() {
      return reason;
    }

    @Override
    public String getMessage() {
      return String.format("%s: %s", this.code, this.reason);
    }

  }

  private static Map<String, String> parsePayload(String payload) throws Exception {
    JSONObject response = org.json.XML
            .toJSONObject(payload)
            .getJSONObject("S:Envelope")
            .getJSONObject("S:Body");

    if (!(response.isNull("S:Fault"))) {
      String reason = response
              .getJSONObject("S:Fault")
              .getJSONObject("S:Reason")
              .getJSONObject("S:Text")
              .getString("content");

      String code = response
              .getJSONObject("S:Fault")
              .getJSONObject("S:Detail")
              .getJSONObject("psf:error")
              .getString("psf:value");

      throw new FailureReason(reason, code);
    }


    JSONObject a =  response
            .getJSONObject("wst:RequestSecurityTokenResponseCollection");
    JSONArray array = a.get("wst:RequestSecurityTokenResponse") instanceof JSONArray
            ? (JSONArray) a.get("wst:RequestSecurityTokenResponse")
            : new JSONArray(Arrays.asList(a.get("wst:RequestSecurityTokenResponse")));

    Map<String, String> tokens = new LinkedHashMap<>();

    array.forEach((Object obj) -> {
      JSONObject json = (JSONObject) obj;

      String content = json.getJSONObject("wst:RequestedSecurityToken")
              .getJSONObject("wsse:BinarySecurityToken")
              .getString("content")
              .replaceAll("&p=", "")
              .replaceAll("t=", "");

      String key = json.getJSONObject("wsp:AppliesTo")
              .getJSONObject("wsa:EndpointReference")
              .getString("wsa:Address")
              .replaceAll("&p=", "")
              .replaceAll("t=", "");

      tokens.put(key, content);
    });

    return tokens;
  }

  private static JSONObject getXTokenObjectFromAccess(String s) throws Exception {
    Connection.Response response = Jsoup.connect(RPS)
            .method(Connection.Method.POST)
            .data("scopes", "client")
            .data("clientVersion", "0/7.18.0.112//")
            .data("access_token", s)
            .data("partner", "999")
            .data("site_name", "lw")
            .ignoreContentType(true)
            .execute();

    return new JSONObject(response.body());
  }

  public static JSONObject getXTokenObject(String email, String password) throws Exception {
    OkHttpClient client = new OkHttpClient();
    MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("application/json; charset=utf-8");

    String created = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .format(new Date(System.currentTimeMillis()));

    String expires = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .format(new Date(System.currentTimeMillis() + (1000L * 60L * 60L * 24L * 14L)));

    String payload = String.format(LiveLoginHelper.SOAP_PAYLOAD,
            StringEscapeUtils.escapeXml11(email),
            StringEscapeUtils.escapeXml11(password),
            StringEscapeUtils.escapeXml11(created),
            StringEscapeUtils.escapeXml11(expires)
    );

    Request request = new Request.Builder()
            .url(LiveLoginHelper.SOAP_URL)
            .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, payload))
            .build();

    Response response = client.newCall(request).execute();

    if (response.code() == 200) {
      Map<String, String> parsedTokens = parsePayload(response.body().string());
      if (parsedTokens.containsKey(SCOPE)) {
        return getXTokenObjectFromAccess(parsedTokens.get(SCOPE));
      } else {
        throw new Exception("Deprecated key");
      }
    } else {
      throw new Exception("Bad response");
    }
  }

  public static final String SCOPE = "lw.skype.com";
  public static final String RPS = "https://api.skype.com/rps/skypetoken";
  public static final String SOAP_URL = "https://login.live.com:443/RST2.srf";
  public static final String SOAP_PAYLOAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
          "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:saml=\"urn:oasis:names:tc:SAML:1.0:assertion\" xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\" xmlns:wssc=\"http://schemas.xmlsoap.org/ws/2005/02/sc\" xmlns:wst=\"http://schemas.xmlsoap.org/ws/2005/02/trust\">\n" +
          "    <s:Header>\n" +
          "        <wsa:Action s:mustUnderstand=\"1\">http://schemas.xmlsoap.org/ws/2005/02/trust/RST/Issue</wsa:Action>\n" +
          "        <wsa:To s:mustUnderstand=\"1\">HTTPS://login.live.com:443//RST2.srf</wsa:To>\n" +
          "        <wsa:MessageID>0</wsa:MessageID>\n" +
          "        <ps:AuthInfo xmlns:ps=\"http://schemas.microsoft.com/Passport/SoapServices/PPCRL\" Id=\"PPAuthInfo\">\n" +
          "            <ps:HostingApp>{7108E71A-9926-4FCB-BCC9-9A9D3F32E423}</ps:HostingApp>\n" +
          "            <ps:BinaryVersion>5</ps:BinaryVersion>\n" +
          "            <ps:UIVersion>1</ps:UIVersion>\n" +
          "            <ps:Cookies />\n" +
          "            <ps:RequestParams>AQAAAAIAAABsYwQAAAAxMDMz</ps:RequestParams>\n" +
          "        </ps:AuthInfo>\n" +
          "        <wsse:Security>\n" +
          "            <wsse:UsernameToken Id=\"user\">\n" +
          "                <wsse:Username>%s</wsse:Username>\n" +
          "                <wsse:Password>%s</wsse:Password>\n" +
          "            </wsse:UsernameToken>\n" +
          "            <wsu:Timestamp Id=\"Timestamp\">\n" +
          "                <wsu:Created>%s</wsu:Created>\n" +
          "                <wsu:Expires>%s</wsu:Expires>\n" +
          "            </wsu:Timestamp>\n" +
          "        </wsse:Security>\n" +
          "    </s:Header>\n" +
          "    <s:Body>\n" +
          "        <ps:RequestMultipleSecurityTokens xmlns:ps=\"http://schemas.microsoft.com/Passport/SoapServices/PPCRL\" Id=\"RSTS\">\n" +
          "            <wst:RequestSecurityToken Id=\"RST0\">\n" +
          "                <wst:RequestType>http://schemas.xmlsoap.org/ws/2005/02/trust/Issue</wst:RequestType>\n" +
          "                <wsp:AppliesTo>\n" +
          "                    <wsa:EndpointReference>\n" +
          "                        <wsa:Address>" + SCOPE + "</wsa:Address>\n" +
          "                    </wsa:EndpointReference>\n" +
          "                </wsp:AppliesTo>\n" +
          "                <wsp:PolicyReference URI=\"MBI_SSL\" />\n" +
          "            </wst:RequestSecurityToken>\n" +
          "        </ps:RequestMultipleSecurityTokens>\n" +
          "    </s:Body>\n" +
          "</s:Envelope>";
}