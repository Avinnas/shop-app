package com.example.shopapp.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ExternalShopHandler {

    private final String apiToken;

    public ExternalShopHandler() {
        this.apiToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhbGxlZ3JvOmFwaTpvcmRlcnM6cmVhZCIsImFsbGVncm86YXBpOnByb2ZpbGU6d3JpdGUiLCJhbGxlZ3JvOmFwaTpzYWxlOm9mZmVyczp3cml0ZSIsImFsbGVncm86YXBpOmJpbGxpbmc6cmVhZCIsImFsbGVncm86YXBpOmNhbXBhaWducyIsImFsbGVncm86YXBpOmRpc3B1dGVzIiwiYWxsZWdybzphcGk6c2FsZTpvZmZlcnM6cmVhZCIsImFsbGVncm86YXBpOmJpZHMiLCJhbGxlZ3JvOmFwaTpvcmRlcnM6d3JpdGUiLCJhbGxlZ3JvOmFwaTphZHMiLCJhbGxlZ3JvOmFwaTpwYXltZW50czp3cml0ZSIsImFsbGVncm86YXBpOnNhbGU6c2V0dGluZ3M6d3JpdGUiLCJhbGxlZ3JvOmFwaTpwcm9maWxlOnJlYWQiLCJhbGxlZ3JvOmFwaTpyYXRpbmdzIiwiYWxsZWdybzphcGk6c2FsZTpzZXR0aW5nczpyZWFkIiwiYWxsZWdybzphcGk6cGF5bWVudHM6cmVhZCJdLCJhbGxlZ3JvX2FwaSI6dHJ1ZSwiZXhwIjoxNTkyMDg3MzYxLCJqdGkiOiI0YWViYjg3Yy1iYWExLTQ5ODQtOTZlZS1jNzdiYzM4NDIxMzUiLCJjbGllbnRfaWQiOiJmNjRmNWQyOTc0OTc0MjMyYmU3NzRmYWVlYWFhMGMwNSJ9.G6szjAVI51HPuSnBPGK-MJCpl-RwQdLUb-IXzay5R0uNqYEh6aBphvlVKIxNKAYVpaNAQq576pf952DOu0ejYJCGK0fyZ-heOU6Y5gepfg5pRFEdzjnHdMkoZI_JCIYqf7AqIXRYADbNdrW5lMTcy46fHm8f4N1BWIVUeTBwqbmOGHSnqIUtP40OGplsJHqc4BF0YCBVp-KJP2TI4VWq28BJHrkz6nBy3IqnX45jz-36wCX6nc297Bz-SL2StZrO8ozpY_WqftNrXNURhyyk1rFE8nFzgJqppP6FlB1UGLjJjVLATVWAMVtcpjvXTn7txax-tIHnl0PxFk8M2ghyKA";
    }

    public ArrayList<Product> getItemsAsListFromExternalShop(String phrase, int number) {
        phrase = phrase.replace(' ', '+');

        ArrayList<Product> products = new ArrayList<>(); // We can return list of products

        for (int i = 0; i < number; i++) {
            String searchMode = "&searchMode=REGULAR";
            String urlAsString = "https://api.allegro.pl/offers/listing?phrase=" + phrase + searchMode + "&offset=" + i + "&limit=1";
            String json = getJsonFromSearch(urlAsString);
//            System.out.println(json); // TMP
//            System.out.println(); // TMP

            Product product = extractProductFromJSON(json);
            products.add(product);
        }

        return products;
    }

    // productName nie moze miec polskich znakow
    public double getPriceOfProduct(String productName) {

        productName = productName.replace(' ', '+');
        String urlAsString = "https://api.allegro.pl/offers/listing?phrase=" + productName + "&limit=1";

        String productAsJSON = getJsonFromSearch(urlAsString);
        double productPrice = extractProductFromJSON(productAsJSON).getPrice();

        return productPrice;
    }

    private String getJsonFromSearch(String urlAsString) {
        try {

            URL url = new URL(urlAsString);
            HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");

            urlConnection.setRequestProperty("accept", "application/vnd.allegro.public.v1+json");
            urlConnection.setRequestProperty("authorization", "Bearer " + apiToken);
            urlConnection.connect();
            try(Scanner in = new Scanner(urlConnection.getInputStream())) {
                String json = in.nextLine();
                return json;
            }
        }
        catch(IOException exception) {
            exception.printStackTrace();
        }

        return "Something went wrong";
    }

    private Product extractProductFromJSON(String json) {

        JSONParser jsonParser = new JSONParser();
        Object object;

        Product product = new Product();

        try {
            object = jsonParser.parse(json);
            JSONObject jsonObject = (JSONObject) object;

            JSONObject items = (JSONObject) jsonObject.get("items");
            JSONArray promoted = (JSONArray) items.get("promoted");
            JSONArray regular = (JSONArray) items.get("regular");

//            if (promoted.size() < 0 && regular.size() < 0) {
//                // NO ITEM FOUND
//                return product;
//            }

            assert (promoted.size() > 0);
            for (Object promotedOne : promoted) {
                JSONObject promotedOneJSONObject = (JSONObject) promotedOne;

                String name = (String) promotedOneJSONObject.get("name");

                JSONObject sellingModeJSONObject = (JSONObject) promotedOneJSONObject.get("sellingMode");
                JSONObject priceJSONObject = (JSONObject) sellingModeJSONObject.get("price");
                String priceAsString = (String) priceJSONObject.get("amount");

                product.setName(name);
                product.setPrice(Float.parseFloat(priceAsString));
                product.setDescription("");
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return product;
    }
}
