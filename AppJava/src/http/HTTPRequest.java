package http;

import http.HTTPResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPRequest {
    private String url;
    private String method = "GET";
    private String payload = null;
    private String contentType = "application/json";

    public HTTPRequest(String url) {
        this.url = url;
    }

    public void setMethod(String method) {
        this.method = method.toUpperCase();
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public void setPayload(Object obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        this.payload = mapper.writeValueAsString(obj);
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public HTTPResponse send() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", contentType);
        connection.setRequestProperty("Accept", "application/json");

        if (payload != null && !method.equals("GET")) {
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                os.write(payload.getBytes("UTF-8"));
            }
        }

        int responseCode = connection.getResponseCode();
        InputStream is = (responseCode >= 200 && responseCode < 300)
                ? connection.getInputStream()
                : connection.getErrorStream();

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }
        }

        return new HTTPResponse(responseCode, response.toString());
    }
}
