package ru.zarwlad.hlarchitectcourse.service;

import ru.zarwlad.hlarchitectcourse.entity.Friend;

import java.util.List;
import java.util.Optional;

public interface FriendService extends CRUDService<Friend> {
    List<Friend> getAllFriendsOfPerson(Long personId);
    List<Friend> getAllUnconfirmedFriendships(Long personId);
    Boolean confirmFriendship(Long personId, Long friendId);
    Boolean removeFriend (Long personId, Long friendId);
    Boolean createFriendByIds(Long personId, Long friendId);
    Boolean recallFriendRequest(Long personId, Long friendId);
}
