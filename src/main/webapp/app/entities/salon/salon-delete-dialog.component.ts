import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISalon } from 'app/shared/model/salon.model';
import { SalonService } from './salon.service';

@Component({
  templateUrl: './salon-delete-dialog.component.html'
})
export class SalonDeleteDialogComponent {
  salon: ISalon;

  constructor(protected salonService: SalonService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.salonService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'salonListModification',
        content: 'Deleted an salon'
      });
      this.activeModal.dismiss(true);
    });
  }
}
