package com.example.ne.client.web.rest;


import com.example.ne.client.dao.Course;
import com.example.ne.client.dao.Student;
import com.example.ne.client.utils.ApiResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/addStudent")
    public String addStudent(){
        return "students/addStudent";
    }

    @PostMapping("/register")
    public String addStudent(Model model, HttpServletRequest request) {
        if(request.getSession().getAttribute("token").toString().isEmpty()){
            return "redirect:/auth/login";
        }


        RestTemplate restTemplate =  new RestTemplate();

        Map<String, String> requestBody = new HashMap<>();


        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            requestBody.put(entry.getKey(), entry.getValue()[0]);
        }

        String token = request.getSession().getAttribute("token").toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);


        ResponseEntity<ApiResponse> res = restTemplate.exchange(formatURL("/api/students"), HttpMethod.POST,  entity,  ApiResponse.class);

        return "redirect:/student/allStudents";
    }


    @PostMapping("/{id}/delete")
    public String deleteStudent(Model model, HttpServletRequest request, @PathVariable long id) {
        if(request.getSession().getAttribute("token").toString().isEmpty()){
            return "redirect:/auth/login";
        }


        RestTemplate restTemplate =  new RestTemplate();

        Map<String, String> requestBody = new HashMap<>();


//        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
//            requestBody.put(entry.getKey(), entry.getValue()[0]);
//        }

        String token = request.getSession().getAttribute("token").toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Object> entity = new HttpEntity<>(null, headers);


        ResponseEntity<ApiResponse> res = restTemplate.exchange(formatURL("/api/students/"+id), HttpMethod.DELETE,  entity,  ApiResponse.class);

        return "redirect:/student/allStudents";
    }




}
