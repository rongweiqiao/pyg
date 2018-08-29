package com.pyg.fm;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MyFreeMaker {
    private static Configuration configuration;
    static {
        configuration = new Configuration(Configuration.getVersion());
        try {
            configuration.setDirectoryForTemplateLoading(new File("C:\\Users\\rong\\IdeaProjects\\pyg" +
                    "\\pyg-html-utils\\src\\main\\resources\\template"));
            configuration.setDefaultEncoding("utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test01() throws Exception{
        Template template = configuration.getTemplate("test.ftl");
        Map<String,String> map = new HashMap<>();
        map.put("name","张三");
        map.put("message","欢迎来到freeMaker");
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("C:\\Users\\rong\\IdeaProjects\\pyg\\pyg-html-utils" +
                "\\src\\main\\resources\\out\\test.html")));
        template.process(map,writer);
        writer.close();
    }

    @Test
    public void test02() throws Exception{
        Template template = configuration.getTemplate("assign.ftl");
        FileWriter writer = new FileWriter("C:\\Users\\rong\\IdeaProjects\\pyg\\" +
                "pyg-html-utils\\src\\main\\resources\\out\\assign.html");
        template.process(null,writer);
        writer.close();
    }

    @Test
    public void test03() throws Exception{
        Template template = configuration.getTemplate("include.ftl");
        FileWriter writer = new FileWriter("C:\\Users\\rong\\IdeaProjects\\pyg\\" +
                "pyg-html-utils\\src\\main\\resources\\out\\include.html");
        template.process(null,writer);
        writer.close();
    }
    @Test
    public void test04() throws Exception{
        Template template = configuration.getTemplate("if.ftl");
        FileWriter writer = new FileWriter("C:\\Users\\rong\\IdeaProjects\\pyg\\" +
                "pyg-html-utils\\src\\main\\resources\\out\\if.html");
        Map map = new HashMap();
        map.put("value1",true);
        template.process(map,writer);
        writer.close();
    }

    @Test
    public void test05() throws Exception{
        Template template = configuration.getTemplate("list.ftl");
        FileWriter writer = new FileWriter("C:\\Users\\rong\\IdeaProjects\\pyg\\" +
                "pyg-html-utils\\src\\main\\resources\\out\\list.html");
        List<People> peopleList = new ArrayList<>();
        peopleList.add(new People("张三","20"));
        peopleList.add(new People("李四","30"));
        peopleList.add(new People("王五","35"));
        peopleList.add(new People("陈六","40"));
        Map map = new HashMap();
        map.put("peopleList",peopleList);
        template.process(map,writer);
        writer.close();
    }

    @Test
    public void test06() throws Exception{
        Template template = configuration.getTemplate("function.ftl");
        FileWriter writer = new FileWriter("C:\\Users\\rong\\IdeaProjects\\" +
                "pyg\\pyg-html-utils\\src\\main\\resources\\out\\function.html");
        Map map = new HashMap();
        List<People> peopleList = new ArrayList<>();
        peopleList.add(new People("张三","20"));
        peopleList.add(new People("李四","30"));
        peopleList.add(new People("王五","35"));
        peopleList.add(new People("陈六","40"));
        map.put("peopleList",peopleList);
        map.put("date",new Date());
        map.put("people","{'name':'陈独秀','age':18}");
        template.process(map,writer);
        writer.close();
    }

    @Test
    public void test07() throws Exception{
        Template template = configuration.getTemplate("number.ftl");
        FileWriter writer = new FileWriter("C:\\Users\\rong\\IdeaProjects\\" +
                "pyg\\pyg-html-utils\\src\\main\\resources\\out\\number.html");
        Map map = new HashMap();
        map.put("num",10000110);
        map.put("aaa",null);
        map.put("bbb","经常");
        template.process(map,writer);
        writer.close();
    }

}
