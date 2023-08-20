package com.backend.librarymanagementsystem.Controller;

import com.backend.librarymanagementsystem.DTO.StudentRequestDto;
import com.backend.librarymanagementsystem.DTO.StudentResponseDto;
import com.backend.librarymanagementsystem.DTO.StudentUpdateEmailRequestDto;
import com.backend.librarymanagementsystem.Entity.Student;
import com.backend.librarymanagementsystem.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;
    @PostMapping("/add")
    public String addStudent(@RequestBody StudentRequestDto studentRequestDto){
        studentService.addStudent(studentRequestDto);
        return "Student has been added";
    }

    @GetMapping("/find_by_email")
    public String findStudentByEmail(@RequestParam("email") String email){

        return studentService.findByEmail(email);
    }

@PutMapping("/update_email")
        public StudentResponseDto updateEmail(@RequestBody StudentUpdateEmailRequestDto studentUpdateEmailRequestDto){
            return studentService.updateEmail(studentUpdateEmailRequestDto);
    }

    @GetMapping("/find_by_age")
    public List<Student> findByAge(@RequestParam("age") int age){
        return studentService.findByAge(age);
    }

}
