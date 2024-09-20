package dao;

import model.Musicas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MusicasDAO {

    private Connection connection;

    public MusicasDAO() {
        try {
            connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/CRUD_Musicas", "postgres", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Musicas musica) {
        String sql = "INSERT INTO \"Musicas\" (\"Nome\", \"Gênero\", \"Artista\", \"Ano\") VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, musica.getNome());
            stmt.setString(2, musica.getGenero());
            stmt.setString(3, musica.getArtista());
            stmt.setInt(4, musica.getAno());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Musicas get(int id) {
        Musicas musica = null;
        String sql = "SELECT * FROM \"Musicas\" WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                musica = new Musicas(rs.getInt("id"), rs.getString("Nome"), rs.getString("Gênero"), rs.getString("Artista"), rs.getInt("Ano"));
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return musica;
    }

    public void update(Musicas musica) {
        String sql = "UPDATE \"Musicas\" SET \"Nome\" = ?, \"Gênero\" = ?, \"Artista\" = ?, \"Ano\" = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, musica.getNome());
            stmt.setString(2, musica.getGenero());
            stmt.setString(3, musica.getArtista());
            stmt.setInt(4, musica.getAno());
            stmt.setInt(5, musica.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM \"Musicas\" WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Musicas> getAll() {
        List<Musicas> musicas = new ArrayList<>();
        String sql = "SELECT * FROM \"Musicas\" ORDER BY id ASC";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Musicas musica = new Musicas(rs.getInt("id"), rs.getString("Nome"), rs.getString("Gênero"),rs.getString("Artista"), rs.getInt("Ano"));
                musicas.add(musica);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return musicas;
    }
}
