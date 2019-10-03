import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrincipalSharedModule } from 'app/shared/shared.module';
import { TarjetaComponent } from './tarjeta.component';
import { TarjetaDetailComponent } from './tarjeta-detail.component';
import { TarjetaUpdateComponent } from './tarjeta-update.component';
import { TarjetaDeletePopupComponent, TarjetaDeleteDialogComponent } from './tarjeta-delete-dialog.component';
import { tarjetaRoute, tarjetaPopupRoute } from './tarjeta.route';

const ENTITY_STATES = [...tarjetaRoute, ...tarjetaPopupRoute];

@NgModule({
  imports: [PrincipalSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TarjetaComponent,
    TarjetaDetailComponent,
    TarjetaUpdateComponent,
    TarjetaDeleteDialogComponent,
    TarjetaDeletePopupComponent
  ],
  entryComponents: [TarjetaDeleteDialogComponent]
})
export class PrincipalTarjetaModule {}
