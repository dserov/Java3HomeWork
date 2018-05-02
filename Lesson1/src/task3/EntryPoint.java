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

import task3.Fruits.Apple;
import task3.Fruits.Orange;

/**
 *
 * @author DSerov
 * @version dated May 05, 2018
 */
public class EntryPoint<T> {

    /**
     * 3. Задача:
         a. Даны классы Fruit -> Apple, Orange;
         b. Класс Box, в который можно складывать фрукты. Коробки условно сортируются по
             типу фрукта, поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
         c. Для хранения фруктов внутри коробки можно использовать ArrayList;
         d. Сделать метод getWeight(), который высчитывает вес коробки. Задать вес одного
             фрукта и их количество: вес яблока – 1.0f, апельсина – 1.5f (единицы измерения не
             важны);
         e. Внутри класса Box сделать метод Compare, который позволяет сравнить текущую
             коробку с той, которую подадут в Compare в качестве параметра. True – если их массы
             равны, False в противоположном случае. Можно сравнивать коробки с яблоками и
             апельсинами;
         f. Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую.
             Помним про сортировку фруктов: нельзя яблоки высыпать в коробку с апельсинами.
             Соответственно, в текущей коробке фруктов не остается, а в другую перекидываются
             объекты, которые были в первой;
         g. Не забываем про метод добавления фрукта в коробку.
     */

    public static void main(String[] args) {
        Box<Apple> boxOfApples = new Box<>();
        boxOfApples.addFruits(new Apple(), new Apple(), new Apple());
        System.out.println("boxOfApples weight = " + boxOfApples.getWeight());

        Box<Orange> boxOfOranges = new Box<>();
        boxOfOranges.addFruits(new Orange(), new Orange());
        System.out.println("boxOfOranges weight = " + boxOfOranges.getWeight());

        // compare
        System.out.println("Compare boxOfApples with boxOfOranges = " + boxOfApples.compare(boxOfOranges));
        System.out.println("after adde one more apple:");
        boxOfApples.addFruits(new Apple());
        System.out.println("Compare boxOfApples with boxOfOranges = " + boxOfApples.compare(boxOfOranges));

        // transfer
        Box<Apple> emptyBoxOfApples = new Box<Apple>();
        boxOfApples.transferTo(emptyBoxOfApples);


        System.out.println("after transfer:");
        System.out.println("boxOfApples weight = " + boxOfApples.getWeight());
        System.out.println("emptyBoxOfApples weight = " + emptyBoxOfApples.getWeight());
        System.out.println("boxOfOranges weight = " + boxOfOranges.getWeight());
    }
}
