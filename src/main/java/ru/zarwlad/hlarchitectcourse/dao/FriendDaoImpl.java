package ru.zarwlad.hlarchitectcourse.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.zarwlad.hlarchitectcourse.entity.Friend;
import ru.zarwlad.hlarchitectcourse.rowmapper.FriendMapper;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FriendDaoImpl implements FriendDao{
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final static String SQL_FIND_BY_ID = "SELECT * FROM friends_view WHERE id = ?";
    private final static String SQL_FIND_ALL_PAGED = "SELECT * FROM friends_view LIMIT ? OFFSET ?";
    private final static String SQL_UPDATE = "UPDATE friends SET person_id = ?, friend_id = ?, is_accepted = ? WHERE id = ?";
    private final static String SQL_CREATE = "INSERT INTO friends (person_id, friend_id, is_accepted) VALUES (?, ?, ?)";
    private final static String SQL_DELETE = "DELETE FROM friends WHERE id = ?";
    private final static String SQL_FIND_ALL_FRIENDS_OF_PERSON = "SELECT * FROM friends_view WHERE person_id = ? AND is_accepted = TRUE";
    private final static String SQL_FIND_ALL_UNCONFIRMED_FRIENDSHIPS = "SELECT * FROM friends_view WHERE friend_id = ? AND is_accepted = FALSE";
    private final static String SQL_CHANGE_FRIENDSHIP_CONFIRMATION = "UPDATE friends SET is_accepted = ? WHERE person_id = ? AND friend_id = ?";
    private final static String SQL_REMOVE_FRIEND = "DELETE FROM friends WHERE person_id = ? AND friend_id = ?";
    private final static String SQL_RECALL_FRIENDSHIP_REQUEST = "DELETE FROM friends WHERE person_id = ? AND friend_id = ? AND is_accepted = FALSE";
    private final static String SQL_FIND_BY_PERSON_ID_AND_FRIEND_ID = "SELECT * FROM friends_view WHERE person_id = ? AND friend_id = ?";

    @Override
    public Optional<Friend> getById(Long id) {
        return jdbcTemplate.query(SQL_FIND_BY_ID, new FriendMapper(), id).stream().findFirst();
    }

    @Override
    public Optional<List<Friend>> getAllPaged(Integer limit, Integer offset) {
        return Optional.of(jdbcTemplate.query(SQL_FIND_ALL_PAGED, new FriendMapper(), limit, offset));
    }

    @Override
    public Boolean update(Friend friend) {
        return jdbcTemplate.update(
                SQL_UPDATE,
                friend.getPerson().getId(),
                friend.getFriend().getId(),
                friend.getIsAccepted(),
                friend.getId()) > 0;
    }

    @Override
    public Boolean create(Friend friend) {
        return createFriendByIds(friend.getPerson().getId(), friend.getFriend().getId(), false);
    }

    @Override
    public Boolean delete(Friend friend) {
        return jdbcTemplate.update(SQL_DELETE, friend.getId()) > 0;
    }

    @Override
    public Boolean createFriendByIds(Long personId, Long friendId, Boolean isAccepted) {
        return jdbcTemplate.update(SQL_CREATE, personId, friendId, isAccepted) > 0;
    }

    @Override
    public Optional<List<Friend>> getAllFriendsOfPerson(Long personId) {
        return Optional.of(jdbcTemplate.query(SQL_FIND_ALL_FRIENDS_OF_PERSON, new FriendMapper(), personId));
    }

    @Override
    public Optional<List<Friend>> getAllUnconfirmedFriendships(Long personId) {
        return Optional.of(jdbcTemplate.query(SQL_FIND_ALL_UNCONFIRMED_FRIENDSHIPS, new FriendMapper(), personId));
    }

    @Override
    @Transactional
    public Boolean confirmFriendship(Long personId, Long friendId) {
        Boolean friendshipCreated = createFriendByIds(personId, friendId, true);
        Boolean friendSide = jdbcTemplate.update(
                SQL_CHANGE_FRIENDSHIP_CONFIRMATION,
                true,
                friendId,
                personId) > 0;

        return friendshipCreated && friendSide;
    }

    @Override
    @Transactional
    public Boolean removeFriend(Long personId, Long friendId) {
        Boolean cancelFriendship = jdbcTemplate.update(
                SQL_CHANGE_FRIENDSHIP_CONFIRMATION,
                false,
                friendId,
                personId) > 0;

        Boolean removeFriend = jdbcTemplate.update(
                SQL_REMOVE_FRIEND,
                personId,
                friendId) > 0;
        return cancelFriendship && removeFriend;
    }

    @Override
    public Boolean recallFriendRequest(Long personId, Long friendId) {
        return jdbcTemplate.update(SQL_RECALL_FRIENDSHIP_REQUEST, personId, friendId) > 0;
    }

    @Override
    public Optional<Friend> findByPersonIdAndFriendId(Long personId, Long friendId) {
        return jdbcTemplate.query(SQL_FIND_BY_PERSON_ID_AND_FRIEND_ID, new FriendMapper(), personId, friendId)
                .stream()
                .findFirst();
    }
}
