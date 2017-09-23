package com.example.tutorial3.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tutorial3.model.StudentModel;
import com.example.tutorial3.service.InMemoryStudentService;
import com.example.tutorial3.service.StudentService;

@Controller
public class StudentController {
	private final StudentService studentService;
	
	public StudentController() {
		studentService = new InMemoryStudentService();
	}
	
	@RequestMapping("/student/add")
	public String add(@RequestParam(value = "npm", required = true) String npm, 
					@RequestParam(value = "name", required = true) String name, 
					@RequestParam(value = "gpa", required = true) double gpa) {
		StudentModel student = new StudentModel(npm, name, gpa);
		studentService.addStudent(student);
		return "add";
	}
	
	@RequestMapping("/student/view") 
	public String view(Model model, @RequestParam(value = "npm", required = true) String npm) {
		StudentModel student = studentService.selectStudent(npm);
		model.addAttribute("student", student);
		return "view";	
	}
	
	 @RequestMapping("/student/viewall")
	 public String viewAll(Model model) {
		 model.addAttribute("students", this.studentService.selectAllStudents());
	     return "viewall";
	 }
	 
	 @RequestMapping("/student/view/{npm}")
	 public String viewbyPath(Model model, @PathVariable(value = "npm", required = true) String npm) {
		 model.addAttribute("student", this.studentService.selectStudent(npm));
	     return "view";
	 }
	 
	 @RequestMapping("/student/delete/{npm}")
	 public String deletebyPath(Model model, @PathVariable(value = "npm", required = true) String npm) {
		 try {
			 this.studentService.deleteStudent(npm);
	     } catch (Exception e) {
	         model.addAttribute("error", e.getMessage());
	         return "error";
	     }
	     return "delete";
	  }

}
