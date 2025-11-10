import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class API {
    private HttpClient client = null;
    private final String endpointBase = "http://www.localhost:4567/api/";

    public API(){
        client = HttpClient.newHttpClient();
    }

    public String fetchArtisti(){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpointBase + "artisti"))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //DESERIALIZZARE
            Gson deserializzatore = new Gson();
            Artista[] artisti = deserializzatore.fromJson(response.body(), Artista[].class);

            //CREARE STRINGA
            StringBuilder result = new StringBuilder();
            for(Artista artista : artisti){
                result.append(artista.toString());
            }

            return result.toString();

        } catch (IOException | InterruptedException e) {
            System.err.println("Errore nella richiesta API: " + e.getMessage());
            return null;
        }
    }

    public String fetchArtista(int id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpointBase + "artisti/" + id))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //DESERIALIZZARE
            Gson deserializzatore = new Gson();
            Artista artista = deserializzatore.fromJson(response.body(), Artista.class);

            return artista.toString();

        } catch (IOException | InterruptedException e) {
            System.err.println("Errore nella richiesta API: " + e.getMessage());
            return null;
        }


    }
}