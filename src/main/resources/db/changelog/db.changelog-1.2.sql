-- liquibASe formatted sql
-- changeset vzaremba:friends_view

CREATE VIEW social_network.friends_view AS
SELECT friends.id  AS id,
       person_id,
       friend_id,
       is_accepted,
       p.email     AS person_email,
       p.login     AS person_login,
       p.pASsword  AS person_password,
       p.name      AS person_name,
       p.surname   AS person_surname,
       p.age       AS person_age,
       p.gender    AS person_gender,
       p.city_id   AS person_city_id,
       c.city      AS person_city,
       c.region    AS person_region,
       p2.email    AS friend_email,
       p2.login    AS friend_login,
       p2.pASsword AS friend_password,
       p2.name     AS friend_name,
       p2.surname  AS friend_surname,
       p2.age      AS friend_age,
       p2.gender   AS friend_gender,
       p2.city_id  AS friend_city_id,
       c2.city     AS friend_city,
       c2.region   AS friend_region
FROM friends
         JOIN persons p ON p.id = friends.person_id
         JOIN persons p2 ON p2.id = friends.friend_id
         JOIN cities c ON c.id = p.city_id
         JOIN cities c2 ON c2.id = p2.city_id;