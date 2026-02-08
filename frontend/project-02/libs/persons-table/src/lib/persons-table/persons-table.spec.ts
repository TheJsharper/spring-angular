import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PersonsTable } from './persons-table';

describe('PersonsTable', () => {
  let component: PersonsTable;
  let fixture: ComponentFixture<PersonsTable>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PersonsTable],
    }).compileComponents();

    fixture = TestBed.createComponent(PersonsTable);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
