package com.zhangjun.thymeleaf.controller;

import com.zhangjun.thymeleaf.bean.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class Hello {

  private static List<Student> students = new ArrayList<>();
  static {
        students.add(new Student("张三", 1,1));
        students.add(new Student("李四", 2,0));
        students.add(new Student("王五", 3,0));
        students.add(new Student("王二麻子", 4,1));
        students.add(new Student("错误", 5,3));
    }

    /**
     * Model主要用于将数据传递到页面，一般采用model.addAttribute("key", object)的方式，页面通过各种表达式将其显示出来；
     * ModelAndView有两个作用，一个是上面Model的作用；另一个就是可以设置view，也就是跳转方向，view既可以是字符串，也可以是View类型的object。
     * @param model
     * @return
     */
    @RequestMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("name", "thymeleaf");
        model.addAttribute("count", 5);



//        JSONObject o = null;
//        JSONArray jsonArray = new JSONArray();
//        for(int i = 0,max=5;i<5;i++) {
//            o = new JSONObject();
//            o.put("name","香蕉"+i);
//            o.put("id",8);
//            o.put("vip",i);
//            jsonArray.put(o);
//        }
//        model.addAttribute("students", jsonArray);



        model.addAttribute("students", students);
        model.addAttribute("student", students.get(0));

        return "hello";
    }


    /**
     * ModelAttribute 从视图中获取一个对象
     * @param student
     * @param model
     * @return
     */
    @RequestMapping(value = "/save", method= RequestMethod.POST)
    public String save(@ModelAttribute(value="student") Student student,Model model) {
        model.addAttribute("student", student);
        return "hello";
    }


    @ResponseBody
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public String add(@ModelAttribute Student student){
        int id = student.getId();
        String name = student.getName();
        return id+"__"+name;
    }




    @RequestMapping(value = "/check/{id}")
    @ResponseBody
    public String check(Model model,@PathVariable("id") int id) {
        Student student = new Student(students.get(id-1).getName(),id,students.get(id-1).getVip());
        return student.toString();
    }

}
