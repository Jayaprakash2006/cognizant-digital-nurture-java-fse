import { Injectable } from '@angular/core';
import { Course } from '../models/course.model';

/**
 * CourseService — Hands-On 6 Task 1
 *
 * providedIn: 'root' makes this a SINGLETON — one instance shared across the
 * entire application tree. Any component or service that injects CourseService
 * receives the exact same instance, so mutations in one place are reflected
 * everywhere (HomeComponent stats and CourseListComponent both see the same array).
 *
 * Why not put this in a component? Components are destroyed/recreated on navigation.
 * A root-level service persists for the application lifetime, making it ideal for
 * shared state.
 *
 * In Hands-On 8, getCourses() / getCourseById() will be replaced with HttpClient
 * calls returning Observables.
 */
@Injectable({ providedIn: 'root' })
export class CourseService {

  /** In-memory course store — replaced with HTTP in Hands-On 8 */
  private courses: Course[] = [
    { id: 1, name: 'Data Structures & Algorithms', code: 'CS101', credits: 4, gradeStatus: 'passed',  enrolled: true  },
    { id: 2, name: 'Web Development Fundamentals', code: 'WD201', credits: 3, gradeStatus: 'pending', enrolled: false },
    { id: 3, name: 'Database Management Systems',  code: 'DB301', credits: 4, gradeStatus: 'passed',  enrolled: true  },
    { id: 4, name: 'Machine Learning Basics',       code: 'ML401', credits: 3, gradeStatus: 'failed',  enrolled: false },
    { id: 5, name: 'Software Engineering',          code: 'SE501', credits: 3, gradeStatus: 'pending', enrolled: false },
  ];

  /** Returns a shallow copy so consumers cannot mutate the internal array directly */
  getCourses(): Course[] {
    return [...this.courses];
  }

  /** Returns the matching Course, or undefined if not found */
  getCourseById(id: number): Course | undefined {
    return this.courses.find(c => c.id === id);
  }

  /** Adds a new course — assigns the next available ID automatically */
  addCourse(course: Omit<Course, 'id'>): Course {
    const newCourse: Course = {
      ...course,
      id: Math.max(0, ...this.courses.map(c => c.id)) + 1
    };
    this.courses.push(newCourse);
    return newCourse;
  }

  /** Updates an existing course by ID; no-op if not found */
  updateCourse(id: number, changes: Partial<Course>): void {
    const index = this.courses.findIndex(c => c.id === id);
    if (index !== -1) {
      this.courses[index] = { ...this.courses[index], ...changes };
    }
  }

  /** Removes a course by ID */
  deleteCourse(id: number): void {
    this.courses = this.courses.filter(c => c.id !== id);
  }
}
