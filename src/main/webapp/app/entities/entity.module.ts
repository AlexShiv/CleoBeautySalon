import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'employee',
        loadChildren: () => import('./employee/employee.module').then(m => m.CleoBeautySalonEmployeeModule)
      },
      {
        path: 'job',
        loadChildren: () => import('./job/job.module').then(m => m.CleoBeautySalonJobModule)
      },
      {
        path: 'promo',
        loadChildren: () => import('./promo/promo.module').then(m => m.CleoBeautySalonPromoModule)
      },
      {
        path: 'service',
        loadChildren: () => import('./service/service.module').then(m => m.CleoBeautySalonServiceModule)
      },
      {
        path: 'time',
        loadChildren: () => import('./time/time.module').then(m => m.CleoBeautySalonTimeModule)
      },
      {
        path: 'client',
        loadChildren: () => import('./client/client.module').then(m => m.CleoBeautySalonClientModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class CleoBeautySalonEntityModule {}
