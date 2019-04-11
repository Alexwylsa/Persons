package com.alexwylsa.Persons.parameters;

import com.alexwylsa.Persons.domain.User;
import com.alexwylsa.Persons.exceptions.AlreadyExistsException;
import com.alexwylsa.Persons.exceptions.DeleteUserExeption;
import com.alexwylsa.Persons.exceptions.NotFoundException;
import com.alexwylsa.Persons.repo.UserRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Log4j2
@Service
public class SortParameters {
    @Autowired
    UserRepo userRepo;
    //parameters for sort and search
    public PageRequest parameters(Integer page, Integer size, String byColumn, Integer ascending) {
        if (Objects.isNull(byColumn) || byColumn.isEmpty()) {
            byColumn = "id";
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
    //count ascending or count descending
    private Sort.Direction parseDirection(Integer ascending) {
        if (ascending.equals(1)) {
            return Sort.Direction.ASC;
        } else {
            return Sort.Direction.DESC;
        }
    }

    public void deleteUserСheck(Long id, User user) {
        if (Objects.isNull(id)) {
            new DeleteUserExeption("user wrong id");
        }
        if (user.getId().equals(id)) {
            new DeleteUserExeption("user can't delete himself");
        }
        userRepo.findById(id).orElseThrow(() -> new NotFoundException());
    }

}
