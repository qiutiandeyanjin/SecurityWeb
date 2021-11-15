package com.example.securityweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;
import java.util.HashMap;

@Controller
public class LoginController {
    @RequestMapping({"/", "/login.html"})
    public String login() {
        return "login";
    }

    @GetMapping(value = "/testJson")
    @ResponseBody
    public Map<String, Object> testJson() {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("code", 20000);
        responseData.put("msg", "请求成功");
        responseData.put("data", "success");
        return responseData;
    }

    @GetMapping(value = "/getFile")
    public void getFile(@RequestParam String filename, HttpServletRequest request, HttpServletResponse response) {
        String serverPath = request.getServletContext().getRealPath("/");
        File folder = new File(serverPath);
        if (!folder.exists()) {
            boolean bool = folder.mkdirs();
            if (bool) {
                System.out.println("创建文件夹成功！");
            }
        }

        int length;
        char[] ch;
        try (PrintWriter out = response.getWriter()) {
            File readFile = new File(serverPath + filename);
            try (Reader fr = new FileReader(readFile)) {
                ch = new char[1024];
                length = fr.read(ch);
                out.println(new String(ch, 0, length));
            } catch (IOException e) {
                out.println(e.getMessage());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
