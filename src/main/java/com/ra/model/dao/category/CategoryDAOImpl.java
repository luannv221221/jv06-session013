package com.ra.model.dao.category;

import com.ra.model.entity.Category;
import com.ra.uitl.ConnectionDatabase;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

    @Override
    public List<Category> findAll() {
        Connection connection = ConnectionDatabase.openConnection();
        List<Category> list = new ArrayList<>();
        try {
            CallableStatement statement = connection.prepareCall("{CALL PROC_FIND_ALL()}");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("id"));
                category.setCategoryName(rs.getString("name"));
                category.setCategoryStatus(rs.getBoolean("status"));
                list.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return list;
    }

    @Override
    public Category findById(Integer id) {
        Connection connection = ConnectionDatabase.openConnection();
        Category category = new Category();
        try {
            CallableStatement statement = connection.prepareCall("{CALL PROC_FIND_CATE_BY_ID(?)}");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                category.setCategoryId(rs.getInt("id"));
                category.setCategoryName(rs.getString("name"));
                category.setCategoryStatus(rs.getBoolean("status"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return category;
    }

    @Override
    public Boolean saveOrUpdate(Category category) {
        Connection connection = ConnectionDatabase.openConnection();
        int check = 0;
        try {
            if (category.getCategoryId() == 0) {
                CallableStatement statement = connection.prepareCall("{CALL PROC_ADD_CATEGORY(?,?)}");
                statement.setString(1, category.getCategoryName());
                statement.setBoolean(2, category.getCategoryStatus());
                check = statement.executeUpdate();

            } else {
                CallableStatement statement = connection.prepareCall("{CALL PROC_UPDATE_CATEGORY(?,?,?)}");
                statement.setInt(1,category.getCategoryId());
                statement.setString(2, category.getCategoryName());
                statement.setBoolean(3, category.getCategoryStatus());
                check = statement.executeUpdate();
            }
            if (check > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }

        return false;
    }

    @Override
    public void delete(Integer id) {

    }
}
