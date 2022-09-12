package ru.zarwlad.hlarchitectcourse.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zarwlad.hlarchitectcourse.dao.FriendDao;
import ru.zarwlad.hlarchitectcourse.entity.Friend;
import ru.zarwlad.hlarchitectcourse.entity.Person;
import ru.zarwlad.hlarchitectcourse.errors.ResourceNotFoundException;
import ru.zarwlad.hlarchitectcourse.errors.SavingDataException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendDao friendDao;

    @Override
    public Boolean create(Friend friend) {
        Friend savedFriend = friendDao.findByPersonIdAndFriendId(friend.getPerson().getId(), friend.getFriend().getId())
                .orElseGet(() -> new Friend(-1L, new Person(), new Person(), false));

        if (savedFriend.getId() == -1L)
            return friendDao.create(friend);
        else
            throw new SavingDataException(String.format("Friendship between person with id %d and friend with id %d already exists", friend.getPerson().getId(), friend.getFriend().getId()));

    }

    @Override
    public Boolean update(Friend friend) {
        friendDao.findByPersonIdAndFriendId(friend.getPerson().getId(), friend.getFriend().getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format("The friendship between person with id %d and friend with id %d has not been found", friend.getPerson().getId(), friend.getFriend().getId()))
                );
        return friendDao.update(friend);
    }

    @Override
    public Friend get(Long id) {
        return friendDao.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Friendship id %d has not been found", id)));
    }

    @Override
    public List<Friend> getPage(Integer limit, Integer offset) {
        return friendDao.getAllPaged(limit, offset)
                .orElseThrow(() -> new ResourceNotFoundException(
                                String.format("The friend list is empty, limit %d, offset %d", limit, offset)
                        )
                );
    }

    @Override
    public Boolean delete(Friend friend) {
        friendDao.findByPersonIdAndFriendId(friend.getPerson().getId(), friend.getFriend().getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format("The friendship between person with id %d and friend with id %d has not been found", friend.getPerson().getId(), friend.getFriend().getId()))
                );
        return friendDao.delete(friend);
    }

    @Override
    public List<Friend> getAllFriendsOfPerson(Long personId) {
        return friendDao.getAllFriendsOfPerson(personId)
                .orElseThrow(() -> new ResourceNotFoundException(
                                String.format("The friend list for person with %d is empty", personId)
                        )
                );
    }

    @Override
    public List<Friend> getAllUnconfirmedFriendships(Long personId) {
        return friendDao.getAllUnconfirmedFriendships(personId).orElseThrow(() -> new ResourceNotFoundException(
                        String.format("No unconfirmed friends for person with id %d", personId)
                )
        );
    }

    @Override
    public Boolean confirmFriendship(Long personId, Long friendId) {
        Friend savedFriendshipRequest = friendDao.findByPersonIdAndFriendId(friendId, personId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format("The friendship request for person with id %d and friend with id %d has not been found", personId, friendId))
                );

        if (savedFriendshipRequest.getIsAccepted())
            throw new SavingDataException(String.format("The friendship request for person with id %d and from the friend with id %d has been already approved", personId, friendId));

        return friendDao.confirmFriendship(personId, friendId);
    }

    @Override
    public Boolean removeFriend(Long personId, Long friendId) {
        friendDao.findByPersonIdAndFriendId(personId, friendId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format("The friendship between person with id %d and friend with id %d has not been found", personId, friendId))
                );
        return friendDao.removeFriend(personId, friendId);
    }

    @Override
    public Boolean createFriendByIds(Long personId, Long friendId) {
        Friend savedFriend = friendDao.findByPersonIdAndFriendId(personId, friendId)
                .orElseGet(() -> new Friend(-1L, new Person(), new Person(), false));

        if (savedFriend.getId() == -1L)
            return friendDao.createFriendByIds(personId, friendId, false);
        else
            throw new SavingDataException(String.format("Friendship between person with id %d and friend with id %d already exists", personId, friendId));
    }

    @Override
    public Boolean recallFriendRequest(Long personId, Long friendId) {
        friendDao.findByPersonIdAndFriendId(personId, friendId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format("The friendship between person with id %d and friend with id %d has not been found", personId, friendId))
                );

        return friendDao.recallFriendRequest(personId, friendId);
    }
}
