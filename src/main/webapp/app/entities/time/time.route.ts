import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Time } from 'app/shared/model/time.model';
import { TimeService } from './time.service';
import { TimeComponent } from './time.component';
import { TimeDetailComponent } from './time-detail.component';
import { TimeUpdateComponent } from './time-update.component';
import { ITime } from 'app/shared/model/time.model';

@Injectable({ providedIn: 'root' })
export class TimeResolve implements Resolve<ITime> {
  constructor(private service: TimeService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITime> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((time: HttpResponse<Time>) => time.body));
    }
    return of(new Time());
  }
}

export const timeRoute: Routes = [
  {
    path: '',
    component: TimeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'cleoBeautySalonApp.time.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TimeDetailComponent,
    resolve: {
      time: TimeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'cleoBeautySalonApp.time.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TimeUpdateComponent,
    resolve: {
      time: TimeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'cleoBeautySalonApp.time.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TimeUpdateComponent,
    resolve: {
      time: TimeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'cleoBeautySalonApp.time.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
