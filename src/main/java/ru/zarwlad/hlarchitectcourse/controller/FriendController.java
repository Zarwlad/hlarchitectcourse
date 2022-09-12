package ru.zarwlad.hlarchitectcourse.controller;

import ru.zarwlad.hlarchitectcourse.model.FriendDto;
import ru.zarwlad.hlarchitectcourse.model.NewFriendDto;

import java.util.List;

public interface FriendController extends Controller<FriendDto, NewFriendDto> {
    List<FriendDto> getAllFriendsOfPerson(Long personId);
    List<FriendDto> getAllUnconfirmedFriendships(Long personId);
    void confirmFriendship(Long personId, Long friendId);
    void removeFriend (Long personId, Long friendId);
    void createFriendByIds(Long personId, Long friendId);
    void recallFriendRequest(Long personId, Long friendId);
}
