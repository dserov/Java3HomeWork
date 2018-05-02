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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author DSerov
 * @version dated May 05, 2018
 */
public class EntryPoint {
    public static void main(String[] args) {
        new EntryPoint();
    }

    private DbConnect dbConnect;

    private EntryPoint() {
        System.out.println("Connecting to db....");
        try {
            this.dbConnect = DbConnect.getInstance();

            System.out.println("create table tovar with indexes...");
            dbConnect.createTblTovar();

            System.out.println("fill by example data...");
            dbConnect.fillTblTovarByData();

            System.out.println("Доступные команды:\n" +
            "/выход - выход\n" +
            "/цена <имя_товара>\n" +
            "/сменитьцену <имя_товара> <новая_цена>\n" +
            "/товарыпоцене <стоимость_от> <стоимость_до>\n\n");
            String cmd = "";
            String parts[];
            Scanner scanner = new Scanner(System.in);
            final String PROMPT = "tovary:>";

            while (!cmd.equalsIgnoreCase("/выход")) {
                System.out.print(PROMPT);
                String input = scanner.nextLine();
                if (input.isEmpty()) continue;

                parts = input.split(" ");
                cmd = parts[0];

                switch (cmd) {
                    case "/цена":
                        getCostByName(parts);
                        break;
                    case "/сменитьцену":
                        changePriceByName(parts);
                        break;
                    case "/товарыпоцене":
                        getTovarList(parts);
                        break;
                    default:
                        System.out.println("Команда не распознана");
                }
            }
        } catch (DbConnectException e) {
            throw new RuntimeException(e);
        }

        try {
            dbConnect.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getCostByName(String parts[]) {
        try {
            if (parts.length < 2)
                throw new TovarException("Наименование товара не задано");
            Tovar tovar = new Tovar(dbConnect);
            tovar.getTovarByTitle(parts[1]);

            System.out.println("Стоимость товара = " + tovar.getCost());
        } catch (TovarException e) {
            System.out.println(e.getMessage());
        }
    }

    private void changePriceByName(String parts[]) {
        try {
            if (parts.length < 2)
                throw new TovarException("Недостаточно параметров");
            Tovar tovar = new Tovar(dbConnect);
            tovar.getTovarByTitle(parts[1]);

            Integer cost = Integer.getInteger(parts[2]);
            if (cost == null)
                throw new TovarException("Цена не является цифровым значнием");

            tovar.setCost(cost);
            tovar.save();
            System.out.println("Установлена новая стоимость товара = " + cost);
        } catch (TovarException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getTovarList(String parts[]) {
        try {
            if (parts.length < 3)
                throw new TovarException("Недостаточно параметров");

            Integer cost_min, cost_max;
            try {
                cost_min = Integer.parseInt(parts[1]);
                cost_max = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                throw new TovarException("Границы цен не являются цифровым значением");
            }

            Tovar tovar = new Tovar(dbConnect);
            ArrayList<Tovar> tovarList = tovar.getTovarListByCostPeriod(cost_min, cost_max);

            if (tovarList.size() == 0) {
                System.out.println("Товары в заданном диапазоне не найдены");
                return;
            }

            System.out.printf("%10s | %10s | %12s | %10s \n", "id", "prodid", "title", "cost");
            System.out.println("============================================================");

            String format_line = "%10d | %10d | %12s | %10d \n";
            for (Tovar item : tovarList) {
                System.out.printf(format_line, item.getId(), item.getProdid(), item.getTitle(), item.getCost());
            }

            System.out.println("============================================================");
        } catch (TovarException e) {
            System.out.println(e.getMessage());
        }
    }
}
