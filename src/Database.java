import java.sql.*;

public class Database {
    private Connection connection;
    private static Database instance = null;

    private Database() throws SQLException {
        String url = "jdbc:sqlite:database/artisti.db"; // crea il file se non esiste
        connection = DriverManager.getConnection(url);
        System.out.println("Connected to database");

        // crea la tabella se non esiste
        String createTable = "CREATE TABLE IF NOT EXISTS artisti (" +
                "id INTEGER PRIMARY KEY," +
                "nome TEXT NOT NULL," +
                "paese TEXT," +
                "genere TEXT)";
        Statement st = connection.createStatement();
        st.execute(createTable);
    }

    public static Database getIstance() throws SQLException {
        if (instance == null)
            instance = new Database();
        return instance;
    }

    // --- INSERT ARTISTA ---
    public boolean insertArtista(Artista artista) {
        String query = "INSERT OR REPLACE INTO artisti (id, nome, paese, genere) VALUES (?, ?, ?, ?)";

        try {
            if (connection == null || !connection.isValid(5)) {
                System.err.println("Database not connected");
                return false;
            }

            PreparedStatement stnt = connection.prepareStatement(query);
            stnt.setInt(1, artista.getId());
            stnt.setString(2, artista.getNome());
            stnt.setString(3, artista.getPaese());
            stnt.setString(4, artista.getGenere());
            stnt.executeUpdate();

            System.out.println("Artista salvato nel database locale.");
        } catch (SQLException e) {
            System.err.println("Errore di query: " + e.getMessage());
            return false;
        }
        return true;
    }

    // --- SELECT ALL ARTISTI ---
    public String selectAllArtisti() {
        StringBuilder result = new StringBuilder();
        String query = "SELECT * FROM artisti";

        try {
            if (connection == null || !connection.isValid(5)) {
                System.err.println("Database not connected");
                return null;
            }

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet artisti = statement.executeQuery();

            while (artisti.next()) {
                result.append(artisti.getInt("id")).append("\t")
                        .append(artisti.getString("nome")).append("\t")
                        .append(artisti.getString("paese")).append("\t")
                        .append(artisti.getString("genere")).append("\n");
            }

        } catch (SQLException e) {
            System.err.println("Errore di query: " + e.getMessage());
            return null;
        }

        return result.toString();
    }

    // --- PRINT ARTISTI ---
    public boolean printArtisti() {
        String query = "SELECT * FROM artisti";

        try {
            if (connection == null || !connection.isValid(5)) {
                System.err.println("Database not connected");
                return false;
            }

            PreparedStatement stnt = connection.prepareStatement(query);
            ResultSet rs = stnt.executeQuery();

            System.out.println("=== ARTISTI LOCALI ===");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String paese = rs.getString("paese");
                String genere = rs.getString("genere");

                System.out.printf("%-3d  %-20s %-15s %-15s%n", id, nome, paese, genere);
            }

        } catch (SQLException e) {
            System.err.println("Errore di query: " + e.getMessage());
            return false;
        }
        return true;
    }
}
