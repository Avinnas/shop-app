package com.example.shopapp.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class ExternalShopHandler {

    private final String apiToken;

    public ExternalShopHandler() {
        this.apiToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhbGxlZ3JvOmFwaTpvcmRlcnM6cmVhZCIsImFsbGVncm86YXBpOnByb2ZpbGU6d3JpdGUiLCJhbGxlZ3JvOmFwaTpzYWxlOm9mZmVyczp3cml0ZSIsImFsbGVncm86YXBpOmJpbGxpbmc6cmVhZCIsImFsbGVncm86YXBpOmNhbXBhaWducyIsImFsbGVncm86YXBpOmRpc3B1dGVzIiwiYWxsZWdybzphcGk6c2FsZTpvZmZlcnM6cmVhZCIsImFsbGVncm86YXBpOmJpZHMiLCJhbGxlZ3JvOmFwaTpvcmRlcnM6d3JpdGUiLCJhbGxlZ3JvOmFwaTphZHMiLCJhbGxlZ3JvOmFwaTpwYXltZW50czp3cml0ZSIsImFsbGVncm86YXBpOnNhbGU6c2V0dGluZ3M6d3JpdGUiLCJhbGxlZ3JvOmFwaTpwcm9maWxlOnJlYWQiLCJhbGxlZ3JvOmFwaTpyYXRpbmdzIiwiYWxsZWdybzphcGk6c2FsZTpzZXR0aW5nczpyZWFkIiwiYWxsZWdybzphcGk6cGF5bWVudHM6cmVhZCJdLCJhbGxlZ3JvX2FwaSI6dHJ1ZSwiZXhwIjoxNTkwOTYwMzExLCJqdGkiOiI0NjQ4NGQ5MS04OWM3LTRhZjctYmUyNi1kOWY0MGRjZGIwN2UiLCJjbGllbnRfaWQiOiJmNjRmNWQyOTc0OTc0MjMyYmU3NzRmYWVlYWFhMGMwNSJ9.KlQtRdilI7NnM23fxlAhdC2r26-S-ANqqrK9AzrFvEId23hKUVaM-u2jRinBDkRsBbrwYYClI6j0MK3fYrCSHV5xlFtcoFSXeT4wS2Td330zwc5ZtQcRPbco1GjqHjSix41Pzr0ahIQidiTYBMucecE92maSCBW5D-ol1q8ZUvsgiNuLLbBIbfpOd22JHhNmsgs-o3kFDUiI9WcL4RZe8nTkD4Ic27TcO1JkVaV3CCS7k-rWtlQr89uQyn56HVTdIPwjiU7vKf3QCIIvS0wUtmYSFmfmsCpVKdflRVtO5azX4HqcKZHsfXMmzZviABy8N_PoOtWxxV4jUoPjdFpa2A";
    }

    // productName nie moze miec polskich znakow
    public double getPriceOfProduct(String productName) {
        String productAsJSON = findProduct(productName);
        double productPrice = getPriceFromJSON(productAsJSON);

        return productPrice;
    }

    // znajduje produkt w zewnetrznym sklepie i zwraca go w postaci JSONa
    private String findProduct(String productName) {

        try {
            productName = productName.replace(' ', '+');
            String urlAsString = "https://api.allegro.pl/offers/listing?phrase=" + productName + "&limit=1";

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

    double getPriceFromJSON(String json) {

        String priceAsString = "empty-price";
        JSONParser jsonParser = new JSONParser();
        Object object;

        try {
            object = jsonParser.parse(json);
            JSONObject jsonObject = (JSONObject) object;

            JSONObject items = (JSONObject) jsonObject.get("items");
            JSONArray promoted = (JSONArray) items.get("promoted");

            for (Object o : promoted) {
                JSONObject o1 = (JSONObject) o;
                JSONObject o2 = (JSONObject) o1.get("sellingMode");
                JSONObject o3 = (JSONObject) o2.get("price");
                priceAsString = (String) o3.get("amount");
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return Double.parseDouble(priceAsString);
    }
}
