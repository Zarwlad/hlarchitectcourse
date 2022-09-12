package ru.zarwlad.hlarchitectcourse.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.zarwlad.hlarchitectcourse.entity.City;
import ru.zarwlad.hlarchitectcourse.entity.Friend;
import ru.zarwlad.hlarchitectcourse.entity.Gender;
import ru.zarwlad.hlarchitectcourse.entity.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendMapper implements RowMapper<Friend> {
    /*
    Reading from friends_view
     */

    @Override
    public Friend mapRow(ResultSet rs, int rowNum) throws SQLException {
        City personCity = new City(
                rs.getLong("person_city_id"),
                rs.getString("person_city"),
                rs.getString("person_region")
        );

        Person person = new Person(
                rs.getLong("person_id"),
                rs.getString("person_email"),
                rs.getString("person_login"),
                rs.getString("person_password"),
                rs.getString("person_name"),
                rs.getString("person_surname"),
                rs.getInt("person_age"),
                Gender.valueOf(rs.getString("person_gender")),
                null,
                personCity,
                null
        );

        City friendCity = new City(
                rs.getLong("friend_city_id"),
                rs.getString("friend_city"),
                rs.getString("friend_region")
        );

        Person friend = new Person(
                rs.getLong("friend_id"),
                rs.getString("friend_email"),
                rs.getString("friend_login"),
                rs.getString("friend_password"),
                rs.getString("friend_name"),
                rs.getString("friend_surname"),
                rs.getInt("friend_age"),
                Gender.valueOf(rs.getString("friend_gender")),
                null,
                personCity,
                null
        );

        return new Friend(
                rs.getLong("id"),
                person,
                friend,
                rs.getBoolean("is_accepted")
        );
    }
}
