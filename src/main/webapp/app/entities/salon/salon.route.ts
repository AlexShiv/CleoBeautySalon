import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Salon } from 'app/shared/model/salon.model';
import { SalonService } from './salon.service';
import { SalonComponent } from './salon.component';
import { SalonDetailComponent } from './salon-detail.component';
import { SalonUpdateComponent } from './salon-update.component';
import { ISalon } from 'app/shared/model/salon.model';

@Injectable({ providedIn: 'root' })
export class SalonResolve implements Resolve<ISalon> {
  constructor(private service: SalonService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISalon> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((salon: HttpResponse<Salon>) => salon.body));
    }
    return of(new Salon());
  }
}

export const salonRoute: Routes = [
  {
    path: '',
    component: SalonComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'cleoBeautySalonApp.salon.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SalonDetailComponent,
    resolve: {
      salon: SalonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'cleoBeautySalonApp.salon.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SalonUpdateComponent,
    resolve: {
      salon: SalonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'cleoBeautySalonApp.salon.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SalonUpdateComponent,
    resolve: {
      salon: SalonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'cleoBeautySalonApp.salon.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
