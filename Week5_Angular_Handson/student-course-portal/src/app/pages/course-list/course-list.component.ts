import { Component, OnInit } from '@angular/core';
import { CourseCardComponent } from '../../components/course-card/course-card.component';

/**
 * CourseListComponent — Hands-On 2 & 3
 *
 * Hands-On 2: @Input/@Output parent-child communication.
 * Hands-On 3:
 *   Task 1 — *ngIf loading state (isLoading with 1.5s setTimeout)
 *            *ngFor with trackBy for performance
 *            *ngSwitch (inside CourseCardComponent per card)
 *            *ngIf with else (empty courses template)
 *   Task 2 — ngClass and ngStyle (inside CourseCardComponent)
 *   Task 3 — appHighlight directive and creditLabel pipe (applied on cards here)
 */
@Component({
  selector: 'app-course-list',
  standalone: true,
  imports: [CourseCardComponent],
  templateUrl: './course-list.component.html',
  styleUrl: './course-list.component.css'
})
export class CourseListComponent implements OnInit {

  /**
   * Hands-On 3 Task 1: loading flag.
   * Set to true initially; cleared after 1.5s to simulate async data fetch.
   * In Hands-On 8 this will be driven by the real HTTP Observable.
   */
  isLoading = true;

  /** 5-course dataset with gradeStatus — extended with enrolled flag for ngClass demo */
  courses = [
    { id: 1, name: 'Data Structures & Algorithms', code: 'CS101', credits: 4, gradeStatus: 'passed'  as const, enrolled: true  },
    { id: 2, name: 'Web Development Fundamentals', code: 'WD201', credits: 3, gradeStatus: 'pending' as const, enrolled: false },
    { id: 3, name: 'Database Management Systems',  code: 'DB301', credits: 4, gradeStatus: 'passed'  as const, enrolled: true  },
    { id: 4, name: 'Machine Learning Basics',       code: 'ML401', credits: 3, gradeStatus: 'failed'  as const, enrolled: false },
    { id: 5, name: 'Software Engineering',          code: 'SE501', credits: 3, gradeStatus: 'pending' as const, enrolled: false },
  ];

  /** Tracks the most recently enrolled course ID (set via @Output from CourseCardComponent) */
  selectedCourseId: number | null = null;

  ngOnInit(): void {
    // Simulate a 1.5-second async load (replaced with HttpClient in Hands-On 8)
    setTimeout(() => {
      this.isLoading = false;
      console.log('CourseListComponent: courses loaded after delay');
    }, 1500);
  }

  /**
   * Hands-On 3 Task 1 — trackBy function for *ngFor.
   *
   * WHY trackBy improves performance:
   * Without trackBy, Angular's *ngFor uses object identity to track list items.
   * When the array reference changes (e.g. after an HTTP reload), Angular
   * destroys and re-creates EVERY DOM element, even if the data is the same.
   * With trackBy returning a stable unique key (course.id), Angular only
   * destroys/creates DOM elements for items that actually changed — much faster
   * for large lists and reduces layout thrash.
   */
  trackByCourseId(index: number, course: { id: number }): number {
    return course.id;
  }

  /** Called when a CourseCardComponent emits (enrollRequested) */
  onEnroll(courseId: number): void {
    console.log('Enrolling in course:', courseId);
    this.selectedCourseId = courseId;
  }
}
