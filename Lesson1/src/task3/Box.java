package task3;
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

/**
 *
 * @author DSerov
 * @version dated May 05, 2018
 */

import task3.Fruits.Fruit;
import java.util.ArrayList;

public class Box<T extends Fruit> {
    private ArrayList<T> content = new ArrayList<>();

    public void addFruits(T... fruit) {
        for(T item: fruit) content.add(item);
    }

    public double getWeight() {
        if (content.size() == 0) return 0f;
        return content.get(0).getWeight() * content.size();
    }

    public boolean compare(Box<?> withBox) {
        return getWeight() == withBox.getWeight();
    }

    public void transferTo(Box<T> toBox) {
        if (getWeight() == 0) return;

        toBox.content.addAll(content);
        content.clear();
    }
}
