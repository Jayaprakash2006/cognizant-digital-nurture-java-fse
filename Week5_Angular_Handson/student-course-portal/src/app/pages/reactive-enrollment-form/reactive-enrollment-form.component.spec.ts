import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveEnrollmentFormComponent } from './reactive-enrollment-form.component';

describe('ReactiveEnrollmentFormComponent', () => {
  let component: ReactiveEnrollmentFormComponent;
  let fixture: ComponentFixture<ReactiveEnrollmentFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReactiveEnrollmentFormComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(ReactiveEnrollmentFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialise with an invalid form (all fields empty)', () => {
    expect(component.enrollForm.valid).toBeFalse();
  });

  it('additionalCourses getter should return a FormArray', () => {
    expect(component.additionalCourses).toBeTruthy();
    expect(component.additionalCourses.length).toBe(0);
  });

  it('should add a course control when addCourse() is called', () => {
    component.addCourse();
    expect(component.additionalCourses.length).toBe(1);
  });

  it('should remove a course control when removeCourse(0) is called', () => {
    component.addCourse();
    component.removeCourse(0);
    expect(component.additionalCourses.length).toBe(0);
  });
});
