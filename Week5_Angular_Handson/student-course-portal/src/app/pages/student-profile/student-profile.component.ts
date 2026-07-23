import { Component } from '@angular/core';

/**
 * StudentProfileComponent — Hands-On 1 Task 2
 * Displays the student's profile information.
 * Will be extended in later hands-on to show enrolled courses.
 */
@Component({
  selector: 'app-student-profile',
  standalone: true,
  imports: [],
  templateUrl: './student-profile.component.html',
  styleUrl: './student-profile.component.css'
})
export class StudentProfileComponent {
  student = {
    name: 'Alex Johnson',
    email: 'alex.johnson@college.edu',
    rollNumber: 'CS2024001',
    semester: 'Odd',
    gpa: 3.8
  };
}
