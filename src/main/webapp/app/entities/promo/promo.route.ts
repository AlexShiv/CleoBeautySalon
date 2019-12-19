import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Promo } from 'app/shared/model/promo.model';
import { PromoService } from './promo.service';
import { PromoComponent } from './promo.component';
import { PromoDetailComponent } from './promo-detail.component';
import { PromoUpdateComponent } from './promo-update.component';
import { IPromo } from 'app/shared/model/promo.model';

@Injectable({ providedIn: 'root' })
export class PromoResolve implements Resolve<IPromo> {
  constructor(private service: PromoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPromo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((promo: HttpResponse<Promo>) => promo.body));
    }
    return of(new Promo());
  }
}

export const promoRoute: Routes = [
  {
    path: '',
    component: PromoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'cleoBeautySalonApp.promo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PromoDetailComponent,
    resolve: {
      promo: PromoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'cleoBeautySalonApp.promo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PromoUpdateComponent,
    resolve: {
      promo: PromoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'cleoBeautySalonApp.promo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PromoUpdateComponent,
    resolve: {
      promo: PromoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'cleoBeautySalonApp.promo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
