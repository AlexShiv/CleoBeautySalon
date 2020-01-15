import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISalon } from 'app/shared/model/salon.model';
import { SalonService } from './salon.service';
import { SalonDeleteDialogComponent } from './salon-delete-dialog.component';

@Component({
  selector: 'jhi-salon',
  templateUrl: './salon.component.html'
})
export class SalonComponent implements OnInit, OnDestroy {
  salons: ISalon[];
  eventSubscriber: Subscription;

  constructor(protected salonService: SalonService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.salonService.query().subscribe((res: HttpResponse<ISalon[]>) => {
      this.salons = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInSalons();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISalon) {
    return item.id;
  }

  registerChangeInSalons() {
    this.eventSubscriber = this.eventManager.subscribe('salonListModification', () => this.loadAll());
  }

  delete(salon: ISalon) {
    const modalRef = this.modalService.open(SalonDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.salon = salon;
  }
}
