import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CleoBeautySalonTestModule } from '../../../test.module';
import { SalonDetailComponent } from 'app/entities/salon/salon-detail.component';
import { Salon } from 'app/shared/model/salon.model';

describe('Component Tests', () => {
  describe('Salon Management Detail Component', () => {
    let comp: SalonDetailComponent;
    let fixture: ComponentFixture<SalonDetailComponent>;
    const route = ({ data: of({ salon: new Salon(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CleoBeautySalonTestModule],
        declarations: [SalonDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SalonDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SalonDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.salon).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
