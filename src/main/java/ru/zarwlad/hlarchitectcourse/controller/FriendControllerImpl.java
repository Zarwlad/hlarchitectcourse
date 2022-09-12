package ru.zarwlad.hlarchitectcourse.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.zarwlad.hlarchitectcourse.facade.FriendFacade;
import ru.zarwlad.hlarchitectcourse.model.FriendDto;
import ru.zarwlad.hlarchitectcourse.model.NewFriendDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("${BASE_URL}/friends")
@RequiredArgsConstructor
public class FriendControllerImpl implements FriendController{
    @Autowired
    FriendFacade friendFacade;

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FriendDto getById(@PathVariable Long id) {
        return friendFacade.get(id);
    }

    @Override
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<FriendDto> getAll(int limit, int offset) {
        return friendFacade.getPage(limit, offset);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody NewFriendDto newFriendDto) {
        friendFacade.create(newFriendDto);
    }

    @Override
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@Valid @RequestBody FriendDto friendDto) {
        friendFacade.update(friendDto);
    }

    @Override
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void delete(@Valid @RequestBody FriendDto friendDto) {
        friendFacade.delete(friendDto);
    }

    @Override
    @GetMapping("person/{personId}/confirmed")
    @ResponseStatus(HttpStatus.OK)
    public List<FriendDto> getAllFriendsOfPerson(@PathVariable Long personId) {
        return friendFacade.getAllFriendsOfPerson(personId);
    }

    @Override
    @GetMapping("person/{personId}/unconfirmed")
    @ResponseStatus(HttpStatus.OK)
    public List<FriendDto> getAllUnconfirmedFriendships(@PathVariable Long personId) {
        return friendFacade.getAllUnconfirmedFriendships(personId);
    }

    @Override
    @PostMapping("person/{personId}/confirm-friendship/{friendId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void confirmFriendship(@PathVariable Long personId,@PathVariable Long friendId) {
        friendFacade.confirmFriendship(personId, friendId);
    }

    @Override
    @PostMapping("person/{personId}/delete-from-friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeFriend(@PathVariable Long personId, @PathVariable Long friendId) {
        friendFacade.removeFriend(personId, friendId);
    }

    @Override
    @PostMapping("person/{personId}/friend-requests/{friendId}/make")
    @ResponseStatus(HttpStatus.CREATED)
    public void createFriendByIds(@PathVariable Long personId, @PathVariable Long friendId) {
        friendFacade.createFriendByIds(personId, friendId);
    }

    @Override
    @PostMapping("person/{personId}/friend-requests/{friendId}/recall")
    @ResponseStatus(HttpStatus.OK)
    public void recallFriendRequest(@PathVariable Long personId, @PathVariable Long friendId) {
        friendFacade.recallFriendRequest(personId, friendId);
    }
}
