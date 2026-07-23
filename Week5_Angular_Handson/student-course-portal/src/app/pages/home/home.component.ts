import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormsModule } from '@angular/forms';

/**
 * HomeComponent — Hands-On 1 & 2
 *
 * Demonstrates all four Angular binding types:
 *   1. Interpolation          {{ portalName }}           — component → DOM (read-only display)
 *   2. Property binding       [disabled]="!isPortalActive" — component → DOM attribute
 *   3. Event binding          (click)="onEnrollClick()"   — DOM event → component method
 *   4. Two-way binding        [(ngModel)]="searchTerm"    — DOM ↔ component (bidirectional)
 *
 * Difference between [property] and [(ngModel)]:
 *   [property] is ONE-WAY: the component pushes a value to the DOM, but DOM changes
 *   do NOT update the component property.
 *   [(ngModel)] is TWO-WAY: it is shorthand for [ngModel]="prop" (ngModelChange)="prop=$event"
 *   — so changes in the DOM (user input) automatically flow back into the component.
 *
 * Lifecycle hooks used here:
 *   ngOnInit    — fires once after Angular sets the component's @Input values.
 *                 Ideal for data fetching & initialisation (NOT the constructor).
 *   ngOnDestroy — fires just before Angular destroys the component.
 *                 Critical for unsubscribing Observables to prevent memory leaks.
 */
@Component({
  selector: 'app-home',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit, OnDestroy {

  // ─── Hands-On 1 ──────────────────────────────────────────────────────────────

  /** Portal title displayed via string interpolation */
  portalName = 'Student Course Portal';

  // ─── Hands-On 2 — Task 1: All Four Binding Types ─────────────────────────────

  /** Controls whether the Enroll Now button is enabled (property binding demo) */
  isPortalActive = true;

  /** Shown after the enroll button is clicked (event binding demo) */
  message = '';

  /**
   * Two-way binding with [(ngModel)].
   * When the user types in the search input, this property updates instantly.
   * The paragraph below the input reflects it live.
   */
  searchTerm = '';

  // ─── Stats (Hands-On 1 Task 2, updated dynamically in Hands-On 2) ────────────

  coursesAvailable = 12;
  enrolledCount = 3;
  gpa = 3.8;

  // ─── Hands-On 2 — Task 2: Lifecycle Hooks ────────────────────────────────────

  /**
   * ngOnInit — called once after the component's inputs are first set.
   * Use this for data fetching; NOT the constructor (inputs aren't ready there yet).
   */
  ngOnInit(): void {
    // Simulate loading course count (will be replaced with real HTTP in Hands-On 8)
    this.coursesAvailable = 12;
    console.log('HomeComponent initialised — courses loaded:', this.coursesAvailable);
  }

  /**
   * ngOnDestroy — called just before Angular destroys this component.
   * Ideal place to unsubscribe from Observables and clear timers.
   * Without this, long-lived subscriptions cause memory leaks in SPAs.
   */
  ngOnDestroy(): void {
    console.log('HomeComponent destroyed');
  }

  // ─── Event Handler ────────────────────────────────────────────────────────────

  /**
   * Called when the "Enroll Now" button is clicked.
   * Demonstrates event binding: (click)="onEnrollClick()"
   */
  onEnrollClick(): void {
    this.message = 'Enrollment opened!';
  }
}
