package service;

import model.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AuthService {

    public static boolean autenticar(String login, String senha) throws Exception {
        URL url = new URL("http://localhost:8080/login");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // Enviar o JSON
        LoginDTO loginDTO = new LoginDTO(login, senha);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(loginDTO);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes(StandardCharsets.UTF_8));
        }

        return conn.getResponseCode() == 200;
    }
}
