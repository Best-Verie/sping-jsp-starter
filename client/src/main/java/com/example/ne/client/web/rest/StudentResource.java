package com.example.ne.client.web.rest;


import com.example.ne.client.dao.Course;
import com.example.ne.client.dao.Student;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

import static com.example.ne.client.utils.Utility.formatURL;

@Controller()
@RequestMapping("/student")
public class StudentResource {

    @GetMapping("/allStudents")
    public String viewAllUsers(Model model, HttpServletRequest request) {
        //get all users

        if(request.getSession().getAttribute("token").toString().isEmpty()){
            return "redirect:/auth/login";
        }

        String token = request.getSession().getAttribute("token").toString();

        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate =  new RestTemplate();
        headers.setBearerAuth(token);
        HttpEntity<Object> entity = new HttpEntity<>(null, headers);

        ResponseEntity<Student[]> students = restTemplate.exchange(formatURL("/api/students"), HttpMethod.GET, entity, Student[].class);
        model.addAttribute("students", students.getBody());

        return "students/AllStudents";
    }

}
