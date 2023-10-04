import java.io.Serializable;

class Animal implements Serializable {
    private int number;
    private String name;
    private int age;
    private String breed;

    public Animal(String name, int age, String breed) {
        this.name = name;
        this.age = age;
        this.breed = breed;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getBreed() {
        return breed;
    }

    @Override
    public String toString() {
        return "Номер: " + number + ", Имя: " + name + ", Возраст: " + age + " лет, Порода: " + breed;
    }
}
