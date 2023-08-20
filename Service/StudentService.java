package com.backend.librarymanagementsystem.Service;

import com.backend.librarymanagementsystem.DTO.StudentRequestDto;
import com.backend.librarymanagementsystem.DTO.StudentResponseDto;
import com.backend.librarymanagementsystem.DTO.StudentUpdateEmailRequestDto;
import com.backend.librarymanagementsystem.Entity.LibraryCard;
import com.backend.librarymanagementsystem.Entity.Student;
import com.backend.librarymanagementsystem.Enum.CardStatus;
import com.backend.librarymanagementsystem.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    public void addStudent(StudentRequestDto studentRequestDto) {

        //create student objet
        Student student = new Student();
        student.setName(studentRequestDto.getName());
        student.setAge(studentRequestDto.getAge());
        student.setEmail(studentRequestDto.getEmail());
        student.setDepartment(studentRequestDto.getDepartment());

        //create a card object
        LibraryCard card = new LibraryCard();
        card.setStatus(CardStatus.ACTIVATED);
        card.setStudent(student); //set the value(student_Id) of  foreign key to the card table

        //set card in student
        student.setCard(card);

        studentRepository.save(student); //will save both student and card
    }

    public String findByEmail(String email) {
        Student student = studentRepository.findByEmail(email);
        return student.getName();
    }


    public StudentResponseDto updateEmail(StudentUpdateEmailRequestDto studentUpdateEmailRequestDto) {

        Student student = studentRepository.findById(studentUpdateEmailRequestDto.getId()).get();
        student.setEmail(studentUpdateEmailRequestDto.getEmail());

        //update step
       Student updatedStudent = studentRepository.save(student);

       //convert updated student to Response DTO
        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setId(updatedStudent.getId());
        studentResponseDto.setName(updatedStudent.getName());
        studentResponseDto.setEmail(updatedStudent.getEmail());

        return studentResponseDto;
    }

    public List<Student> findByAge(int age) {
       return studentRepository.findByAge(age);
    }
}
