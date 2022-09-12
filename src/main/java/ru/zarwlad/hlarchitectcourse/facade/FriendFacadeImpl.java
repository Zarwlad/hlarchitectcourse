package ru.zarwlad.hlarchitectcourse.facade;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zarwlad.hlarchitectcourse.dtomapper.FriendDtoMapper;
import ru.zarwlad.hlarchitectcourse.model.FriendDto;
import ru.zarwlad.hlarchitectcourse.model.NewFriendDto;
import ru.zarwlad.hlarchitectcourse.service.FriendService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class FriendFacadeImpl implements FriendFacade{
    @Autowired
    FriendService friendService;

    @Autowired
    FriendDtoMapper friendDtoMapper;

    @Override
    public Boolean create(NewFriendDto newFriendDto) {
        return friendService.create(friendDtoMapper.fromNewDto(newFriendDto));
    }

    @Override
    public Boolean update(FriendDto friendDto) {
        return friendService.update(friendDtoMapper.fromDto(friendDto));
    }

    @Override
    public FriendDto get(Long id) {
        return friendDtoMapper.toDto(friendService.get(id));
    }

    @Override
    public List<FriendDto> getPage(Integer limit, Integer offset) {
        return friendService.getPage(limit, offset).stream()
                .map(friend -> friendDtoMapper.toDto(friend))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean delete(FriendDto friendDto) {
        return friendService.delete(friendDtoMapper.fromDto(friendDto));
    }

    @Override
    public List<FriendDto> getAllFriendsOfPerson(Long personId) {
        return friendService.getAllFriendsOfPerson(personId).stream()
                .map(friend -> friendDtoMapper.toDto(friend))
                .collect(Collectors.toList());
    }

    @Override
    public List<FriendDto> getAllUnconfirmedFriendships(Long personId) {
        return friendService.getAllUnconfirmedFriendships(personId).stream()
                .map(friend -> friendDtoMapper.toDto(friend))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean confirmFriendship(Long personId, Long friendId) {
        return friendService.confirmFriendship(personId, friendId);
    }

    @Override
    public Boolean removeFriend(Long personId, Long friendId) {
        return friendService.removeFriend(personId, friendId);
    }

    @Override
    public Boolean createFriendByIds(Long personId, Long friendId) {
        return friendService.createFriendByIds(personId, friendId);
    }

    @Override
    public Boolean recallFriendRequest(Long personId, Long friendId) {
        return friendService.recallFriendRequest(personId, friendId);
    }
}
