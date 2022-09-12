package ru.zarwlad.hlarchitectcourse.dtomapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zarwlad.hlarchitectcourse.entity.Friend;
import ru.zarwlad.hlarchitectcourse.model.FriendDto;
import ru.zarwlad.hlarchitectcourse.model.NewFriendDto;

@Component
public class FriendDtoMapperImpl implements FriendDtoMapper{
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    PersonDtoMapper personDtoMapper;

    @Override
    public Friend fromDto(FriendDto dto) {
        Friend friend = modelMapper.map(dto, Friend.class);
        friend.setFriend(personDtoMapper.fromDto(dto.getFriendDto()));
        friend.setPerson(personDtoMapper.fromDto(dto.getPersonDto()));

        return friend;
    }

    @Override
    public FriendDto toDto(Friend entity) {
        FriendDto friendDto = modelMapper.map(entity, FriendDto.class);
        friendDto.setFriendDto(personDtoMapper.toDto(entity.getFriend()));
        friendDto.setPersonDto(personDtoMapper.toDto(entity.getPerson()));

        return friendDto;
    }

    @Override
    public Friend fromNewDto(NewFriendDto newDto) {
        Friend friend = modelMapper.map(newDto, Friend.class);
        friend.setPerson(personDtoMapper.fromNewDto(newDto.getNewPersonDto()));
        friend.setFriend(personDtoMapper.fromNewDto(newDto.getNewFriendDto()));

        return friend;
    }
}
