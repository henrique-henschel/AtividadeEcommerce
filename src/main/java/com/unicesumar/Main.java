package com.unicesumar;

import com.unicesumar.controller.Controller;
import com.unicesumar.repository.ProductRepository;
import com.unicesumar.repository.SaleRepository;
import com.unicesumar.repository.UserRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:database.sqlite";

        try {
            Connection connection = DriverManager.getConnection(url);
            ProductRepository productRepository = new ProductRepository(connection);
            UserRepository userRepository = new UserRepository(connection);
            SaleRepository saleRepository = new SaleRepository(connection);

            Controller controller = new Controller(productRepository, userRepository, saleRepository);
            controller.run();

            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            System.exit(1);
        }
    }
}
