import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CleoBeautySalonTestModule } from '../../../test.module';
import { SalonUpdateComponent } from 'app/entities/salon/salon-update.component';
import { SalonService } from 'app/entities/salon/salon.service';
import { Salon } from 'app/shared/model/salon.model';

describe('Component Tests', () => {
  describe('Salon Management Update Component', () => {
    let comp: SalonUpdateComponent;
    let fixture: ComponentFixture<SalonUpdateComponent>;
    let service: SalonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CleoBeautySalonTestModule],
        declarations: [SalonUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SalonUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SalonUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SalonService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Salon(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Salon();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
