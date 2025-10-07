package org.example;

import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        KleinanzeigenClient client = new KleinanzeigenClient("http://localhost:8000");
        List<Listing> results = client.search("lululemon", "10115", 20, null, null, 1);

        System.out.println("Results: " + results.size());
        results.stream().limit(15).forEach(listing ->
                System.out.println(listing.title + " — " + listing.price + "€ — " + listing.url)
        );
    }
}
