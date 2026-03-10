import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TreeChart } from './tree-chart';

describe('TreeChart', () => {
  let component: TreeChart;
  let fixture: ComponentFixture<TreeChart>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TreeChart],
    }).compileComponents();

    fixture = TestBed.createComponent(TreeChart);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
