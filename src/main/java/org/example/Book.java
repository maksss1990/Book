package org.example;

import java.util.Arrays;

public class Book {
    private String title;
    private Publisher publisher;
    private int year;
    private String[] authors;

    public Book(String title, int year, Publisher publisher) {
        setTitle(title);
        setYear(year);
        setPublisher(publisher);
        this.authors = null;
    }

    public Book(String title, String author, int year, Publisher publisher) {
        this(title, year, publisher);
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя автора не может быть пустым");
        }
        this.authors = new String[]{author.trim()};
    }

    public Book(String title, String[] authors, int year, Publisher publisher) {
        this(title, year, publisher);
        setAuthors(authors);
    }

    public String getTitle() {
        return title;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public int getYear() {
        return year;
    }

    public String[] getAuthors() {
        return authors == null ? null : authors.clone();
    }

    public int getAuthorsCount() {
        return authors == null ? 0 : authors.length;
    }

    public String getAuthor(int index) {
        if (authors == null || index < 0 || index >= authors.length) {
            throw new IndexOutOfBoundsException("Индекс автора вне диапазона: " + index);
        }
        return authors[index];
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Название книги не может быть пустым");
        }
        this.title = title.trim();
    }

    public void setPublisher(Publisher publisher) {
        if (publisher == null) {
            throw new IllegalArgumentException("Издательство не может быть null");
        }
        this.publisher = publisher;
    }

    public void setYear(int year) {
        if (year <= 0) {
            throw new IllegalArgumentException("Год издания должен быть больше 0");
        }
        this.year = year;
    }

    public void setAuthor(String author) {
        if (this.authors == null) {
            throw new IllegalArgumentException("Массив авторов не инициализирован");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя автора не может быть пустым");
        }
        String[] newAuthorsArray = Arrays.copyOf(this.authors, this.authors.length + 1);
        newAuthorsArray[newAuthorsArray.length - 1] = author.trim();
        this.authors = newAuthorsArray;
    }

    public void setAuthors(String[] authors) {
        if (authors == null) {
            this.authors = null;
            return;
        }

        for (String newAuthor : authors) {
            if (newAuthor == null || newAuthor.trim().isEmpty()) {
                throw new IllegalArgumentException("Имя автора в массиве не может быть пустым");
            }
        }

        this.authors = authors.clone();
    }

    public void print() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Название: ").append(title).append("\n");

        if (authors != null && authors.length > 0) {
            sb.append("Автор(ы): ").append(String.join(", ", authors)).append("\n");
        }

        sb.append("Издательство: ")
                .append(publisher.getName())
                .append(", ")
                .append(publisher.getCity())
                .append("\n")
                .append("Год: ")
                .append(year);

        return sb.toString();
    }
}

