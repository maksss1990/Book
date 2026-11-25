package org.example;


import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Publisher[] publishers;
    private static Book[] books;
    private static final String TEXT_FOR_PUBLISHER_NAME = "Введите название издательства: ";
    private static final String TEXT_FOR_PUBLISHER_CITY = "Введите город издательства: ";

    public static void main(String[] args) {
        Publisher prospect = new Publisher("Проспект", "Москва");
        Publisher piter = new Publisher("Питер", "Санкт-Петербург");
        Publisher bhv = new Publisher("БХВ", "Санкт-Петербург");
        Publisher dialectica = new Publisher("Диалектика", "Киев");

        publishers = new Publisher[]{prospect, piter, bhv, dialectica};

        books = new Book[]{
                new Book("Computer Science: основы программирования на Java, ООП, алгоритмы и структуры данных",
                        new String[]{"Седжвик Роберт", "Уэйн Кевин"}, 2018, piter),
                new Book("Разработка требований к программному обеспечению. 3-е издание, дополненное",
                        "Вигерс Карл", 2019, bhv),
                new Book("Java. Полное руководство, 10-е издание",
                        "Шилдт Герберт", 2018, dialectica),
                new Book("C/C++. Процедурное программирование",
                        "Полубенцева М.И.", 2017, bhv),
                new Book("Конституция РФ", 2020, prospect)
        };

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Введите команду create, print или exit :");
            String command = sc.nextLine().trim().toLowerCase();
            switch (command) {
                case "create" -> create(sc);
                case "print" -> printAll(books);
                case "exit" -> {
                    System.out.println("Программа завершена.");
                    return;
                }
                default -> System.out.println("Неизвестная команда. Доступные: create, print, exit.");
            }
        }
    }

    private static void create(Scanner scanner) {
        String command = checkLine(scanner, "Какой объект необходимо создать (p/b): ").toLowerCase();
        switch (command) {
            case "p"-> createPublisher(scanner);
            case "b"-> createBook(scanner);
            default -> System.out.println("Неизвестный тип объекта. Доступные: p, b.");
        }
    }

    private static void createPublisher(Scanner scanner) {
        while (true) {
            String[] publisherData = addNameAndCityForPublisher(scanner,
                    TEXT_FOR_PUBLISHER_NAME,
                    TEXT_FOR_PUBLISHER_CITY);

            String name = publisherData[0];
            String city = publisherData[1];

            Publisher existing = findPublisher(name, city);
            if (existing != null) {
                System.out.println("Издательство " + existing + " уже существует.");
                return;
            }

            if (addPublisher(name, city) != null) {
                return;
            }
        }
    }

    private static void createBook(Scanner scanner) {
        Publisher publisher = findOrCreatePublisher(scanner);
        if (publisher == null) {
            System.out.println("Создание книги отменено.");
            return;
        }

        String title = checkLine(scanner, "Введите название книги: ");
        int year = checkInt(scanner, "Введите год издания: ");
        String[] authors = readAuthors(scanner);

        Book book;
        if (authors == null || authors.length == 0) {
            book = new Book(title, year, publisher);
        } else if (authors.length == 1) {
            book = new Book(title, authors[0], year, publisher);
        } else {
            book = new Book(title, authors, year, publisher);
        }

        books = Arrays.copyOf(books, books.length + 1);
        books[books.length - 1] = book;

        System.out.println("Книга добавлена: " + book.getTitle() + ".");
    }

    private static Publisher findOrCreatePublisher(Scanner scanner) {
        while (true) {
            String[] publisherData = addNameAndCityForPublisher(scanner,
                    TEXT_FOR_PUBLISHER_NAME,
                    TEXT_FOR_PUBLISHER_CITY);

            String name = publisherData[0];
            String city = publisherData[1];

            Publisher existing = findPublisher(name, city);
            if (existing != null) {
                return existing;
            }

            System.out.println("Издательство " + name + ", " + city + " не найдено.");
            System.out.println("Создать новое издательство " + name + ", " + city + " ? (y/n): ");
            String answer = scanner.nextLine().trim().toLowerCase();

            if (answer.equals("y")) {
                Publisher newPublisher = addPublisher(name, city);
                if (newPublisher != null) {
                    return newPublisher;
                }
            } else if (answer.equals("n")) {
                return null;
            } else {
                System.out.println("Введите y или n.");
            }
        }
    }

    private static Publisher addPublisher(String name, String city) {
        try {
            Publisher publisher = new Publisher(name, city);

            publishers = Arrays.copyOf(publishers, publishers.length + 1);
            publishers[publishers.length - 1] = publisher;

            System.out.println("Издательство добавлено: " + publisher + ".");
            return publisher;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static String[] readAuthors(Scanner scanner) {
        while (true) {
            System.out.print("Введите авторов через запятую (оставьте пустым, если авторов нет): ");
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                return null;
            }

            line = line.replaceAll("[,.\\s]+$", "").trim();
            if (line.isEmpty()) {
                return null;
            }

            String[] parts = line.split(",", -1);
            String[] authors = new String[parts.length];
            boolean hasAuthors = true;

            for (int i = 0; i < parts.length; i++) {
                String author = parts[i].trim();

                if (author.isEmpty()) {
                    hasAuthors = false;
                    break;
                }

                authors[i] = author;
            }

            if (hasAuthors) {
                return authors;
            }

            System.out.println("Имя автора не может быть пустым.");
        }
    }

    private static Publisher findPublisher(String name, String city) {
        for (Publisher publisher : publishers) {
            if (publisher != null
                    && publisher.getName().equalsIgnoreCase(name)
                    && publisher.getCity().equalsIgnoreCase(city)) {
                return publisher;
            }
        }
        return null;
    }

    private static String checkLine(Scanner scanner, String text) {
        while (true) {
            System.out.println(text);
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println("Значение не может быть пустым. Повторите ввод.");
        }
    }

    private static int checkInt(Scanner scanner, String text) {
        while (true) {
            System.out.print(text);
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Введено не число. Введите корректное целое число.");
                scanner.nextLine();
            }
        }
    }

    private static String[] addNameAndCityForPublisher(Scanner scanner, String textforName, String textForCity) {
        String name = checkLine(scanner, textforName);
        String city = checkLine(scanner, textForCity);
        return new String[]{name, city};
    }

    public static void printAll(Book[] books) {
        if (books == null) {
            return;
        }
        for (Book book : books) {
            if (book != null) {
                book.print();
            }
        }
    }

}