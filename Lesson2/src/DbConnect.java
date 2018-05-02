/*
 * Copyright (C) 2018 geekbrains homework lesson2
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.sql.*;

/**
 * @author DSerov
 * @version dated May 05, 2018
 */
public class DbConnect {
    private final String connString = "jdbc:sqlite:main.sqlite";
    private static DbConnect instance;
    private Connection connection;

    public static synchronized DbConnect getInstance() throws DbConnectException {
        if (instance == null) {
            try {
                instance = new DbConnect();
            } catch (SQLException e) {
                throw new DbConnectException("DbConnect failed: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new DbConnectException("DbConnect failed: " + e.getMessage());
            }
        }
        return instance;
    }

    private DbConnect() throws ClassNotFoundException, SQLException {
        // подключимся к БД
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(connString);
    }

    public void disconnect() throws SQLException {
        connection.close();
    }

    public void createTblTovar() {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute("drop table if exists tovar;");
            stmt.execute("create table tovar (" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "prodid INTEGER UNIQUE NOT NULL," +
                    "title TEXT NOT NULL," +
                    "cost INTEGER NOT NULL DEFAULT 0" +
                    ")");

            stmt.execute("CREATE UNIQUE INDEX idx_prodid_unique ON tovar (" +
                    "prodid" +
                    ");");
            stmt.execute("CREATE INDEX idx_title ON tovar (" +
                    "    title" +
                    ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fillTblTovarByData() {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Tovar (prodid, title, cost) VALUES (?,?,?);");
            for (int i = 1; i <= 10000; i++) {
                preparedStatement.setInt(1, i);
                preparedStatement.setString(2, "Tovar" + i);
                preparedStatement.setInt(3, i * 10);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
