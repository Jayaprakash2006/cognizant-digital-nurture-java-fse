import { Injectable } from '@angular/core';
import { Course } from '../models/course.model';
import { CourseService } from './course.service';

/**
 * EnrollmentService — Hands-On 6 Task 2
 *
 * Demonstrates SERVICE-TO-SERVICE injection: EnrollmentService injects CourseService
 * to resolve enrolled course IDs into full Course objects.
 *
 * providedIn: 'root' — singleton shared across the whole app, just like CourseService.
 * Both HomeComponent and CourseCardComponent see the same enrollment state.
 */
@Injectable({ providedIn: 'root' })
export class EnrollmentService {

  /** Set of enrolled course IDs — using Set for O(1) lookup */
  private enrolledCourseIds = new Set<number>();

  /**
   * CourseService is injected here — this is service-to-service injection.
   * Angular's DI resolves CourseService from the root injector and passes
   * the same singleton instance used by all components.
   */
  constructor(private courseService: CourseService) {
    // Pre-populate with courses that were already marked enrolled in CourseService
    this.courseService.getCourses()
      .filter(c => c.enrolled)
      .forEach(c => this.enrolledCourseIds.add(c.id));
  }

  /** Enroll in a course by ID */
  enroll(courseId: number): void {
    this.enrolledCourseIds.add(courseId);
    // Keep the CourseService in sync
    this.courseService.updateCourse(courseId, { enrolled: true });
    console.log('[EnrollmentService] Enrolled in course:', courseId);
  }

  /** Unenroll from a course by ID */
  unenroll(courseId: number): void {
    this.enrolledCourseIds.delete(courseId);
    this.courseService.updateCourse(courseId, { enrolled: false });
    console.log('[EnrollmentService] Unenrolled from course:', courseId);
  }

  /** Returns true if the student is currently enrolled in the given course */
  isEnrolled(courseId: number): boolean {
    return this.enrolledCourseIds.has(courseId);
  }

  /** Resolves enrolled IDs to full Course objects using CourseService */
  getEnrolledCourses(): Course[] {
    return this.courseService.getCourses()
      .filter(c => this.enrolledCourseIds.has(c.id));
  }

  /** Returns the raw set of enrolled IDs (for NgRx integration in HO9) */
  getEnrolledIds(): number[] {
    return [...this.enrolledCourseIds];
  }
}
