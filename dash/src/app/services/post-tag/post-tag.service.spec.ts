import { TestBed } from '@angular/core/testing';

import { PostTagService } from './post-tag.service';

describe('PostTagService', () => {
  let service: PostTagService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PostTagService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
