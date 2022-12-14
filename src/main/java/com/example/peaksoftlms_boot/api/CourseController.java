package com.example.peaksoftlms_boot.api;

import com.example.peaksoftlms_boot.model.Course;
import com.example.peaksoftlms_boot.model.Group;
import com.example.peaksoftlms_boot.model.Instructor;
import com.example.peaksoftlms_boot.service.CourseService;
import com.example.peaksoftlms_boot.service.GroupService;
import com.example.peaksoftlms_boot.service.InstructorService;
import com.example.peaksoftlms_boot.service.impl.CompanyServiceImpl;
import com.example.peaksoftlms_boot.service.impl.CourseServiceImpl;
import com.example.peaksoftlms_boot.service.impl.GroupServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class CourseController {
    private final CourseService courseService;
    private final GroupService groupService;
    private final InstructorService instructorService;

    @Autowired
    public CourseController(CourseService courseService, GroupService groupService, InstructorService instructorService) {
        this.courseService = courseService;
        this.groupService = groupService;
        this.instructorService = instructorService;
    }

    @GetMapping("/courses/{id}")
    public String getAllCourses(@PathVariable Long id, Model model,
                                @ModelAttribute("group") Group group,
                                @ModelAttribute("instructor") Instructor instructor) {
        model.addAttribute("courses", courseService.getAllCourses(id));
        model.addAttribute("groups",groupService.getAllListGroup());
        model.addAttribute("instructors",instructorService.getInstructorList());
        model.addAttribute("companyId", id);
        return "/course/courses";
    }

    @GetMapping("/courses/{id}/addCourse")
    public String addCourse(@PathVariable Long id, Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("companyId", id);
        return "/course/addCourse";


    }

    @PostMapping("/{id}/saveCourse")

    public String saveCourse(@ModelAttribute("course") Course course, @PathVariable Long id) {
        courseService.addCourse(id, course);
        return "redirect:/courses/" + id;
    }

    @GetMapping("/update/{id}")
    public String updateCourse(@PathVariable("id") Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        model.addAttribute("companyId", course.getCompany().getId());
        return "/course/update_courses";
    }

    @GetMapping("{companyId}/{id}/saveUpdateCourse")
    public String saveUpdateCourse(@PathVariable("companyId") Long companyId,
                                   @PathVariable("id") Long id,
                                   @ModelAttribute("course") Course course) {
        courseService.updateCourse(course, id);
        return "redirect:/courses/" + companyId;
    }

    @GetMapping("/{companyId}/{id}/deleteCourse")
    public String deleteCourse(@PathVariable("id") Long id, @PathVariable("companyId") Long companyId) {
        courseService.deleteCourse(id);
        return "redirect:/courses/" + companyId;
    }
    @PostMapping("{courseId}/assignGroup")
    private String assignGroup(@PathVariable("courseId") Long courseId,
                               @ModelAttribute("group") Group group) throws IOException {
        Long id = group.getId();
        groupService.assignGroup(courseId, id);
        return "redirect:/groups/" + courseId;
    }

    @PostMapping("{courseId}/assignInstructor")
    private String assignInstructor(@PathVariable("courseId") Long courseId,
                                    @ModelAttribute("instructor") Instructor instructor) throws IOException{

        Long id = instructor.getId();
        instructorService.assignInstructor(courseId, id);
        return "redirect:/instructors/"+courseId;
    }


}