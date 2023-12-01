package com.ra.model.dao.product;

import com.ra.model.dao.category.CategoryDAO;
import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import com.ra.uitl.ConnectionDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO{
    @Autowired
    private CategoryDAO categoryDAO;
    @Override
    public List<Product> findAll() {
        Connection connection = ConnectionDatabase.openConnection();
        List<Product> list = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM product");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("id"));
                product.setProductName(rs.getString("name_product"));
                product.setPrice(rs.getDouble("price"));
                Category category = categoryDAO.findById(rs.getInt("category_id"));
                product.setCategory(category);
                list.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return list;
    }
}
