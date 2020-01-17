import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'job',
        loadChildren: () => import('./job/job.module').then(m => m.CleoBeautySalonJobModule)
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
      },
      {
        path: 'salon',
        loadChildren: () => import('./salon/salon.module').then(m => m.CleoBeautySalonSalonModule)
      },
      {
        path: 'promo',
        loadChildren: () => import('./promo/promo.module').then(m => m.CleoBeautySalonPromoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class CleoBeautySalonEntityModule {}
