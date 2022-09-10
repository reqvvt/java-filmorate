CREATE TABLE "user"(
    "user_id" INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    "email" VARCHAR(255) NOT NULL,
    "login" VARCHAR(255) NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "birthday" DATE NOT NULL
);

CREATE TABLE "mpa"(
    "rating_id" INTEGER PRIMARY KEY,
    "rating_value" VARCHAR(5) NOT NULL
);

CREATE TABLE "film"(
    "film_id" INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    "title" VARCHAR(255) NOT NULL,
    "description" VARCHAR(255) NOT NULL,
    "releaseDate" DATE NOT NULL,
    "duration" INTEGER NOT NULL,
    "rating_id" INTEGER NOT NULL,
    CONSTRAINT fk_rating_id FOREIGN KEY("rating_id") REFERENCES mpa("rating_id")
);

CREATE TABLE "genre"(
    "genre_id" INTEGER PRIMARY KEY,
    "genre" VARCHAR(255) NOT NULL
);

CREATE TABLE "film_genre"(
    "film_id" INTEGER NOT NULL,
    "genre_id" INTEGER NOT NULL,
    PRIMARY KEY (film_id, genre_id),
    CONSTRAINT fk_film_genre_film_id FOREIGN KEY (film_id) REFERENCES film(film_id) ON DELETE CASCADE,
    CONSTRAINT fk_film_genre_genre_id FOREIGN KEY (genre_id) REFERENCES genre(genre_id) ON DELETE CASCADE
);

CREATE TABLE "like"(
    "user_id" INTEGER NOT NULL,
    "film_id" INTEGER NOT NULL,
    PRIMARY KEY (user_id, film_id),
    CONSTRAINT fk_film_like_user_id FOREIGN KEY (user_id) REFERENCES "user"(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_film_like_film_id FOREIGN KEY (film_id) REFERENCES film(film_id) ON DELETE CASCADE

);

CREATE TABLE "friend"(
    "user_id" INTEGER NOT NULL,
    "friend_id" INTEGER NOT NULL,
    PRIMARY KEY (user_id, friend_id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES "user"(user_id) ON DELETE CASCADE
);

