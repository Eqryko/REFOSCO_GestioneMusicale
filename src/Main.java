/*
__________        _____                           ___________            .__
\______   \ _____/ ____\____  ______ ____  ____   \_   _____/ ___________|__| ____  ____
 |       _// __ \   __\/  _ \/  ___// ___\/  _ \   |    __)_ /    \_  __ \  |/ ___\/  _ \
 |    |   \  ___/|  | (  <_> )___ \\  \__(  <_> )  |        \   |  \  | \/  \  \__(  <_> )
 |____|_  /\___  >__|  \____/____  >\___  >____/  /_______  /___|  /__|  |__|\___  >____/
        \/     \/                \/     \/                \/     \/              \/
Classe 5BII
TPSIT
*/

import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        API api = new API();
        Scanner scan = new Scanner(System.in);

        Database db = null;
        try{
            db = Database.getIstance();
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            System.exit(-1);
        }

        System.out.println("MENU Operazioni \n0. Stato API\n1. Lista Artisti\n2. Dettaglio artista\n3. Canzoni artista\n4. Crea artista\n5. Aggiorna artista\n6. Elimina artista\n7. Lista canzoni\n8. Dettagli canzone\n9. Artisti salvati nel DB\n10. Exit");
        System.out.print("Scelta (numero): ");
        int choice = scan.nextInt();
        System.out.println("-----------------------------------------");

        int id;
        switch(choice){
            case 0:
                System.out.println("0.Stato di salute API");
                System.out.println(api.fetchStato());
                break;
            case 1:
                System.out.println("1.Lista di tutti gli artisti");
                System.out.println(api.fetchArtisti());
                break;
            case 2:
                System.out.print("2.Dettaglio del singolo artista\nID Artista: ");
                id = scan.nextInt();

                // otteniamo JSON completo
                String jsonArtista = apiGetRaw("/artisti/" + id);
                if (jsonArtista == null) break;

                Gson gson = new Gson();
                Artista artista = gson.fromJson(jsonArtista, Artista.class);
                System.out.println(artista);

                System.out.print("Vuoi salvare questo artista nel database locale? (s/n): ");
                scan.nextLine();
                String resp = scan.nextLine();
                if (resp.equalsIgnoreCase("s")) {
                    db.insertArtista(artista);
                }
                break;
            case 3:
                System.out.print("3.Canzoni di un artista\nID Artista: ");
                id = scan.nextInt();
                System.out.println(api.fetchArtistaCanzoni(id));
                break;
            case 4:
                System.out.println("4.Crea Artista");
                System.out.println("Inserisci il nome dell'artista: ");
                scan.nextLine();
                String Anome = scan.nextLine();
                System.out.println("Inserisci il paese dell'artista: ");
                String Apaese = scan.nextLine();
                System.out.println("Inserisci il genere musicale dell'artista: ");
                String Agenere = scan.nextLine();

                // crea via API
                api.creaArtista(Anome, Apaese, Agenere);

                // recupera l'artista appena creato per salvarlo nel DB (facoltativo)
                System.out.print("Vuoi salvare anche questo artista nel database locale? (s/n): ");
                String salva = scan.nextLine();
                if (salva.equalsIgnoreCase("s")) {
                    String jsonUltimi = apiGetRaw("/artisti");
                    Artista[] artisti = new Gson().fromJson(jsonUltimi, Artista[].class);
                    Artista ultimo = artisti[artisti.length - 1];
                    db.insertArtista(ultimo);
                }
                break;
            case 5:
                System.out.println("5.Aggiorna Artista");

                System.out.print("ID Artista: ");
                id = scan.nextInt();
                System.out.println("Inserisci il nome dell'artista: ");
                scan.nextLine();
                Anome = scan.nextLine();
                System.out.println("Inserisci il paese dell'artista: ");
                Apaese = scan.nextLine();
                System.out.println("Inserisci il genere musicale dell'artista: ");
                Agenere = scan.nextLine();

                api.aggiornaArtista(id, Anome, Apaese, Agenere);
                break;
            case 6:
                System.out.println("6.Eliminazione Artista");
                System.out.print("ID Artista: ");
                id = scan.nextInt();
                api.eliminaArtista(id);
                break;
            case 7:
                System.out.println("7.Elenco canzoni");
                System.out.println(api.fetchCanzoni());
                break;
            case 8:
                System.out.print("8.Dettagli canzone\nID Canzone:");
                id = scan.nextInt();
                System.out.println(api.fetchCanzone(id));
                break;
            case 9:
                System.out.println("9.Artisti salvati localmente:");
                db.printArtisti();
                break;
            case 10:
                System.out.println("USCITA");
                System.exit(0);
                break;
            default:
                System.out.println("Selezione non valida");
                break;
        }
        System.out.println("-----------------------------------------");
    }

    // piccolo helper per ottenere la risposta JSON da API
    private static String apiGetRaw(String path) {
        try {
            java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
            java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                    .uri(java.net.URI.create("http://localhost:4567/api" + path))
                    .GET()
                    .build();
            java.net.http.HttpResponse<String> response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            System.err.println("Errore nella richiesta diretta API: " + e.getMessage());
            return null;
        }
    }
}
