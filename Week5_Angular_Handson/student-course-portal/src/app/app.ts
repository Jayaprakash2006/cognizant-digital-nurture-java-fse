import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';

/**
 * App (Root Component) — Hands-On 1 Task 2
 *
 * The root component rendered inside <app-root> in index.html.
 * Contains:
 *   <app-header>    — persistent navigation bar across all pages
 *   <router-outlet> — Angular's dynamic view slot; replaced by the routed component
 *
 * Replaced the default Angular welcome boilerplate with portal layout.
 */
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  title = 'Student Course Portal';
}
