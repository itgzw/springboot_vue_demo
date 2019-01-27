package com.study.controller;

import com.study.domain.User;
import com.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 查询所有
     * @return
     */
    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public List<User> findAll(){
        return userService.findAll();
    }



    /**
     * 查询所有
     * @return
     */
    @RequestMapping(value = "/findById/{id}",method = RequestMethod.GET)
    public User findById(@PathVariable("id") Integer id){
        return userService.findById(id);
    }


    /**
     *修改
     * @param user
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public void update(@RequestBody User user){
        userService.update(user);
    }



    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public void delete(@RequestBody Long[] ids){
        userService.delete(ids);
    }

    /**
     * 搜索
     * @param keyword
     * @return
     */
    @RequestMapping("/searchAny/{keyword}")
    public List<User> searchAny(@PathVariable("keyword") String keyword){
        return userService.searchAny(keyword);
    }



    @RequestMapping("/findPage/{currentPage}/{pageSize}")
    public HashMap findPage(@PathVariable("currentPage") Integer currentPage, @PathVariable("pageSize") Integer pageSize ){
        Page<User> page = userService.findPage(currentPage, pageSize);
        HashMap map = new HashMap<>();
        List<User> users = page.getContent();
        int totalPages = page.getTotalPages();
        long totalElements = page.getTotalElements();
        map.put("users",users);
        map.put("totalPages",totalPages);
        map.put("totalElements",totalElements);
        return map;
    }

}
