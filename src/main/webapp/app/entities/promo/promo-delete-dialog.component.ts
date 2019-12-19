import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPromo } from 'app/shared/model/promo.model';
import { PromoService } from './promo.service';

@Component({
  templateUrl: './promo-delete-dialog.component.html'
})
export class PromoDeleteDialogComponent {
  promo: IPromo;

  constructor(protected promoService: PromoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.promoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'promoListModification',
        content: 'Deleted an promo'
      });
      this.activeModal.dismiss(true);
    });
  }
}
