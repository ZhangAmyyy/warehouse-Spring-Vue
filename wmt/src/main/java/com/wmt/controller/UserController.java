package com.wmt.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wmt.common.QueryPageParam;
import com.wmt.common.Result;
import com.wmt.entity.Menu;
import com.wmt.entity.User;
import com.wmt.service.MenuService;
import com.wmt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public List<User> list(){
        return userService.listAll();
    }

    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no){
        List list = userService.lambdaQuery().eq(User::getNo,no).list();
        return list.size()>0?Result.suc(list):Result.fail();
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody User user){//保存需要传进user对象

        return userService.save(user)? Result.suc():Result.fail();
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody User user){
        return userService.updateById(user)?Result.suc():Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return userService.removeById(id)?Result.suc():Result.fail();
    }

    //login
    //更新
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        List list= userService.lambdaQuery()
                .eq(User::getNo,user.getNo())
                .eq(User::getPassword,user.getPassword()).list();
        System.out.println(list);
//        return list.size()>0?Result.suc(list.get(0)):Result.fail();
        if(list.size()>0){
            User user1 =(User) list.get(0);
            List menuList =menuService.lambdaQuery().like(Menu::getMenuright,user1.getRoleId()).list();
            HashMap res=new HashMap();
            res.put("user",user1);
            res.put("menu1",menuList);
            return Result.suc(res);
        }
        return Result.fail();
    }


    //修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user){//保存需要传进user对象
        return userService.updateById(user);
    }


    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody User user){//保存需要传进user对象
        return userService.saveOrUpdate(user);
    }

    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id){//保存需要传进user对象
        return userService.removeById(id);
    }

    //模糊查询
    @PostMapping("/listP")
    public Result listP(@RequestBody User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(user.getName())){
            lambdaQueryWrapper.like(User::getName,user.getName());
        }
        return Result.suc(userService.list(lambdaQueryWrapper));
    }

    //精确查询
    @PostMapping("/listE")
    public List<User> listE(@RequestBody User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(User::getName,user.getName());
        return userService.list(lambdaQueryWrapper);
    }

    //分页
    @PostMapping("/listPage")
    public List<User> listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name= (String) param.get("name");

        Page<User> page= new Page<>();
        page.setCurrent(query.getPageNum());//设置有多少页
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getName,name);

        IPage result = userService.page(page,lambdaQueryWrapper);

        System.out.println(result.getTotal());//总记录数

        return result.getRecords();
    }

    //分页自定义
    @PostMapping("/listPageCus")
    public List<User> listPageCus(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name= (String) param.get("name");

        Page<User> page= new Page<>();
        page.setCurrent(query.getPageNum());//设置有多少页
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(User::getName,name);
        }


        IPage result = userService.pageCus(page, lambdaQueryWrapper);

        System.out.println(result.getTotal());//总记录数

        return result.getRecords();
    }

    //后端返回封装
    @PostMapping("/listPageEn")
    public Result listPageEn(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String gender =(String)param.get("gender");
        String roleId=(String)param.get("roleId");


        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(User::getName,name);
        }
        if(StringUtils.isNotBlank(gender) && !"null".equals(gender)){
            lambdaQueryWrapper.eq(User::getGender,gender);
        }
        if(StringUtils.isNotBlank(roleId) && !"null".equals(roleId)){
            lambdaQueryWrapper.eq(User::getRoleId,roleId);
        }


        //IPage result = userService.pageC(page);
        IPage result = userService.pageCus(page,lambdaQueryWrapper);

        System.out.println("total=="+result.getTotal());
        System.out.println(name);

        return Result.suc(result.getRecords(),result.getTotal());
    }
}


