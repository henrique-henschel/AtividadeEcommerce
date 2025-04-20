package com.unicesumar.repository;

import com.unicesumar.model.*;
import com.unicesumar.paymentMethods.PaymentMethodFactory;
import com.unicesumar.paymentMethods.PaymentMethod;
import com.unicesumar.paymentMethods.PaymentType;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class SaleRepository implements EntityRepository<Sale> {
    private final Connection connection;
    private final ProductRepository productRepo;

    public SaleRepository(Connection connection) {
        this.connection  = connection;
        this.productRepo = new ProductRepository(connection);
    }

    @Override
    public void save(Sale sale) {
        String insertSale = "INSERT INTO sales (id, user_id, payment_method, sale_date) VALUES (?, ?, ?, ?);";
        String insertLink = "INSERT INTO sale_products (sale_id, product_id) VALUES (?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSale)) {
            preparedStatement.setString(1, sale.getUuid().toString());
            preparedStatement.setString(2, sale.getUserId().toString());
            preparedStatement.setString(3, sale.getPaymentMethod().getType().toString());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(sale.getSaleDate()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (PreparedStatement preparedStatement2 = connection.prepareStatement(insertLink)) {
            for (Product p : sale.getProductList()) {
                preparedStatement2.setString(1, sale.getUuid().toString());
                preparedStatement2.setString(2, p.getUuid().toString());
                preparedStatement2.addBatch();
            }
            preparedStatement2.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Sale> findById(UUID id) {
        String sqlSale = "SELECT * FROM sales WHERE id = ?;";
        String sqlProducts = "SELECT product_id FROM sale_products WHERE sale_id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSale)) {
            preparedStatement.setObject(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }

                UUID userId = UUID.fromString(resultSet.getString("user_id"));
                PaymentType paymentType = PaymentType.valueOf(resultSet.getString("payment_method"));
                LocalDateTime saleDate = resultSet.getTimestamp("sale_date").toLocalDateTime();
                List<Product> products = new ArrayList<>();

                try (PreparedStatement preparedStatement2 = connection.prepareStatement(sqlProducts)) {
                    preparedStatement2.setString(1, id.toString());
                    ResultSet resultSet2 = preparedStatement2.executeQuery();
                    while (resultSet2.next()) {
                        UUID pid = UUID.fromString(resultSet2.getString("product_id"));
                        productRepo.findById(pid).ifPresent(products::add);
                    }
                }

                PaymentMethod paymentMethod = PaymentMethodFactory.create(paymentType);
                Sale sale = new Sale(id, userId, products, paymentMethod, saleDate);
                return Optional.of(sale);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Sale> findAll() {
        String sql = "SELECT id FROM sales;";
        List<Sale> sales = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {
             while (resultSet.next()) {
                 UUID id = UUID.fromString(resultSet.getString("id"));
                 findById(id).ifPresent(sales::add);
             }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return sales;
    }

    @Override
    public void deleteById(UUID id) {
        String delLinks = "DELETE FROM sale_products WHERE sale_id = ?;";
        String delSale = "DELETE FROM sales WHERE id = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(delLinks)) {
            stmt.setString(1, id.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (PreparedStatement stmt2 = connection.prepareStatement(delSale)) {
            stmt2.setString(1, id.toString());
            stmt2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
