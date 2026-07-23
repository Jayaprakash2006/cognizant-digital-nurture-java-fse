import { Injectable } from '@angular/core';

/**
 * NotificationService — Hands-On 6 Task 2
 *
 * This service is intentionally NOT provided at root level.
 * It is provided at the component level via:
 *   @Component({ providers: [NotificationService] })
 *
 * WHY component-level providing creates a NEW, SEPARATE instance:
 *   Angular's DI system is hierarchical. When you add a service to a component's
 *   providers array, Angular creates a NEW injector scoped to that component subtree.
 *   This means each component instance that provides NotificationService gets its
 *   OWN private copy — not the root singleton.
 *
 *   Use cases:
 *     - Form wizard steps that each need isolated state
 *     - Data-grid rows with per-row services
 *     - Any scenario where you need per-component instance isolation
 */
@Injectable()   // No providedIn — must be provided explicitly where used
export class NotificationService {

  private messages: string[] = [];

  add(message: string): void {
    this.messages.push(message);
    console.log('[NotificationService] New notification:', message);
  }

  getAll(): string[] {
    return [...this.messages];
  }

  clear(): void {
    this.messages = [];
  }
}
