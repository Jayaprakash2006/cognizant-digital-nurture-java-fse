import {
  Component,
  Input,
  Output,
  EventEmitter,
  OnChanges,
  SimpleChanges,
  OnInit
} from '@angular/core';
import { NgClass, NgStyle } from '@angular/common';
import { CreditLabelPipe } from '../../pipes/credit-label.pipe';
import { HighlightDirective } from '../../directives/highlight.directive';
import { EnrollmentService } from '../../services/enrollment.service';
import { Course } from '../../models/course.model';

/**
 * CourseCardComponent — Hands-On 2, 3 & 6
 *
 * HO2: @Input/@Output, ngOnChanges.
 * HO3: ngClass, ngStyle, @switch badge, isExpanded, appHighlight, creditLabel pipe.
 * HO6: Injects EnrollmentService.
 *        enroll()/unenroll() delegate to the service.
 *        isEnrolled() from the service drives the Enroll/Unenroll button label.
 */
@Component({
  selector: 'app-course-card',
  standalone: true,
  imports: [NgClass, NgStyle, CreditLabelPipe, HighlightDirective],
  templateUrl: './course-card.component.html',
  styleUrl: './course-card.component.css'
})
export class CourseCardComponent implements OnInit, OnChanges {

  // ─── @Input ────────────────────────────────────────────────────────────────

  @Input() course: Course = { id: 0, name: '', code: '', credits: 0, gradeStatus: 'pending' };
  @Input() highlightColor = 'lightyellow';

  // ─── @Output ───────────────────────────────────────────────────────────────

  @Output() enrollRequested = new EventEmitter<number>();

  // ─── Local state ───────────────────────────────────────────────────────────

  isExpanded = false;

  /**
   * HO6: Inject EnrollmentService via constructor.
   * Angular resolves this from the root injector — same singleton instance
   * used by every component and service in the app.
   */
  constructor(private enrollmentService: EnrollmentService) {}

  ngOnInit(): void {
    // Sync the card's enrolled display with the service state on first render
  }

  // ─── Computed getters ──────────────────────────────────────────────────────

  /**
   * HO6: Checks enrollment state from the service.
   * Drives the Enroll/Unenroll button label in the template.
   */
  get enrolled(): boolean {
    return this.enrollmentService.isEnrolled(this.course.id);
  }

  /**
   * HO3: ngClass object — kept as a getter to keep the template clean and testable.
   * A getter is recalculated whenever referenced reactive properties change.
   */
  get cardClasses(): Record<string, boolean> {
    return {
      'card--enrolled': this.enrolled,
      'card--full':     this.course.credits >= 4,
      'expanded':       this.isExpanded
    };
  }

  /**
   * HO3: dynamic left-border colour driven by gradeStatus.
   * Use ngStyle only for values that cannot be pre-defined as CSS classes.
   */
  get borderStyle(): Record<string, string> {
    const colours: Record<string, string> = {
      passed:  '#2e7d32',
      failed:  '#c62828',
      pending: '#9e9e9e'
    };
    return { 'border-left': `4px solid ${colours[this.course.gradeStatus] ?? '#9e9e9e'}` };
  }

  // ─── Lifecycle ─────────────────────────────────────────────────────────────

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['course']) {
      console.log('[CourseCardComponent] ngOnChanges:', {
        previous: changes['course'].previousValue,
        current:  changes['course'].currentValue
      });
    }
  }

  // ─── Methods ───────────────────────────────────────────────────────────────

  toggleExpanded(): void {
    this.isExpanded = !this.isExpanded;
  }

  /**
   * HO6: Toggles enrollment via EnrollmentService.
   * Also emits the course ID upward to the parent (CourseListComponent) via @Output.
   */
  toggleEnroll(): void {
    if (this.enrolled) {
      this.enrollmentService.unenroll(this.course.id);
    } else {
      this.enrollmentService.enroll(this.course.id);
      this.enrollRequested.emit(this.course.id);
    }
  }
}
