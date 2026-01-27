import { AsyncPipe } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { PeriodicElement, ProductsService } from '@services';
import { Observable } from 'rxjs';



@Component({
  selector: 'lib-products',
  imports: [MatTableModule, AsyncPipe],
  templateUrl: './products.html',
  styleUrl: './products.scss',
})
export class Products implements OnInit {

  private productService: ProductsService = inject(ProductsService);

  periodictElement!: Observable<Array<PeriodicElement>>;

  displayedColumns: string[] = ['position', 'name', 'weight', 'symbol'];

  ngOnInit() {
    this.periodictElement = this.productService.getAllPeriodictElement();
  }
}
