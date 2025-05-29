import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostTagComponent } from './post-tag.component';

describe('PostTagComponent', () => {
  let component: PostTagComponent;
  let fixture: ComponentFixture<PostTagComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PostTagComponent]
    });
    fixture = TestBed.createComponent(PostTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
