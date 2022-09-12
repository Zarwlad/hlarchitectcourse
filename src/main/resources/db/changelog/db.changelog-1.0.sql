--liquibase formatted sql

--changeset vzaremba:init
CREATE
DATABASE IF NOT EXISTS social_network;

CREATE TABLE social_network.interests
(
    id  BIGINT auto_increment NOT NULL,
    tag varchar(100) NOT NULL,
    CONSTRAINT interests_PK PRIMARY KEY (id)
) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE social_network.cities
(
    id     BIGINT auto_increment NOT NULL,
    city   varchar(255) NOT NULL,
    region varchar(255) NOT NULL,
    CONSTRAINT cities_PK PRIMARY KEY (id)
) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE social_network.persons
(
    id       BIGINT auto_increment NOT NULL,
    email    varchar(255) NOT NULL,
    login    varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    name     varchar(500) NOT NULL,
    surname  varchar(500) NOT NULL,
    age      INT          NOT NULL,
    gender   VARCHAR(10)  NOT NULL,
    city_id  BIGINT       NOT NULL,
    CONSTRAINT persons_PK PRIMARY KEY (id),
    CONSTRAINT persons_to_cities_FK FOREIGN KEY (city_id) REFERENCES social_network.cities (id)
) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE social_network.persons_interests
(
    id          BIGINT auto_increment NOT NULL,
    person_id   BIGINT NOT NULL,
    interest_id BIGINT NOT NULL,
    CONSTRAINT persons_interests_PK PRIMARY KEY (id),
    CONSTRAINT persons_interests_to_persons_FK FOREIGN KEY (person_id) REFERENCES social_network.persons (id) ON DELETE CASCADE,
    CONSTRAINT persons_interests_to_interests_FK FOREIGN KEY (interest_id) REFERENCES social_network.interests (id)
) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE social_network.friends
(
    id          BIGINT auto_increment NOT NULL,
    person_id   BIGINT NOT NULL,
    friend_id   BIGINT NOT NULL,
    is_accepted BOOL   NOT NULL,
    CONSTRAINT friends_PK PRIMARY KEY (id),
    CONSTRAINT friends_to_persons_FK FOREIGN KEY (person_id) REFERENCES social_network.persons (id),
    CONSTRAINT friends_to_persons_friends_FK_1 FOREIGN KEY (friend_id) REFERENCES social_network.persons (id)
) ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
