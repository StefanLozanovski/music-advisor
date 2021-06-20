package advisor;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static advisor.Authorization.ACCESS_TOKEN;
import static advisor.Authorization.API_SERVER;

public class Content {

    private static final String CATEGORIES = "/v1/browse/categories";
    private static final String NEW = "/v1/browse/new-releases";
    private static final String FEATURED = "/v1/browse/featured-playlists";
    private static final String PLAYLIST = "/v1/browse/categories/";

    String request(String path) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .uri(URI.create(path))
                .GET()
                .build();
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            assert response != null;
            return response.body();
        } catch (InterruptedException | IOException e) {
            return "Error response";
        }
    }

    String getNewReleases() {
        List<Info> data = new ArrayList<>();
        String response = request(API_SERVER + NEW);
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        JsonObject categories = jsonObject.getAsJsonObject("albums");

        for (JsonElement item : categories.getAsJsonArray("items")) {
            Info element = new Info();
            element.setAlbum(item.getAsJsonObject().get("name").toString().replaceAll("\"", ""));
            StringBuilder artists = new StringBuilder("[");
            for (JsonElement name : item.getAsJsonObject().getAsJsonArray("artists")) {
                if (!artists.toString().endsWith("[")) {
                    artists.append(", ");
                }
                artists.append(name.getAsJsonObject().get("name"));
            }
            element.setName(artists.append("]").toString().replaceAll("\"", ""));
            element.setLink(item.getAsJsonObject().get("external_urls")
                    .getAsJsonObject().get("spotify")
                    .toString().replaceAll("\"", ""));
            data.add(element);
        }

        StringBuilder result = new StringBuilder();
        for (Info each : data) {
            result.append(each.album).append("\n")
                    .append(each.name).append("\n")
                    .append(each.link).append("\n")
                    .append("\n");
        }
        return result.toString();
    }

    String getFeatured() {
        List<Info> data = new ArrayList<>();
        String response = request(API_SERVER + FEATURED);
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        JsonObject categories = jsonObject.getAsJsonObject("playlists");

        for (JsonElement item : categories.getAsJsonArray("items")) {
            Info element = new Info();
            element.setAlbum(item.getAsJsonObject().get("name").toString().replaceAll("\"", ""));
            element.setLink(item.getAsJsonObject().get("external_urls")
                    .getAsJsonObject().get("spotify")
                    .toString().replaceAll("\"", ""));
            data.add(element);
        }

        StringBuilder result = new StringBuilder();
        for (Info each : data) {
            result.append(each.album).append("\n")
                    .append(each.link).append("\n")
                    .append("\n");
        }
        return result.toString();
    }

    String getCategories() {
        List<Info> data = new ArrayList<>();
        String response = request(API_SERVER + CATEGORIES);
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        JsonObject categories = jsonObject.getAsJsonObject("categories");

        for (JsonElement item : categories.getAsJsonArray("items")) {
            Info element = new Info();
            element.setCategories(item.getAsJsonObject().get("name").toString().replaceAll("\"", ""));
            data.add(element);
        }

        StringBuilder result = new StringBuilder();
        for (Info each : data) {
            result.append(each.categories).append("\n").append("\n");
        }
        return result.toString();
    }

    String getPlaylists(String categoryName) {
        List<Info> data = new ArrayList<>();
        String response = request(API_SERVER + CATEGORIES);
        String categoryId = "Unknown category name.";
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        JsonObject categories = jsonObject.getAsJsonObject("categories");

        for (JsonElement item : categories.getAsJsonArray("items")) {
            if (item.getAsJsonObject().get("name").toString().replaceAll("\"", "").equals(categoryName)){
                categoryId = item.getAsJsonObject().get("id").toString().replaceAll("\"", "");
                break;
            }
        }
        if (categoryId.equals("Unknown category name.")) {
            return categoryId;
        }
        response = request(API_SERVER + PLAYLIST + categoryId + "/playlists");
        System.out.println(response);
        if (response.contains("Test unpredictable error message")) {
            return "Test unpredictable error message";
        }
        jsonObject = JsonParser.parseString(response).getAsJsonObject();
        categories = jsonObject.getAsJsonObject("playlists");
        for (JsonElement item : categories.getAsJsonArray("items")) {
            Info element = new Info();
            element.setAlbum(item.getAsJsonObject().get("name").toString().replaceAll("\"", ""));
            element.setLink(item.getAsJsonObject().get("external_urls")
                    .getAsJsonObject().get("spotify")
                    .toString().replaceAll("\"", ""));
            data.add(element);
        }

        StringBuilder result = new StringBuilder();
        for (Info each : data) {
            result.append(each.album).append("\n")
                    .append(each.link).append("\n")
                    .append("\n");
        }
        return result.toString();
    }
}