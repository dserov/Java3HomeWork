/*
 * Copyright (C) 2018 geekbrains homework lesson1
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

import java.util.ArrayList;

/**
 *
 * @author DSerov
 * @version dated May 05, 2018
 */
public class Task2<T> {

    /**
     * 2. Написать метод, который преобразует массив в ArrayList;
     */
    public ArrayList<T> convertToArrayList (T... arr) {
        ArrayList<T> result = new ArrayList<T>();

        for (T item: arr ) result.add(item);

        return result;
    }
}
