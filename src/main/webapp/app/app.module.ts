import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { CleoBeautySalonSharedModule } from 'app/shared/shared.module';
import { CleoBeautySalonCoreModule } from 'app/core/core.module';
import { CleoBeautySalonAppRoutingModule } from './app-routing.module';
import { CleoBeautySalonHomeModule } from './home/home.module';
import { CleoBeautySalonEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    CleoBeautySalonSharedModule,
    CleoBeautySalonCoreModule,
    CleoBeautySalonHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    CleoBeautySalonEntityModule,
    CleoBeautySalonAppRoutingModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class CleoBeautySalonAppModule {}
