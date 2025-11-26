package external;

import global.enums.ErrorMessage;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DiscordClient implements WebhookClient {
    private final HttpClient httpClient;
    private final String webhookUrl;
    private final boolean enabled;

    public DiscordClient(HttpClient httpClient, String webhookUrl) {
        this.httpClient = httpClient;
        this.webhookUrl = webhookUrl;
        this.enabled = validateUrl(webhookUrl);
    }

    @Override
    public void sendMessage(String message) {

        if (!enabled) {
            return;
        }

        String jsonBody = buildJsonBody(message);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(webhookUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();

            if (statusCode < 200 || statusCode >= 300) {
                System.err.println(ErrorMessage.INVALID_REQUEST.getMessage() + statusCode);
                System.err.println(response.body());
            }

        } catch (IOException | InterruptedException e) {
            System.err.println(ErrorMessage.FAIL_REQUEST.getMessage() + e.getMessage());
        }
    }

    private String buildJsonBody(String message) {
        return """
                {
                 "content": "%s"
                }
                """.formatted(escapeJson(message));
    }

    private String escapeJson(String text) {
        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n");
    }

    private boolean validateUrl(String webhookUrl) {
        boolean isUrl = (webhookUrl != null && !webhookUrl.isBlank());
        if (!isUrl) {
            System.out.println(ErrorMessage.INVALID_SYSTEM_VALUES.getMessage());
        }
        return isUrl;
    }
}
