import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITime } from 'app/shared/model/time.model';
import { TimeService } from './time.service';
import { TimeDeleteDialogComponent } from './time-delete-dialog.component';

@Component({
  selector: 'jhi-time',
  templateUrl: './time.component.html'
})
export class TimeComponent implements OnInit, OnDestroy {
  times: ITime[];
  eventSubscriber: Subscription;

  constructor(protected timeService: TimeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.timeService.query().subscribe((res: HttpResponse<ITime[]>) => {
      this.times = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInTimes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITime) {
    return item.id;
  }

  registerChangeInTimes() {
    this.eventSubscriber = this.eventManager.subscribe('timeListModification', () => this.loadAll());
  }

  delete(time: ITime) {
    const modalRef = this.modalService.open(TimeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.time = time;
  }
}
