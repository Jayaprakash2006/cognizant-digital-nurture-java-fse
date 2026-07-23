import { Component, OnInit } from '@angular/core';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  FormArray,
  FormControl,
  Validators,
  AbstractControl,
  ValidationErrors
} from '@angular/forms';
import { NgClass } from '@angular/common';

// ─────────────────────────────────────────────────────────────────────────────
// Custom synchronous validator — Hands-On 5 Task 2
// ─────────────────────────────────────────────────────────────────────────────

/**
 * noCourseCode — synchronous validator function.
 *
 * Returns { noCourseCode: true } if the control value starts with 'XX'.
 * Applied alongside Validators.required on the courseId control.
 *
 * Validator functions accept an AbstractControl and return:
 *   ValidationErrors  — an object describing the error(s)
 *   null              — no error (control is valid)
 */
export function noCourseCode(control: AbstractControl): ValidationErrors | null {
  const value: string = control.value ?? '';
  if (value.toUpperCase().startsWith('XX')) {
    return { noCourseCode: true };
  }
  return null;
}

// ─────────────────────────────────────────────────────────────────────────────
// Custom asynchronous validator — Hands-On 5 Task 2
// ─────────────────────────────────────────────────────────────────────────────

/**
 * simulateEmailCheck — async validator that simulates a backend uniqueness check.
 *
 * Async validators run AFTER all sync validators pass — this avoids unnecessary
 * API calls when the field is already invalid (e.g. bad email format).
 *
 * Returns: Promise<ValidationErrors | null>
 *   { emailTaken: true }  — if the email contains 'test@' (simulated taken)
 *   null                  — email is available
 *
 * The control shows 'pending' status while the Promise is resolving.
 */
export function simulateEmailCheck(
  control: AbstractControl
): Promise<ValidationErrors | null> {
  return new Promise(resolve => {
    setTimeout(() => {
      const value: string = (control.value ?? '').toLowerCase();
      resolve(value.includes('test@') ? { emailTaken: true } : null);
    }, 800);
  });
}

// ─────────────────────────────────────────────────────────────────────────────
// Component
// ─────────────────────────────────────────────────────────────────────────────

/**
 * ReactiveEnrollmentFormComponent — Hands-On 5
 *
 * Reactive forms define the entire form structure in TypeScript — making forms:
 *   - Fully unit-testable without a DOM
 *   - Better suited to complex, dynamic scenarios (FormArray, conditional validators)
 *   - More explicit: no magic in the template
 *
 * Key reactive-forms concepts demonstrated:
 *   FormBuilder.group()   — creates a FormGroup (the form model)
 *   FormBuilder.control() — creates individual FormControl instances
 *   FormBuilder.array()   — creates a FormArray for dynamic/repeating controls
 *   [formGroup]           — binds the template <form> to the FormGroup
 *   formControlName       — binds an input to a named control (NO ngModel needed)
 *   [formControl]         — binds an input directly to a FormControl instance
 *
 * enrollForm.value        — object of all non-disabled control values
 * enrollForm.getRawValue()— includes disabled controls too
 * The difference: disabled controls are excluded from .value to prevent
 * accidental submission of data the user wasn't allowed to edit.
 */
@Component({
  selector: 'app-reactive-enrollment-form',
  standalone: true,
  imports: [ReactiveFormsModule, NgClass],
  templateUrl: './reactive-enrollment-form.component.html',
  styleUrl: './reactive-enrollment-form.component.css'
})
export class ReactiveEnrollmentFormComponent implements OnInit {

  enrollForm!: FormGroup;
  submitted = false;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.enrollForm = this.fb.group({
      studentName: [
        '',
        [Validators.required, Validators.minLength(3)]
      ],
      studentEmail: [
        '',
        [Validators.required, Validators.email],
        [simulateEmailCheck]   // async validator as 3rd argument
      ],
      courseId: [
        '',
        [Validators.required, noCourseCode]  // built-in + custom sync validator
      ],
      preferredSemester: [
        'Odd',
        Validators.required
      ],
      agreeToTerms: [
        false,
        // Validators.requiredTrue specifically validates that a checkbox IS checked.
        // Validators.required only checks for a non-empty/non-null value —
        // a checkbox with false would pass required but fail requiredTrue.
        Validators.requiredTrue
      ],
      // FormArray for dynamic course entries — Hands-On 5 Task 2
      additionalCourses: this.fb.array([])
    });
  }

  // ─── FormArray getter ──────────────────────────────────────────────────────

  /**
   * Typed getter for the FormArray.
   *
   * WHY a getter instead of casting in the template:
   *   In the template you'd need:
   *     (enrollForm.get('additionalCourses') as FormArray).controls
   *   which is verbose, repeated everywhere, and breaks if the control name changes.
   *   A getter gives a single typed access point, is reusable, and is unit-testable.
   */
  get additionalCourses(): FormArray {
    return this.enrollForm.get('additionalCourses') as FormArray;
  }

  // ─── Dynamic controls ─────────────────────────────────────────────────────

  /** Adds a new empty course input row to the FormArray */
  addCourse(): void {
    this.additionalCourses.push(
      new FormControl('', Validators.required)
    );
  }

  /** Removes a course input row at the given index */
  removeCourse(index: number): void {
    this.additionalCourses.removeAt(index);
  }

  // ─── Helpers for template ─────────────────────────────────────────────────

  /** Returns the FormControl at a given index in additionalCourses */
  getCourseControl(index: number): FormControl {
    return this.additionalCourses.at(index) as FormControl;
  }

  // ─── Submit / Reset ───────────────────────────────────────────────────────

  onSubmit(): void {
    if (this.enrollForm.valid) {
      /*
       * enrollForm.value        — excludes disabled controls
       * enrollForm.getRawValue()— includes disabled controls
       *
       * Practical example: if you disable the studentEmail field after
       * auto-fill, .value would omit it but .getRawValue() would include it.
       */
      console.log('Form value (excludes disabled):', this.enrollForm.value);
      console.log('Raw value (includes disabled):', this.enrollForm.getRawValue());
      this.submitted = true;
    } else {
      // Mark all controls touched to trigger error display
      this.enrollForm.markAllAsTouched();
    }
  }

  onReset(): void {
    this.enrollForm.reset({
      studentName:       '',
      studentEmail:      '',
      courseId:          '',
      preferredSemester: 'Odd',
      agreeToTerms:      false
    });
    // Clear all dynamic course rows
    while (this.additionalCourses.length) {
      this.additionalCourses.removeAt(0);
    }
    this.submitted = false;
  }
}
