package com.zhangjun.thymeleaf.controller;

import com.zhangjun.thymeleaf.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.*;

@Controller
public class Home {

    @RequestMapping("/home")
    public String home(Model model){
        ArrayList<User> list = new ArrayList<>();

        list.add(new User("0001","张三","123456789","1778790864@qq.com",new Date(),1));
        list.add(new User("0002","韩佳","123456789","1778790864@qq.com",new Date(),1));
        list.add(new User("0003","马成光","123456789","1778790864@qq.com",new Date(),1));
        list.add(new User("0004","杨官元","123456789","1778790864@qq.com",new Date(),1));
        list.add(new User("0005","李良沙","123456789","1778790864@qq.com",new Date(),1));
        list.add(new User("0006","马文俊","123456789","1778790864@qq.com",new Date(),1));
        list.add(new User("0007","马俊","123456789","1778790864@qq.com",new Date(),1));
        list.add(new User("0008","杨磊","123456789","1778790864@qq.com",new Date(),1));
        list.add(new User("0009","张根华","123456789","1778790864@qq.com",new Date(),1));
        list.add(new User("0010","张三","123456789","1778790864@qq.com",new Date(),1));

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        Map<String, Object> student = new HashMap<String, Object>(){{
            put("sid", "0001");
            put("sname", "张三");
            put("sage", "20");
            put("scourse", new HashMap<String, String>(){{
                put("cname", "科幻，悬疑，惊悚");
                put("cscore", "喜欢,一般,一般");
            }});
        }};
        resultList.add(student);
        student = new HashMap<String, Object>(){{
            put("sid", "0002");
            put("sname", "韩佳");
            put("sage", "30");
            put("scourse", new HashMap<String, String>(){{
                put("cname", "科幻，悬疑，惊悚");
                put("cscore", "不喜欢,一般,一般");
            }});
        }};
        resultList.add(student);

        student = new HashMap<String, Object>(){{
            put("sid", "0003");
            put("sname", "马成光");
            put("sage", "25");
            put("scourse", new HashMap<String, String>(){{
                put("cname", "科幻，悬疑，惊悚");
                put("cscore", "喜欢,喜欢,一般");
            }});
        }};
        resultList.add(student);

        student = new HashMap<String, Object>(){{
            put("sid", "0004");
            put("sname", "杨官元");
            put("sage", "20");
            put("scourse", new HashMap<String, String>(){{
                put("cname", "科幻，悬疑，惊悚");
                put("cscore", "喜欢,喜欢,一般");
            }});
        }};
        resultList.add(student);

        student = new HashMap<String, Object>(){{
            put("sid", "0005");
            put("sname", "李良沙");
            put("sage", "20");
            put("scourse", new HashMap<String, String>(){{
                put("cname", "科幻，悬疑，惊悚");
                put("cscore", "喜欢,喜欢,一般");
            }});
        }};
        resultList.add(student);

        student = new HashMap<String, Object>(){{
            put("sid", "0006");
            put("sname", "马文俊");
            put("sage", "20");
            put("scourse", new HashMap<String, String>(){{
                put("cname", "科幻，悬疑，惊悚");
                put("cscore", "喜欢,喜欢,一般");
            }});
        }};
        resultList.add(student);

        student = new HashMap<String, Object>(){{
            put("sid", "0007");
            put("sname", "马俊");
            put("sage", "20");
            put("scourse", new HashMap<String, String>(){{
                put("cname", "科幻，悬疑，惊悚");
                put("cscore", "喜欢,喜欢,一般");
            }});
        }};
        resultList.add(student);

        student = new HashMap<String, Object>(){{
            put("sid", "0008");
            put("sname", "杨磊");
            put("sage", "20");
            put("scourse", new HashMap<String, String>(){{
                put("cname", "科幻，悬疑，惊悚");
                put("cscore", "喜欢,喜欢,一般");
            }});
        }};
        resultList.add(student);

        student = new HashMap<String, Object>(){{
            put("sid", "0009");
            put("sname", "张根华");
            put("sage", "20");
            put("scourse", new HashMap<String, String>(){{
                put("cname", "科幻，悬疑，惊悚");
                put("cscore", "喜欢,喜欢,一般");
            }});
        }};
        resultList.add(student);

        student = new HashMap<String, Object>(){{
            put("sid", "0010");
            put("sname", "张三");
            put("sage", "20");
            put("scourse", new HashMap<String, String>(){{
                put("cname", "科幻，悬疑，惊悚");
                put("cscore", "喜欢,喜欢,一般");
            }});
        }};
        resultList.add(student);

        model.addAttribute("users",list);
        model.addAttribute("resultList", resultList);

        return "home";
    }
}
