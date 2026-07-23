import { Pipe, PipeTransform } from '@angular/core';

/**
 * CreditLabelPipe — Hands-On 3 Task 3
 *
 * Transforms a credits number into a human-readable string:
 *   null / 0  → 'No Credits'
 *   1         → '1 Credit'
 *   2+        → '2 Credits', '3 Credits', etc.
 *
 * Usage in template:  {{ course.credits | creditLabel }}
 *
 * Pure pipes (default pure: true) only re-run when the INPUT REFERENCE changes.
 * This is an optimisation — Angular skips the pipe if the value hasn't changed.
 * Set pure: false only when the pipe needs to react to mutable object/array mutations,
 * but use this sparingly as it runs on every change-detection cycle.
 */
@Pipe({
  name: 'creditLabel',
  standalone: true,
  pure: true   // default; only re-runs when the input value reference changes
})
export class CreditLabelPipe implements PipeTransform {

  transform(credits: number | null | undefined): string {
    // Edge cases: null, undefined, or 0
    if (credits == null || credits === 0) {
      return 'No Credits';
    }

    return credits === 1 ? '1 Credit' : `${credits} Credits`;
  }
}
