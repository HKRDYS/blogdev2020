package com.example.demo.service.impl;

import com.example.demo.dao.IUserMapper;
import com.example.demo.model.entity.User;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/*
* User服务接口实现类
* */
//添加事务控制
@Transactional
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserMapper iUserMapper;//注入数据访问层接口
    @Autowired
    private RedisTemplate redisTemplate;//注入redis接口做数据缓存
    @Override
    public User findByLoginName(String loginName) {
        Object obj = redisTemplate.opsForValue().get(loginName);
        if (obj != null){
            return (User) obj;
        }else {
            User user = iUserMapper.findByLoginName(loginName);
            if (user != null){
                redisTemplate.opsForValue().set(user.getLoginName(),user);
                return user;
            }else {
                return null;
            }
        }
    }

    @Override
    public List<User> findAllUser() {

        Object obj = redisTemplate.opsForValue().get("allUser");
        if (obj != null){
            return (List<User>) obj;
        }else {
            List<User> list = iUserMapper.findAllUser();
            if (list != null && list.size() > 0){
                redisTemplate.opsForValue().set("allUser",list);
            }
            return null;
        }

    }

    @Override
    public String delUser(User user) {
        //Integer -> int
        Integer num = iUserMapper.delUser(user);
        if (num != 0){
            //成功删除一个用户后，可能影响了allUser中的数据，也可能影响了LoginName_List中的数据
            //所以还要处理这两个缓存
            //更新allUser缓存
            Object obj = redisTemplate.opsForValue().get("allUser");
            if (obj != null){
                List<User> list = (List<User>) obj;
                //使用以下for增强性循环做迭代，并且remove掉list中元素时会出异常
                for (User u : list){
                    if (u.getLoginName().equals(user.getLoginName())){
                        list.remove(u);
                        return  delUser(user);
                    }
                }
                //改用lambda表达式来做
                list.stream().map((u) -> !u.getLoginName().equals(u.getLoginName())).collect(Collectors.toList());
                redisTemplate.opsForValue().set("allUser",list);
            }
            //更新loginName_list缓存
            Object obj2 = redisTemplate.opsForValue().get(user.getLoginName() + "_list");
            if (obj2 != null){
                List<User> list2 = (List<User>) obj2;
                list2.stream().map((u) -> !u.getLoginName().equals(u.getLoginName())).collect(Collectors.toList());
                redisTemplate.opsForValue().set(user.getLoginName() + "_list",list2);
            }
            redisTemplate.delete(user.getLoginName());
        }
        return num == 0 ? "删除失败" : "删除成功";

    }

    @Override
    public String modifyUser(User user) {
        Integer num = iUserMapper.modifyUser(user);
        if (num != 0){
            //修改成功后可能会影响allUser和Login_list中缓存的数据，所以需要更新
            //更新allUser缓存
            Object obj = redisTemplate.opsForValue().get("addUser");
            if (obj != null){
                List<User> list = (List<User>) obj;
                for (User u:list){
                    if (u.getLoginName().equals(user.getLoginName())){
                        //此处移除一个元素，再添加一个元素不会出错，注意要非常小心使用remove
                        list.remove(u);
                        list.add(user);
                    }
                }
                redisTemplate.opsForValue().set("allUser",list);
            }
            //更新loginName_list缓存
            Object obj2 = redisTemplate.opsForValue().get(user.getLoginName() + "_list");
            if (obj2 != null){
                List<User> list2 = (List<User>) obj2;
                for (User u : list2){
                    if (u.getLoginName().equals(user.getLoginName())) {
                        list2.remove(u);
                        list2.add(user);
                    }
                }
                redisTemplate.opsForValue().set(user.getLoginName() + "_list",list2);
            }
            redisTemplate.opsForValue().set(user.getLoginName(),user);
        }
        return num == 0 ? "修改失败" : "修改成功";
    }

    @Override
    public List<User> findUser(User user) {
        Object obj = redisTemplate.opsForValue().get(user.getLoginName() + "_list");
        if (obj != null){
            return (List<User>) obj;
        }else {
            List<User> list =iUserMapper.findUser(user);
            if (list != null && list.size() > 0){
                redisTemplate.opsForValue().set(user.getLoginName() + "_list",list);
            }
            return list;
        }
    }

    @Override
    public String addUser(User user) {
        //先查找有同名账号没，如果有，禁止添加
        User oldUser = iUserMapper.findByLoginName(user.getLoginName());
        if (oldUser != null){
            return "已经存在同名用户，不能添加";
        }else {
            Integer num = iUserMapper.addUser(user);
            if (num != 0){
                User newUser = iUserMapper.findByLoginName(user.getLoginName());
                //还会影响allUser和loginName_list缓存
                //更新allUser缓存
                Object obj = redisTemplate.opsForValue().get("allUser");
                if (obj != null){
                    List<User> list = (List<User>) obj;
                    list.add(newUser);
                    redisTemplate.opsForValue().set("allUser",list);
                }
                //更新loginName_list缓存
                Object obj2 = redisTemplate.opsForValue().get(newUser.getLoginName() + "_list");
                if (obj2 != null){
                    List<User> list2 = (List<User>)obj2;
                    list2.add(newUser);
                    redisTemplate.opsForValue().set(newUser.getLoginName() + "_list",list2);

                }
                redisTemplate.opsForValue().set(newUser.getLoginName(),newUser);
            }
            return num == 0 ? "添加失败" : "添加成功";
        }

    }
}
