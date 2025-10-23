package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UserController {

   @Autowired
   private JdbcTemplate jdbcTemplate;

   @Autowired
   private StringRedisTemplate redis;

   @GetMapping("/")
   public String root() {
       return "<h1> my compos tomcat</h1>";
   }

   @GetMapping("/hello")
   public String hello() {
       return "Hello from Spring Boot!";
   }

   @GetMapping("/db-count")
   public String dbcount() {
       try {
           String sql = "SELECT count(*) from member";
           Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
           return "Database successful. The result of 'count' is: " + result;
       } catch (Exception e) {
           e.printStackTrace();
           return "Database connection failed! Error: " + e.getMessage();
       }
   }

   @GetMapping("/db-insert")
   public String dbinsert() {
       try {
           String sql = "insert into member values ('ms', '1234', 'ms', '011')";
           Integer result = jdbcTemplate.update(sql);
           return "Database test successful insert " + result;
       } catch (Exception e) {
           e.printStackTrace();
           return "Database connection failed! Error: " + e.getMessage();
       }
   }

   @GetMapping("/db-insert-parm")
   public String dbinsertparm(
    @RequestParam String id,
    @RequestParam String pw,
    @RequestParam String name,
    @RequestParam String phone) {
       try {
           String sql = "insert into member values (?, ?, ?, ?)";
        int result = jdbcTemplate.update(sql, id, pw, name, phone);
        return "Database test successful insert " + result;
       } catch (Exception e) {
           e.printStackTrace();
           return "Database connection failed! Error: " + e.getMessage();
       }
   }

   @GetMapping("/redis-set")
    public String redisSet() {
        try {
            redis.opsForValue().set("key", "100");
            return "Redis SET OK. key=key, value=100";
        } catch (Exception e) {
            e.printStackTrace();
            return "Redis SET failed! Error: " + e.getMessage();
        }
    }

    @GetMapping("/redis-get")
    public String redisGet() {
        try {
            String value = redis.opsForValue().get("key");
            return "Redis GET OK. key=key >> " + value;
        } catch (Exception e) {
            e.printStackTrace();
            return "Redis GET failed! Error: " + e.getMessage();
        }
    }


   
}
