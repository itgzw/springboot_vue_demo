package com.study.service;

import com.study.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Integer id);

    void update(User user);

    void delete(Long[] ids);

    List<User> searchAny(String keyword);

    Page<User> findPage(Integer currentPage, Integer pageSize);
}
