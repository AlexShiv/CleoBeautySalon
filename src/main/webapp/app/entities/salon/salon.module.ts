import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CleoBeautySalonSharedModule } from 'app/shared/shared.module';
import { SalonComponent } from './salon.component';
import { SalonDetailComponent } from './salon-detail.component';
import { SalonUpdateComponent } from './salon-update.component';
import { SalonDeleteDialogComponent } from './salon-delete-dialog.component';
import { salonRoute } from './salon.route';

@NgModule({
  imports: [CleoBeautySalonSharedModule, RouterModule.forChild(salonRoute)],
  declarations: [SalonComponent, SalonDetailComponent, SalonUpdateComponent, SalonDeleteDialogComponent],
  entryComponents: [SalonDeleteDialogComponent]
})
export class CleoBeautySalonSalonModule {}
