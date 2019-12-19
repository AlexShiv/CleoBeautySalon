import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPromo } from 'app/shared/model/promo.model';
import { PromoService } from './promo.service';
import { PromoDeleteDialogComponent } from './promo-delete-dialog.component';

@Component({
  selector: 'jhi-promo',
  templateUrl: './promo.component.html'
})
export class PromoComponent implements OnInit, OnDestroy {
  promos: IPromo[];
  eventSubscriber: Subscription;

  constructor(protected promoService: PromoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.promoService.query().subscribe((res: HttpResponse<IPromo[]>) => {
      this.promos = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInPromos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPromo) {
    return item.id;
  }

  registerChangeInPromos() {
    this.eventSubscriber = this.eventManager.subscribe('promoListModification', () => this.loadAll());
  }

  delete(promo: IPromo) {
    const modalRef = this.modalService.open(PromoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.promo = promo;
  }
}
