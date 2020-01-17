import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISalon } from 'app/shared/model/salon.model';

@Component({
  selector: 'jhi-salon-detail',
  templateUrl: './salon-detail.component.html'
})
export class SalonDetailComponent implements OnInit {
  salon: ISalon;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ salon }) => {
      this.salon = salon;
    });
  }

  previousState() {
    window.history.back();
  }
}
