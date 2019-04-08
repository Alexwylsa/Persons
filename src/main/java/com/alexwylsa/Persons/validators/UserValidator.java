package com.alexwylsa.Persons.validators;

import com.alexwylsa.Persons.domain.User;
import com.alexwylsa.Persons.repo.UserRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Log4j2
@Service
public class UserValidator {
    @Autowired
    private UserRepo userRepo;

//    public void validateDeleteUser(Long id, User user) {
//        if (Objects.isNull(user) || user.isEmpty()) {
//            throw new RestException(ErrorCodes.USER_WRONG_ID);
//        }
//        if (id.getId().equals(user)) {
//            throw new RestException(ErrorCodes.USER_CANT_DELETE_HIMSELF);
//        }
//        userRepo.findById(user).orElseThrow(() -> new RestException(ErrorCodes.USER_NOT_EXIST));
//    }

    public PageRequest validatePagingAndThrowAndReturn(Integer page, Integer size, String byColumn, Integer ascending) {
        if (Objects.isNull(byColumn) || byColumn.isEmpty()) {
            byColumn = "username";
        }
        if (Objects.isNull(ascending)) {
            ascending = 1;
        }
        page = page - 1;
        Sort.Direction dir = parseDirection(ascending);
        if ("role".equals(byColumn)) {
            byColumn = "grantedAuthorities";
        }
        return PageRequest.of(page, size, new Sort(dir, byColumn));
    }

    private Sort.Direction parseDirection(Integer ascending) {
        if (ascending.equals(1)) {
            return Sort.Direction.ASC;
        } else {
            return Sort.Direction.DESC;
        }
    }
}
