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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author DSerov
 * @version dated May 05, 2018
 */
public class Tovar {
    private DbConnect connect;

    private int id;
    private int prodid;
    private String title;
    private int cost;

    public Tovar(DbConnect connect, int id, int prodid, String title, int cost) {
        this.connect = connect;
        this.id = id;
        this.prodid = prodid;
        this.title = title;
        this.cost = cost;
    }

    public Tovar(DbConnect connect) {
        this(connect, 0, 0, "", 0);
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public void save() {
        String sql = "UPDATE tovar SET prodid=?, title=?, cost=? LIMIT 1;";
        try (PreparedStatement statement = connect.getPreparedStatement(sql)) {
            statement.setInt(1, this.prodid);
            statement.setString(2, this.title);
            statement.setInt(3, this.cost);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getTovarByTitle(String title) throws TovarException {
        String sql = "SELECT * FROM tovar WHERE title=? LIMIT 1;";
        try (PreparedStatement statement = connect.getPreparedStatement(sql)) {
            if (title.isEmpty()) throw new TovarException("Имя товара не задано");
            statement.setString(1, title);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    this.id = resultSet.getInt(1);
                    this.prodid = resultSet.getInt(2);
                    this.title = resultSet.getString(3);
                    this.cost = resultSet.getInt(4);
                } else
                    throw new TovarException("Товар не найден");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getId() {
        return id;
    }

    public int getProdid() {
        return prodid;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Tovar> getTovarListByCostPeriod(int cost_min, int cost_max) {
        ArrayList<Tovar> result = new ArrayList<>();

        String sql = "SELECT * FROM tovar WHERE cost BETWEEN ? AND ?;";
        try {
            PreparedStatement statement = connect.getPreparedStatement(sql);
            statement.setInt(1, cost_min);
            statement.setInt(2, cost_max);
            try (ResultSet rs = statement.executeQuery()) {
                while(rs.next()) {
                    Tovar tovar = new Tovar(connect,
                            rs.getInt(1), rs.getInt(2),
                            rs.getString(3), rs.getInt(4));
                    result.add(tovar);
                }
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
