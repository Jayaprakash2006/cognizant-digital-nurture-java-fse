import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';

/**
 * EnrollmentFormComponent — Hands-On 4
 *
 * Demonstrates template-driven forms:
 *   - <form #enrollForm="ngForm"> gives access to the NgForm directive instance
 *     which exposes the entire form's validity, value, and state.
 *   - [(ngModel)] + name attribute bind each input to the form model.
 *     name is MANDATORY in template-driven forms — Angular uses it as the
 *     key in form.value.
 *   - #nameCtrl="ngModel" template reference variables give per-field access
 *     to validity, touched/dirty state, and specific errors.
 *
 * Validation state CSS classes (applied automatically by Angular):
 *   ng-valid / ng-invalid — passes or fails validators
 *   ng-pristine / ng-dirty — whether the user has changed the value
 *   ng-untouched / ng-touched — whether the user has focused + left the field
 *
 * WHY use 'touched' for error display (not 'dirty'):
 *   touched = user has visited AND left the field → show errors after they leave
 *   dirty   = user has typed anything → would show errors while still typing
 *   Using 'touched' gives a better UX — errors only appear after interaction.
 */
@Component({
  selector: 'app-enrollment-form',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './enrollment-form.component.html',
  styleUrl: './enrollment-form.component.css'
})
export class EnrollmentFormComponent {

  /** Two-way bound model — ngModel populates this object via name attributes */
  formModel = {
    studentName:       '',
    studentEmail:      '',
    courseId:          '',
    preferredSemester: 'Odd',
    agreeToTerms:      false
  };

  /** Toggled to true after a successful submit — shows the success message */
  submitted = false;

  /**
   * Called on (ngSubmit).
   * @param form — the NgForm instance from #enrollForm="ngForm"
   *
   * form.value  — object with all control values keyed by their name attributes
   * form.valid  — true when all validators pass
   */
  onSubmit(form: NgForm): void {
    console.log('Form value:', form.value);
    console.log('Form valid:', form.valid);

    if (form.valid) {
      this.submitted = true;
    }
  }

  /**
   * Resets all field values and all validation states back to pristine/untouched.
   * Useful for "fill another" UX after a successful submission.
   */
  onReset(form: NgForm): void {
    form.resetForm({
      studentName:       '',
      studentEmail:      '',
      courseId:          '',
      preferredSemester: 'Odd',
      agreeToTerms:      false
    });
    this.submitted = false;
  }
}
