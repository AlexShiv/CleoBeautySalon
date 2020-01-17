import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CleoBeautySalonTestModule } from '../../../test.module';
import { SalonDeleteDialogComponent } from 'app/entities/salon/salon-delete-dialog.component';
import { SalonService } from 'app/entities/salon/salon.service';

describe('Component Tests', () => {
  describe('Salon Management Delete Component', () => {
    let comp: SalonDeleteDialogComponent;
    let fixture: ComponentFixture<SalonDeleteDialogComponent>;
    let service: SalonService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CleoBeautySalonTestModule],
        declarations: [SalonDeleteDialogComponent]
      })
        .overrideTemplate(SalonDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SalonDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SalonService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
