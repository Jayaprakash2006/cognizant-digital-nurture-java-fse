import { Directive, ElementRef, HostListener, Input, Renderer2 } from '@angular/core';

/**
 * HighlightDirective — Hands-On 3 Task 3
 *
 * Attribute directive that adds a background highlight to the host element
 * on mouseenter and removes it on mouseleave.
 *
 * Usage (default yellow):
 *   <app-course-card appHighlight ...>
 *
 * Usage (custom colour):
 *   <app-course-card appHighlight="lightblue" ...>
 *
 * @HostListener binds to DOM events on the host element without needing to
 * manually call addEventListener / removeEventListener — Angular handles cleanup
 * automatically when the component is destroyed.
 *
 * The directive is configurable via @Input() appHighlight — the input name matches
 * the selector so the caller can pass a value directly on the attribute itself.
 */
@Directive({
  selector: '[appHighlight]',
  standalone: true
})
export class HighlightDirective {

  /**
   * @Input with the same name as the selector allows:
   *   appHighlight="lightblue"   → sets the colour to 'lightblue'
   *   appHighlight               → uses the default 'yellow'
   */
  @Input() appHighlight = 'yellow';

  constructor(private el: ElementRef, private renderer: Renderer2) {}

  /** Fires when the mouse enters the host element — apply highlight */
  @HostListener('mouseenter')
  onMouseEnter(): void {
    this.renderer.setStyle(this.el.nativeElement, 'background-color', this.appHighlight);
    this.renderer.setStyle(this.el.nativeElement, 'transition', 'background-color 0.2s ease');
  }

  /** Fires when the mouse leaves the host element — remove highlight */
  @HostListener('mouseleave')
  onMouseLeave(): void {
    this.renderer.removeStyle(this.el.nativeElement, 'background-color');
  }
}
