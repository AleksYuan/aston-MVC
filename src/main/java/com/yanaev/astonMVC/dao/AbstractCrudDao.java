package com.yanaev.astonMVC.dao;

import lombok.Getter;
import lombok.Setter;

import java.sql.*;

@Setter
@Getter
public class AbstractCrudDao {
    private String URL;
    private String USERNAME;
    private String PASSWORD;
    static Connection connection;

    public void setConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected ResultSet getResultSet(String SQLQuery) {
        try {
            Statement statement = connection.createStatement();
            String SQL = SQLQuery;
            ResultSet resultSet = statement.executeQuery(SQL);
            return resultSet;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected ResultSet getResultSet(String preparedStatement, Long id) {
        try (PreparedStatement preStat = connection.prepareStatement(preparedStatement)) {
            preStat.setLong(1, id);
            return preStat.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    protected void executeUpdatePreparedStatement(String preparedStatement,
                                                  String param1,
                                                  String param2,
                                                  Integer param3,
                                                  String param4,
                                                  Long param5) {
        try (PreparedStatement prepStat = connection.prepareStatement(
                preparedStatement);) {
            prepStat.setString(1, param1);
            prepStat.setString(2, param2);
            prepStat.setInt(3, param3);
            prepStat.setString(4, param4);
            if (param5 != null) prepStat.setLong(5, param5);

            prepStat.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    protected void executeUpdatePreparedStatement(String preparedStatement,
                                                  String param1,
                                                  String param2,
                                                  Integer param3,
                                                  Long param4,
                                                  Long param5) {
        try (PreparedStatement prepStat = connection.prepareStatement(
                preparedStatement)) {

            prepStat.setString(1, param1);
            prepStat.setString(2, param2);
            prepStat.setInt(3, param3);
            prepStat.setLong(4, param4);
            if (param5 != null) prepStat.setLong(5, param5);

            prepStat.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    protected void executeUpdatePreparedStatement(String preparedStatement,
                                                  Integer param1,
                                                  Boolean param2,
                                                  Long param3) {
        try (PreparedStatement prepStat = connection.prepareStatement(
                preparedStatement)) {

            prepStat.setInt(1, param1);
            prepStat.setBoolean(2, param2);
            if (param3 != null) prepStat.setLong(3, param3);
            prepStat.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    protected void executeUpdatePreparedStatement(String preparedStatement,
                                                  String param1,
                                                  Integer param2,
                                                  Long param3,
                                                  Long param4) {
        try (PreparedStatement prepStat = connection.prepareStatement(
                preparedStatement)) {

            prepStat.setString(1, param1);
            prepStat.setInt(2, param2);
            if (param3 != null) prepStat.setLong(3, param3);
            if (param4 != null) prepStat.setLong(4, param4);
            prepStat.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    protected void executeQuery(String SQL) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void addManyToManyDependency(String preparedStatement, Long id1, Long id2) {
        try (PreparedStatement prepStat = connection.prepareStatement(
                preparedStatement)) {

            prepStat.setLong(1, id1);
            prepStat.setLong(2, id2);
            prepStat.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    protected void addEntityToEntity(String SQL, Long id1, Long id2) {
        try {
            PreparedStatement preStat = connection.prepareStatement(SQL);
            preStat.setLong(1, id1);
            preStat.setLong(2, id2);
            preStat.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
