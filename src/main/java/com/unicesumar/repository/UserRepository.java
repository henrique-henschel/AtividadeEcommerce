package com.unicesumar.repository;

import com.unicesumar.model.User;

import java.sql.*;
import java.util.*;

public class UserRepository implements EntityRepository<User> {
    private final Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(User entity) {
        String query = "INSERT INTO users VALUES (?, ?, ?, ?);";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, entity.getUuid().toString());
            stmt.setString(2, entity.getName());
            stmt.setString(3, entity.getEmail());
            stmt.setString(4, entity.getPassword());
            stmt.executeUpdate();  // PERSISTENCIA no DB
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findById(UUID id) {
        String query = "SELECT * FROM users WHERE uuid = ?;";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, id.toString());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                // HIDRATA o objeto com os dados vindos do BD
                return Optional.of(new User(
                        UUID.fromString(resultSet.getString("uuid")),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    public Optional<User> findByEmail(String email) {
        String query = "SELECT * from users WHERE email = ?;";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new User(
                        UUID.fromString(resultSet.getString("uuid")),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM users;";
        ArrayList<User> users = new ArrayList<>();

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                users.add(new User(
                        UUID.fromString(resultSet.getString("uuid")),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    @Override
    public void deleteById(UUID id) {
        String query = "DELETE FROM users WHERE uuid = ?;";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, id.toString());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
