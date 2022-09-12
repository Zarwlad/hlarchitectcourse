package ru.zarwlad.hlarchitectcourse.dao;

import ru.zarwlad.hlarchitectcourse.entity.Friend;
import ru.zarwlad.hlarchitectcourse.entity.Person;

import java.util.List;
import java.util.Optional;

public interface FriendDao extends Dao<Friend> {
    Optional<List<Friend>> getAllFriendsOfPerson(Long personId);
    Optional<List<Friend>> getAllUnconfirmedFriendships(Long personId);
    Boolean confirmFriendship(Long personId, Long friendId);
    Boolean removeFriend (Long personId, Long friendId);
    Boolean createFriendByIds(Long personId, Long friendId, Boolean isAccepted);
    Boolean recallFriendRequest(Long personId, Long friendId);
    Optional<Friend> findByPersonIdAndFriendId(Long personId, Long friendId);
}
