import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITime } from 'app/shared/model/time.model';
import { TimeService } from './time.service';

@Component({
  templateUrl: './time-delete-dialog.component.html'
})
export class TimeDeleteDialogComponent {
  time: ITime;

  constructor(protected timeService: TimeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.timeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'timeListModification',
        content: 'Deleted an time'
      });
      this.activeModal.dismiss(true);
    });
  }
}
