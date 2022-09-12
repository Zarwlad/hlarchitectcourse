package ru.zarwlad.hlarchitectcourse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Класс описывает связи пользователя и друзей.
 * Если @isAccepted = false, значит заявка подана, но не одобрена.
 * В таком случае, в БД будет находиться одна запись, где User - заявитель, а Friend - возможный друг.
 * Если заявка подтверждена, то в БД будет также создана обратная запись: Friend - заявитель, а User - друг.
 * TODO: подумать над более красивой реализацией ребер графа, с учетом одно- и двунаправленности связи.
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Friend {
    private Long id;
    private Person person;
    private Person friend;
    private Boolean isAccepted;
}
