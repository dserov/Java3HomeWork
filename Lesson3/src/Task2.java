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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * @author DSerov
 * @version dated May 03, 2018
 */
public class Task2 {
    static final String FILENAME_REGEX = "file.\\.bin";

    public static void main(String[] args) throws IOException {
        File currentDir = new File(".");

        ArrayList<File> fileArrayList = new ArrayList<>();

        // добавим в массив файлы-примеры только по маске FILENAME_REGEX
        Collections.addAll(fileArrayList,
                currentDir.listFiles(new FilenameFilter() {
                    Pattern pattern = Pattern.compile(FILENAME_REGEX);

                    @Override
                    public boolean accept(File dir, String name) {
                        return pattern.matcher(name).matches();
                    }
                }));

        // входные файла в потоки преобразуем
        ArrayList<BufferedInputStream> fileInputStreams = new ArrayList<>();
        for (File f : fileArrayList) {
            fileInputStreams.add(
                    new BufferedInputStream(
                            new FileInputStream(f)));
        }

        Enumeration<BufferedInputStream> streamEnumeration = Collections.enumeration(fileInputStreams);
        SequenceInputStream sequenceInputStream = new SequenceInputStream(streamEnumeration);

        // зададим результирующий файл
        try (BufferedOutputStream outputStream = new BufferedOutputStream(
                new FileOutputStream(
                        new File("result.bin")
                )
        )) {
            // перекачиваем данные
            int data = sequenceInputStream.read();
            while(data != -1) {
                outputStream.write(data);
                data = sequenceInputStream.read();
            }
        }

        System.out.println("Done!");
    }
}
