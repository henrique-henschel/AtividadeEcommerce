package com.unicesumar.repository;

import com.unicesumar.model.Product;

import java.sql.*;
import java.util.*;

public class ProductRepository implements EntityRepository<Product> {
    private final Connection connection;

    public ProductRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Product product) {
        String query = "INSERT INTO products VALUES (?, ?, ?);";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, product.getUuid().toString());
            stmt.setString(2, product.getName());
            stmt.setDouble(3, product.getPrice());
            stmt.executeUpdate();  // PERSISTIR os dados do objeto la no BD
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Product> findById(UUID id) {
        String query = "SELECT * FROM products WHERE uuid = ?;";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, id.toString());
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                // HIDRATACAO (construir objetos a partir dos dados persistidos la no BD)
                Product product = new Product(
                        UUID.fromString(resultSet.getString("uuid")),
                        resultSet.getString("name"),
                        resultSet.getDouble("price")
                );
                return Optional.of(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        String query = "SELECT * FROM products;";

        List<Product> products = new LinkedList<>();
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                // HIDRATACAO
                Product product = new Product(
                        UUID.fromString(resultSet.getString("uuid")),
                        resultSet.getString("name"),
                        resultSet.getDouble("price")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    @Override
    public void deleteById(UUID id) {
        String query = "DELETE FROM products WHERE uuid = ?;";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, id.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
