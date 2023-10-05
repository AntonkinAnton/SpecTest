-- 8.	Создать таблицы с иерархией из диаграммы в БД
CREATE TABLE Animal (
    animal_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    commands VARCHAR(255),
    birth_date DATE
);

CREATE TABLE DomesticAnimal (
    domestic_id INT PRIMARY KEY AUTO_INCREMENT,
    animal_id INT,
    name VARCHAR(255),
    commands VARCHAR(255),
    birth_date DATE,
    FOREIGN KEY (animal_id) REFERENCES Animal(animal_id)
);

CREATE TABLE WorkingAnimal (
    working_id INT PRIMARY KEY AUTO_INCREMENT,
    animal_id INT,
    name VARCHAR(255),
    commands VARCHAR(255),
    birth_date DATE,
    FOREIGN KEY (animal_id) REFERENCES Animal(animal_id)
);

CREATE TABLE Dog (
    dog_id INT PRIMARY KEY AUTO_INCREMENT,
    domestic_id INT,
    name VARCHAR(255),
    commands VARCHAR(255),
    birth_date DATE,
    FOREIGN KEY (domestic_id) REFERENCES DomesticAnimal(domestic_id)
);

CREATE TABLE Cat (
    cat_id INT PRIMARY KEY AUTO_INCREMENT,
    domestic_id INT,
    name VARCHAR(255),
    commands VARCHAR(255),
    birth_date DATE,
    FOREIGN KEY (domestic_id) REFERENCES DomesticAnimal(domestic_id)
);

CREATE TABLE Hamster (
    hamster_id INT PRIMARY KEY AUTO_INCREMENT,
    domestic_id INT,
    name VARCHAR(255),
    commands VARCHAR(255),
    birth_date DATE,
    FOREIGN KEY (domestic_id) REFERENCES DomesticAnimal(domestic_id)
);

CREATE TABLE Horse (
    horse_id INT PRIMARY KEY AUTO_INCREMENT,
    working_id INT,
    name VARCHAR(255),
    commands VARCHAR(255),
    birth_date DATE,
    FOREIGN KEY (working_id) REFERENCES WorkingAnimal(working_id)
);

CREATE TABLE Camel (
    camel_id INT PRIMARY KEY AUTO_INCREMENT,
    working_id INT,
    name VARCHAR(255),
    commands VARCHAR(255),
    birth_date DATE,
    FOREIGN KEY (working_id) REFERENCES WorkingAnimal(working_id)
);

CREATE TABLE Donkey (
    donkey_id INT PRIMARY KEY AUTO_INCREMENT,
    working_id INT,
    name VARCHAR(255),
    commands VARCHAR(255),
    birth_date DATE,
    FOREIGN KEY (working_id) REFERENCES WorkingAnimal(working_id)
);

-- 9.	Заполнить низкоуровневые таблицы именами(животных), командами, которые они выполняют, и датами рождения

INSERT INTO Animal (name, commands, birth_date) VALUES
    ('Fluffy', 'Sit, Fetch', '2022-04-15'),
    ('Rex', 'Fetch, Guard', '2018-02-10'),
    ('Whiskers', 'Play, Purr', '2019-07-20'),
    ('Buddy', 'Pull cart, Plow', '2016-11-30'),
    ('Sasha', 'Carry loads', '2017-09-25');

INSERT INTO DomesticAnimal (animal_id, name, commands, birth_date) VALUES
    (1, 'Fluffy', 'Sit, Fetch', '2022-04-15'),  
    (2, 'Rex', 'Fetch, Guard', '2018-02-10'),
    (3, 'Whiskers', 'Play, Purr', '2019-07-20');

INSERT INTO WorkingAnimal (animal_id, name, commands, birth_date) VALUES
    (4, 'Buddy', 'Pull cart, Plow', '2016-11-30'), 
    (5, 'Sasha', 'Carry loads', '2017-09-25');

INSERT INTO Dog (domestic_id, name, commands, birth_date) VALUES
    (1, 'Fluffy', 'Sit, Fetch', '2022-04-15');

INSERT INTO Cat (domestic_id, name, commands, birth_date) VALUES
    (2, 'Rex', 'Fetch, Guard', '2018-02-10');

INSERT INTO Hamster (domestic_id, name, commands, birth_date) VALUES
    (3, 'Whiskers', 'Play, Purr', '2019-07-20');

INSERT INTO Horse (working_id, name, commands, birth_date) VALUES
    (4, 'Buddy', 'Pull cart, Plow', '2016-11-30');

INSERT INTO Camel (working_id, name, commands, birth_date) VALUES
    (5, 'Sasha', 'Carry loads', '2017-09-25');

-- 10.	Удалив из таблицы верблюдов, т.к. верблюдов решили перевезти в другой питомник на зимовку. Объединить таблицы лошади, и ослы в одну таблицу.

DELETE FROM Camel;
CREATE TABLE HorseAndDonkey AS
SELECT * FROM Horse
UNION
SELECT * FROM Donkey;
DROP TABLE Horse;
DROP TABLE Donkey;

-- 11.	Создать новую таблицу “Молодые животные” в которую попадут все животные старше 1 года, но младше 3 лет и в отдельном столбце с точностью до месяца подсчитать возраст животных в новой таблице
CREATE TABLE YoungAnimals (
    young_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    age_months INT
);
INSERT INTO YoungAnimals (name, age_months)
SELECT
    A.name,
    TIMESTAMPDIFF(MONTH, A.birth_date, CURDATE()) AS age_months
FROM Animal A
WHERE TIMESTAMPDIFF(YEAR, A.birth_date, CURDATE()) >= 1
  AND TIMESTAMPDIFF(YEAR, A.birth_date, CURDATE()) < 3;

-- 12.	Объединить все таблицы в одну, при этом сохраняя поля, указывающие на прошлую принадлежность к старым таблицам
CREATE TABLE AllAnimals AS
SELECT 'Animal' AS table_name, animal_id, name, commands, birth_date FROM Animal
UNION ALL
SELECT 'DomesticAnimal' AS table_name, domestic_id, name, commands, birth_date FROM DomesticAnimal
UNION ALL
SELECT 'WorkingAnimal' AS table_name, working_id, name, commands, birth_date FROM WorkingAnimal
UNION ALL
SELECT 'Dog' AS table_name, dog_id, name, commands, birth_date FROM Dog
UNION ALL
SELECT 'Cat' AS table_name, cat_id, name, commands, birth_date FROM Cat
UNION ALL
SELECT 'Hamster' AS table_name, hamster_id, name, commands, birth_date FROM Hamster
UNION ALL
SELECT 'HorseAndDonkey' AS table_name, horse_id AS working_id, name, commands, birth_date FROM HorseAndDonkey
UNION ALL
SELECT 'Camel' AS table_name, camel_id AS working_id, name, commands, birth_date FROM Camel;
