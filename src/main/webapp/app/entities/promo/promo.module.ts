import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CleoBeautySalonSharedModule } from 'app/shared/shared.module';
import { PromoComponent } from './promo.component';
import { PromoDetailComponent } from './promo-detail.component';
import { PromoUpdateComponent } from './promo-update.component';
import { PromoDeleteDialogComponent } from './promo-delete-dialog.component';
import { promoRoute } from './promo.route';

@NgModule({
  imports: [CleoBeautySalonSharedModule, RouterModule.forChild(promoRoute)],
  declarations: [PromoComponent, PromoDetailComponent, PromoUpdateComponent, PromoDeleteDialogComponent],
  entryComponents: [PromoDeleteDialogComponent]
})
export class CleoBeautySalonPromoModule {}
