package ru.zarwlad.hlarchitectcourse.facade;

import ru.zarwlad.hlarchitectcourse.entity.Friend;
import ru.zarwlad.hlarchitectcourse.model.FriendDto;
import ru.zarwlad.hlarchitectcourse.model.NewFriendDto;

import java.util.List;

public interface FriendFacade extends Facade<FriendDto, NewFriendDto, Friend>{
    List<FriendDto> getAllFriendsOfPerson(Long personId);
    List<FriendDto> getAllUnconfirmedFriendships(Long personId);
    Boolean confirmFriendship(Long personId, Long friendId);
    Boolean removeFriend (Long personId, Long friendId);
    Boolean createFriendByIds(Long personId, Long friendId);
    Boolean recallFriendRequest(Long personId, Long friendId);
}
