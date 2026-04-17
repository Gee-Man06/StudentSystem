/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package za.ac.cput.shared;

import java.io.Serializable;
import za.ac.cput.shared.AddCourseRequestPackage.AddCourseRequest;

import za.ac.cput.shared.AddStudentRequests.AddStudentRequests;
import za.ac.cput.shared.CoursePackage.Course;
import za.ac.cput.shared.DeleteCoursePackage.DeleteCourse;
import za.ac.cput.shared.DeleteStudentPackage.DeleteStudent;

import za.ac.cput.shared.EnroleStudentsPackage.EnroleStudents;
import za.ac.cput.shared.ShowAvailableCoursesPackage.ShowAvailableCourses;
import za.ac.cput.shared.ShowCoursePackage.ShowCourse;
import za.ac.cput.shared.ShowStudentPackage.ShowStudent;
import za.ac.cput.shared.StudentsPackage.Students;
import za.ac.cput.shared.UpdateStudentPackage.UpdateStudent;
import za.ac.cput.shared.loginNetwork.LoginRequest;
import za.ac.cput.shared.showMyEnroledCoursePackage.ShowMyEnroledCourse;
import za.ac.cput.shared.updateCoursePackage.UpdateCourse;
import za.ac.cput.shared.viewstudentcourse.ViewStudentCourses;

public class Shared {

    public static void main(String[] args) {
   
        LoginRequest req = new LoginRequest("LOGIN", "username", "password", "STUDENT");
        System.out.println("LoginRequest created for user: " + req.getUsername());
      Long studentNumber = 251000001L; 

        
     Students s = new Students(123L, "John", "Doe", "12345", "john@email.com", "pass");
System.out.println("Serializable? " + (s instanceof Serializable));

       // AddStudentRequest req2 = new AddStudentRequest("KASMK",studentNumber,"JJSK","SKLK","KAKSAK","DSJ","SJK");
         AddStudentRequests req14 = new AddStudentRequests("KASMK",studentNumber,"JJSK","SKLK","KAKSAK","DSJ","SJK");
        UpdateStudent updateStudent = new UpdateStudent(studentNumber,"name","surname","cell","gmail","passowrd");
         DeleteStudent req4 = new DeleteStudent(studentNumber);
        ShowStudent req6 = new ShowStudent();
        ShowCourse req7 = new ShowCourse();
        AddCourseRequest r5 = new AddCourseRequest("desc","code");
        Course course = new Course("desc" ,"code");
        
          UpdateCourse updatecourse = new UpdateCourse("desc" ,"code");
           DeleteCourse deleteCourse = new DeleteCourse("code");
           
           System.out.println(req14);
           
           ShowAvailableCourses showavailablecourses=new ShowAvailableCourses();
           
         //  EnroleStudent enrolstudent= new EnroleStudent("StudentNumber", "code");
            EnroleStudents enrolstudents= new EnroleStudents("StudentNumber", "code");
           
           ViewStudentCourses viewstudentcourse = new ViewStudentCourses("code","description");
           ShowMyEnroledCourse req10 = new ShowMyEnroledCourse("student");
           
    }
}
