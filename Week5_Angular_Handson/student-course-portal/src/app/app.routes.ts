import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { CourseListComponent } from './pages/course-list/course-list.component';
import { StudentProfileComponent } from './pages/student-profile/student-profile.component';
import { EnrollmentFormComponent } from './pages/enrollment-form/enrollment-form.component';
import { ReactiveEnrollmentFormComponent } from './pages/reactive-enrollment-form/reactive-enrollment-form.component';

/**
 * App Routes — Hands-On 1–5
 * Extended in Hands-On 7 with lazy loading, guards, and nested routes.
 */
export const routes: Routes = [
  // Home
  { path: '',                component: HomeComponent,                   title: 'Home — Student Course Portal' },
  // Course browsing
  { path: 'courses',         component: CourseListComponent,             title: 'Courses — Student Course Portal' },
  // Student profile
  { path: 'profile',         component: StudentProfileComponent,         title: 'Profile — Student Course Portal' },
  // Hands-On 4: Template-driven enrollment form
  { path: 'enroll',          component: EnrollmentFormComponent,         title: 'Enroll — Student Course Portal' },
  // Hands-On 5: Reactive enrollment form
  { path: 'enroll-reactive', component: ReactiveEnrollmentFormComponent, title: 'Enroll (Reactive) — Student Course Portal' },
  // Wildcard — must be last
  { path: '**',              redirectTo: '' }
];
