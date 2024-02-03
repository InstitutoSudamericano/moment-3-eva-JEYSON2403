CREATE TABLE IF NOT EXISTS film(
    id SERIAL,
    title VARCHAR (100),
    director VARCHAR (100),
    duration INT,
    release_year INT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS scene (
    id SERIAL,
    description VARCHAR(255),
    budget DECIMAL(10,2),
    minutes INT,
    film_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (film_id) REFERENCES film(id)
);
CREATE TABLE IF NOT EXISTS characters (
    id SERIAL,
    description VARCHAR(255),
    cost DECIMAL(10,2),
    stock INT,
    scene_id INT,
-- Agregar otra columna extra (ejemplo: gender)
    gender VARCHAR(10), -- Columna extra para el género del personaje
    PRIMARY KEY (id),
    FOREIGN KEY (scene_id) REFERENCES scene(id)
);

