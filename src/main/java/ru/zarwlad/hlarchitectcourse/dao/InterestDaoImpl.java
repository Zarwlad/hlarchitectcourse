package ru.zarwlad.hlarchitectcourse.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.zarwlad.hlarchitectcourse.entity.Interest;
import ru.zarwlad.hlarchitectcourse.rowmapper.InterestMapper;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InterestDaoImpl implements InterestDao{
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SQL_FIND_BY_ID = "SELECT * FROM interests WHERE id = ?";
    private static final String SQL_FIND_PAGED = "SELECT * FROM interests LIMIT ? OFFSET ?";
    private static final String SQL_CREATE = "INSERT INTO interests (tag) VALUES (?)";
    private static final String SQL_UPDATE = "UPDATE interests SET tag = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM interests WHERE id = ?";
    private static final String SQL_FIND_BY_TAG = "SELECT * FROM interests WHERE tag = ?";
    private static final String SQL_FIND_ALL_IN_LIST = "SELECT * FROM interests WHERE tag IN (:tags)";

    @Override
    public Optional<Interest> getById(Long id) {
        return jdbcTemplate.query(SQL_FIND_BY_ID, new InterestMapper(), id).stream().findFirst();
    }

    @Override
    public Optional<List<Interest>> getAllPaged(Integer limit, Integer offset) {
        return Optional.of(jdbcTemplate.query(SQL_FIND_PAGED, new InterestMapper(), limit, offset));
    }

    @Override
    public Boolean update(Interest interest) {
        return jdbcTemplate.update(SQL_UPDATE, interest.getTag(), interest.getId()) > 0;
    }

    @Override
    public Boolean create(Interest interest) {
        return jdbcTemplate.update(SQL_CREATE, interest.getTag()) > 0;
    }

    @Override
    public Boolean delete(Interest interest) {
        return jdbcTemplate.update(SQL_DELETE, interest.getId()) > 0;
    }

    @Override
    public Optional<Interest> findByTag(String tag) {
        return jdbcTemplate.query(SQL_FIND_BY_TAG, new InterestMapper(), tag).stream().findFirst();
    }

    @Override
    public Optional<List<Interest>> findAllTagsByList(List<String> tags) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("tags", tags);
        return Optional.of(namedParameterJdbcTemplate.query(SQL_FIND_ALL_IN_LIST, sqlParameterSource, new InterestMapper()));
    }
}
