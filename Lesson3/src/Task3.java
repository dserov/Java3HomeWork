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

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author DSerov
 * @version dated May 04, 2018
 */
public class Task3 {
    static final String FILENAME = "efremova.txt"; // если не задано параметром, то по дефолту
    static final int terminalHeight = 24; // по умолчанию, терминал вмещает 24 строки

    public static void main(String[] args) throws IOException {
        String fileNameToOpen = FILENAME;
        if (args.length == 1)
            fileNameToOpen = args[0];

        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(fileNameToOpen))) {
            while (bufferedReader.ready()) {
                int i = terminalHeight;
                while (bufferedReader.ready() && i > 0) {
                    System.out.println(bufferedReader.readLine());
                    i--;
                }
                if (!bufferedReader.ready())
                    break;
                System.out.print(":'space' - to continue, 'q' - to quit: ");
                char input = getUserInput();
                if (input == 'q') break;
             }
         }
    }

    private static char getUserInput() throws IOException {
        char input = (char) System.in.read();
        while (input != 'q' && input != ' ')
            input = (char) System.in.read();
        return input;
    }
}
