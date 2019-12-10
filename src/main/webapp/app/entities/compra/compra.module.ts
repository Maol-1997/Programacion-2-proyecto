import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrincipalSharedModule } from 'app/shared/shared.module';
import { CompraComponent } from './compra.component';
import { CompraDetailComponent } from './compra-detail.component';
import { CompraUpdateComponent } from './compra-update.component';
import { CompraDeletePopupComponent, CompraDeleteDialogComponent } from './compra-delete-dialog.component';
import { compraRoute, compraPopupRoute } from './compra.route';

const ENTITY_STATES = [...compraRoute, ...compraPopupRoute];

@NgModule({
  imports: [PrincipalSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [CompraComponent, CompraDetailComponent, CompraUpdateComponent, CompraDeleteDialogComponent, CompraDeletePopupComponent],
  entryComponents: [CompraDeleteDialogComponent]
})
export class PrincipalCompraModule {}
