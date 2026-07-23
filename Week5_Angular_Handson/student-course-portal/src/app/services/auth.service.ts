import { Injectable } from '@angular/core';

/**
 * AuthService — Hands-On 6 / 7
 *
 * Simulates authentication state for the route guard demo in Hands-On 7.
 * In a real app, this would check a JWT token or session cookie.
 * isLoggedIn is hardcoded to true — toggle to false to test the guard redirect.
 */
@Injectable({ providedIn: 'root' })
export class AuthService {

  /** Hardcoded for demo — set to false to trigger the auth guard redirect */
  private loggedIn = true;

  isLoggedIn(): boolean {
    return this.loggedIn;
  }

  login(): void {
    this.loggedIn = true;
  }

  logout(): void {
    this.loggedIn = false;
  }
}
