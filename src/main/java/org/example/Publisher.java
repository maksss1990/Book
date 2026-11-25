package org.example;

public class Publisher {
    private String name;
    private String city;

    public Publisher(String name, String city) {
        setName(name);
        setCity(city);
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название издательства не может быть пустым");
        }
        this.name = name.trim();
    }

    public void setCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("Город издательства не может быть пустым");
        }
        this.city = city.trim();
    }

    @Override
    public String toString() {
        return name + ", " + city;
    }
}

