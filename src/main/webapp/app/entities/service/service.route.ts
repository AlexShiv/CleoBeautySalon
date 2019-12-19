import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Service } from 'app/shared/model/service.model';
import { ServiceService } from './service.service';
import { ServiceComponent } from './service.component';
import { ServiceDetailComponent } from './service-detail.component';
import { ServiceUpdateComponent } from './service-update.component';
import { IService } from 'app/shared/model/service.model';

@Injectable({ providedIn: 'root' })
export class ServiceResolve implements Resolve<IService> {
  constructor(private service: ServiceService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IService> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((service: HttpResponse<Service>) => service.body));
    }
    return of(new Service());
  }
}

export const serviceRoute: Routes = [
  {
    path: '',
    component: ServiceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'cleoBeautySalonApp.service.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceDetailComponent,
    resolve: {
      service: ServiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'cleoBeautySalonApp.service.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceUpdateComponent,
    resolve: {
      service: ServiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'cleoBeautySalonApp.service.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceUpdateComponent,
    resolve: {
      service: ServiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'cleoBeautySalonApp.service.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
