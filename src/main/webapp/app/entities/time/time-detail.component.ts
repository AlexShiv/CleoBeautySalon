import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITime } from 'app/shared/model/time.model';

@Component({
  selector: 'jhi-time-detail',
  templateUrl: './time-detail.component.html'
})
export class TimeDetailComponent implements OnInit {
  time: ITime;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ time }) => {
      this.time = time;
    });
  }

  previousState() {
    window.history.back();
  }
}
