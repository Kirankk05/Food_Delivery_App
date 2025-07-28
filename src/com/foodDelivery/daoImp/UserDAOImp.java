package com.foodDelivery.daoImp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foodDelivery.dao.UserDAO;
import com.foodDelivery.model.User;

public class UserDAOImp implements UserDAO {

	private static final String URL = "jdbc:mysql://localhost:3306/fooddeliveryapp";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";

	private static final String INSERT_QUERY = "INSERT INTO user (UserID, Username, Password, Email, Address, Role) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SELECT_QUERY = "SELECT * FROM user WHERE UserID = ?";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM user";
	private static final String UPDATE_QUERY = "UPDATE user SET Username = ?, Password = ?, Email = ?, Address = ?, Role = ? WHERE UserID = ?";
	private static final String DELETE_QUERY = "DELETE FROM user WHERE UserID = ?";

	public UserDAOImp() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}

	@Override
	public void addUser(User user) {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {

			preparedStatement.setInt(1, user.getUserId());
			preparedStatement.setString(2, user.getUserName());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getAddress());
			preparedStatement.setString(6, user.getRole());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public User getUser(int userId) {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {

			preparedStatement.setInt(1, userId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return new User(resultSet.getInt("UserID"), resultSet.getString("Username"),
							resultSet.getString("Password"), resultSet.getString("Email"),
							resultSet.getString("Address"), resultSet.getString("Role"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateUser(User user) {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {

			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getAddress());
			preparedStatement.setString(5, user.getRole());
			preparedStatement.setInt(6, user.getUserId());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteUser(int userId) {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {

			preparedStatement.setInt(1, userId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				User user = new User(resultSet.getInt("UserID"), resultSet.getString("Username"),
						resultSet.getString("Password"), resultSet.getString("Email"), resultSet.getString("Address"),
						resultSet.getString("Role"));
				users.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}

	@Override
	public User getUserByUsername(String username) {
		User user = null;

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM user WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("UserID"));
                user.setUserName(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setEmail(rs.getString("Email"));
                user.setAddress(rs.getString("Address"));
                user.setRole(rs.getString("Role"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    
	}

	@Override
	public User getUserByEmail(String email) {
		User user = null;

	    try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
	        String sql = "SELECT * FROM user WHERE email = ?";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setString(1, email);

	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            user = new User();
	            user.setUserId(rs.getInt("UserID"));
	            user.setUserName(rs.getString("Username"));
	            user.setPassword(rs.getString("Password"));
	            user.setEmail(rs.getString("Email"));
	            user.setAddress(rs.getString("Address"));
	            user.setRole(rs.getString("Role"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return user;
	}
}
