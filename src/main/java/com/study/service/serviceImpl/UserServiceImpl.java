package com.study.service.serviceImpl;

import com.study.dao.UserDao;
import com.study.domain.User;
import com.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id).get();
    }

    @Override
    public void update(User user) {
        userDao.save(user);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
        userDao.deleteById(Math.toIntExact(id));
        }
    }

    @Override
    public List<User> searchAny(String keyword) {
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<String> username = root.get("username");
                Predicate predicate = criteriaBuilder.like(username, "%" + keyword + "%");
                return predicate;
            }
        };

        return  userDao.findAll(specification);
    }

    @Override
    public Page<User> findPage(Integer currentPage, Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage,pageSize);
        return userDao.findAll(pageable);
    }
}
