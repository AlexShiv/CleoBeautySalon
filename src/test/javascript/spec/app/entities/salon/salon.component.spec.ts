import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CleoBeautySalonTestModule } from '../../../test.module';
import { SalonComponent } from 'app/entities/salon/salon.component';
import { SalonService } from 'app/entities/salon/salon.service';
import { Salon } from 'app/shared/model/salon.model';

describe('Component Tests', () => {
  describe('Salon Management Component', () => {
    let comp: SalonComponent;
    let fixture: ComponentFixture<SalonComponent>;
    let service: SalonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CleoBeautySalonTestModule],
        declarations: [SalonComponent],
        providers: []
      })
        .overrideTemplate(SalonComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SalonComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SalonService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Salon(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.salons[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
