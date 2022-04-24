import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogLoginForCommentComponent } from './dialog-login-for-comment.component';

describe('DialogLoginForCommentComponent', () => {
  let component: DialogLoginForCommentComponent;
  let fixture: ComponentFixture<DialogLoginForCommentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogLoginForCommentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogLoginForCommentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
