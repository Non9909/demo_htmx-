package org.example.demohtmxspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Stream;

@SpringBootApplication
public class DemoHtmxSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoHtmxSpringBootApplication.class, args);
    }

}

@Component
class Initialize{
    private final StudentRepo studentRepo;

    public Initialize(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        studentRepo.deleteAll();
        Stream.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J")
                .forEach(student -> studentRepo.save(new Student(null,student)));
    }
}

@Controller
@RequestMapping(value = "student")
class StudentController{
    private final StudentRepo studentRepo;

    public StudentController(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }
    @GetMapping
    String getStudents(Model model){
        model.addAttribute("students",this.studentRepo.findAll());
        return "student";
    }
}
interface StudentRepo extends CrudRepository<Student, Long> {}

record Student(@Id Long id , String name){}
