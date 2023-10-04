import java.util.*;

class Program {
    private Map<Class<? extends Animal>, List<Animal>> animalClasses = new HashMap<>();
    private Map<Animal, List<String>> animalCommands = new HashMap<>();
    private Counter counter = new Counter();

    public void addAnimal(String name, int age, String breed, Class<? extends Animal> animalClass) {
        Animal animal = null;

        try (Counter ignored = new Counter()) {
            animal = animalClass.getDeclaredConstructor(String.class, int.class, String.class).newInstance(name, age, breed);
        } catch (Exception e) {
            System.err.println("Ошибка при создании животного: " + e.getMessage());
        }

        if (animal != null) {
            animal.setNumber(counter.addAndGet());
            animalClasses.computeIfAbsent(animalClass, k -> new ArrayList<>()).add(animal);
            animalCommands.put(animal, new ArrayList<>());
            System.out.println("Животное успешно добавлено!");
        }
    }

    public void addCommand(Animal animal, String command) {
        List<String> commands = animalCommands.get(animal);
        if (commands != null) {
            commands.add(command);
        }
    }

    public void displayMenu() {
        System.out.println("Меню реестра животных:");
        System.out.println("1. Завести новое животное");
        System.out.println("2. Просмотреть список животных");
        System.out.println("3. Добавить команду для животного");
        System.out.println("4. Выйти");

        try (Counter ignored = new Counter()) {
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Введите данные о животном:");
                    System.out.print("Имя: ");
                    String name = scanner.next();
                    System.out.print("Возраст: ");
                    int age = scanner.nextInt();
                    System.out.print("Порода: ");
                    String breed = scanner.next();

                    System.out.println("Выберите класс для животного:");
                    System.out.println("1. Собака");
                    System.out.println("2. Кошка");
                    System.out.println("3. Хомяк");
                    System.out.println("4. Лошадь");
                    System.out.println("5. Верблюд");
                    System.out.println("6. Осел");
                    int classChoice = scanner.nextInt();

                    Class<? extends Animal> animalClass = null;
                    switch (classChoice) {
                        case 1:
                            animalClass = Dog.class;
                            break;
                        case 2:
                            animalClass = Cat.class;
                            break;
                        case 3:
                            animalClass = Hamster.class;
                            break;
                        case 4:
                            animalClass = Horse.class;
                            break;
                        case 5:
                            animalClass = Camel.class;
                            break;
                        case 6:
                            animalClass = Donkey.class;
                            break;
                        default:
                            System.out.println("Неверный выбор класса.");
                            break;
                    }

                    if (animalClass != null) {
                        addAnimal(name, age, breed, animalClass);
                    }
                    break;
                case 2:
                    displayAnimalMenu();
                    break;
                case 3:
                    List<Animal> animals = new ArrayList<>();
                    for (List<Animal> animalList : animalClasses.values()) {
                        animals.addAll(animalList);
                    }
                    if (animals.isEmpty()){
                        System.out.println("Список животных пуст.");
                        break;
                    }
                    System.out.println("Выберите животное для добавления команды:");
                    for (Animal animal : animalCommands.keySet()) {
                        System.out.println(animal.getNumber() + ". " + animal.getName());
                    }
                    int animalNumber = scanner.nextInt();
                    Animal selectedAnimal = findAnimalByNumber(animalNumber);

                    if (selectedAnimal != null) {
                        System.out.print("Введите команду для животного: ");
                        String command = scanner.next();
                        addCommand(selectedAnimal, command);
                        System.out.println("Команда успешно добавлена.");
                    } else {
                        System.out.println("Животное с таким номером не найдено.");
                    }
                    break;
                case 4:
                    System.out.println("Выход...");
                    System.exit(0);
                default:
                    System.out.println("Неверный выбор. Пожалуйста, выберите корректную опцию.");
            }
        } catch (IllegalStateException e) {
            System.err.println("Ошибка при добавлении");
        }
    }

    private void displayAnimalMenu() {
        System.out.println("Просмотреть список животных:");
        System.out.println("1. Домашние животные");
        System.out.println("2. Вьючные животные");
        System.out.println("3. Вернуться в главное меню");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                printAnimals(DomesticAnimal.class);
                break;
            case 2:
                printAnimals(WorkingAnimal.class);
                break;
            case 3:
                return;
            default:
                System.out.println("Неверный выбор. Пожалуйста, выберите корректную опцию.");
        }
    }

    private void printAnimals(Class<? extends Animal> animalClass) {
        List<Animal> animals = new ArrayList<>();
        for (List<Animal> animalList : animalClasses.values()) {
            animals.addAll(animalList);
        }

        List<Animal> filteredAnimals = new ArrayList<>();
        for (Animal animal : animals) {
            if (animalClass.isAssignableFrom(animal.getClass())) {
                filteredAnimals.add(animal);
            }
        }

        if (filteredAnimals.isEmpty()) {
            System.out.println("Список животных класса " + animalClass.getSimpleName() + " пуст.");
        } else {
            System.out.println("Список животных класса " + animalClass.getSimpleName() + ":");
            for (Animal animal : filteredAnimals) {
                System.out.println(animal);
                List<String> commands = animalCommands.get(animal);
                if (commands != null && !commands.isEmpty()) {
                    System.out.println("Команды: " + String.join(", ", commands));
                }
            }
        }
    }

    private Animal findAnimalByNumber(int number) {
        for (List<Animal> animals : animalClasses.values()) {
            for (Animal animal : animals) {
                if (animal.getNumber() == number) {
                    return animal;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Program registry = new Program();

        while (true) {
            registry.displayMenu();
        }
    }
}

