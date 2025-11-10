import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class API {
    private HttpClient client = null;
    private final String endpointBase = "http://localhost:4567/api/";

    public API(){
        client = HttpClient.newHttpClient();
    }

    public String fetchStato() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpointBase + "health"))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson deserializzatore = new Gson();
            Stato stato = deserializzatore.fromJson(response.body(), Stato.class);

            return stato.toString();

        } catch (IOException | InterruptedException e) {
            System.err.println("Errore nella richiesta API: " + e.getMessage());
            return null;
        }
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

    public String fetchArtistaCanzoni(int id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpointBase + "artisti/" + id + "/canzoni"))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //DESERIALIZZARE
            Gson deserializzatore = new Gson();
            Canzone[] canzoni = deserializzatore.fromJson(response.body(), Canzone[].class);

            //CREARE STRINGA
            StringBuilder result = new StringBuilder();
            for(Canzone canzone : canzoni){
                result.append(canzone.toString());
            }

            return result.toString();

        } catch (IOException | InterruptedException e) {
            System.err.println("Errore nella richiesta API: " + e.getMessage());
            return null;
        }
    }

    public void creaArtista(String nome, String paese, String genere) {
        try {
            JsonObject nuovoArtista = new JsonObject();
            nuovoArtista.addProperty("nome", nome);
            nuovoArtista.addProperty("paese", paese);
            nuovoArtista.addProperty("genere", genere);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpointBase + "artisti"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(nuovoArtista.toString()))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201 || response.statusCode() == 200) {
                System.out.println("Artista creato con successo: " + response.body());
            } else {
                System.err.println("Errore nella creazione dell’artista. Codice: " + response.statusCode());
                System.err.println("Risposta: " + response.body());
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Errore nella richiesta API: " + e.getMessage());
        }
    }

    public void aggiornaArtista(int id, String nome, String paese, String genere) {
        try {
            JsonObject nuovoArtista = new JsonObject();
            nuovoArtista.addProperty("nome", nome);
            nuovoArtista.addProperty("paese", paese);
            nuovoArtista.addProperty("genere", genere);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpointBase + "artisti/" + id))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(nuovoArtista.toString()))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201 || response.statusCode() == 200) {
                System.out.println("Artista aggiornato con successo: " + response.body());
            } else {
                System.err.println("Errore nell'aggiornamento dell’artista. Codice: " + response.statusCode());
                System.err.println("Risposta: " + response.body());
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Errore nella richiesta API: " + e.getMessage());
        }
    }

    public void eliminaArtista(int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpointBase + "artisti/" + id))
                    .DELETE()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 204 || response.statusCode() == 200) {
                System.out.println("Artista eliminato con successo");
            } else {
                System.err.println("Errore nell'eliminazione dell’artista. Codice: " + response.statusCode());
                System.err.println("Risposta: " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Errore nella richiesta API: " + e.getMessage());
        }
    }

    public String fetchCanzoni(){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpointBase + "canzoni"))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //DESERIALIZZARE
            Gson deserializzatore = new Gson();
            Canzone[] canzoni = deserializzatore.fromJson(response.body(), Canzone[].class);

            //CREARE STRINGA
            StringBuilder result = new StringBuilder();
            for(Canzone canzone : canzoni){
                result.append(canzone.toString());
            }

            return result.toString();

        } catch (IOException | InterruptedException e) {
            System.err.println("Errore nella richiesta API: " + e.getMessage());
            return null;
        }
    }

    public String fetchCanzone(int id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpointBase + "canzoni/" + id))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //DESERIALIZZARE
            Gson deserializzatore = new Gson();
            Canzone canzone = deserializzatore.fromJson(response.body(), Canzone.class);

            return canzone.toString();

        } catch (IOException | InterruptedException e) {
            System.err.println("Errore nella richiesta API: " + e.getMessage());
            return null;
        }
    }
}