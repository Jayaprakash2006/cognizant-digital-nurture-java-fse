/**
 * Course — Hands-On 6
 * Shared data model used across CourseService, EnrollmentService, and components.
 * Using a TypeScript interface gives compile-time type checking across the entire app
 * — always prefer this over 'any'.
 */
export interface Course {
  id: number;
  name: string;
  code: string;
  credits: number;
  gradeStatus: 'passed' | 'failed' | 'pending';
  enrolled?: boolean;
}
