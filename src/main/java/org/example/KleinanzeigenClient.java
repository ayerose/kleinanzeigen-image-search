package org.example;
import okhttp3.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.type.TypeReference;
import org.example.Listing;

import java.util.*;
// http client

public class KleinanzeigenClient {
    private final OkHttpClient http = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String baseUrl;

    public KleinanzeigenClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public List<Listing> search(String query, String location, Integer radius, Integer minPrice, Integer maxPrice, Integer pageCount) throws Exception {
        HttpUrl.Builder url = HttpUrl.parse(baseUrl + "/inserate").newBuilder();
        if (query != null) url.addQueryParameter("query", query);
        if (location != null) url.addQueryParameter("location", location);
        if (radius != null) url.addQueryParameter("radius", String.valueOf(radius));
        if (minPrice != null) url.addQueryParameter("min_price", String.valueOf(minPrice));
        if (maxPrice != null) url.addQueryParameter("max_price", String.valueOf(maxPrice));
        if (pageCount != null) url.addQueryParameter("page_count", String.valueOf(pageCount));

        Request req = new Request.Builder().url(url.build()).get().build();
        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) throw new RuntimeException("request failed: " + res.code());
            String body = res.body().string();
            JsonNode root = mapper.readTree(body);
            if (!root.path("success").asBoolean(false)) return Collections.emptyList();
            JsonNode data = root.path("data");
            return mapper.readValue(mapper.treeAsTokens(data), new TypeReference<List<Listing>>(){});
        }
    }
}
