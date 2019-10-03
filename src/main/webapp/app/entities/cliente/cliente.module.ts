import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrincipalSharedModule } from 'app/shared/shared.module';
import { ClienteComponent } from './cliente.component';
import { ClienteDetailComponent } from './cliente-detail.component';
import { ClienteUpdateComponent } from './cliente-update.component';
import { ClienteDeletePopupComponent, ClienteDeleteDialogComponent } from './cliente-delete-dialog.component';
import { clienteRoute, clientePopupRoute } from './cliente.route';

const ENTITY_STATES = [...clienteRoute, ...clientePopupRoute];

@NgModule({
  imports: [PrincipalSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ClienteComponent,
    ClienteDetailComponent,
    ClienteUpdateComponent,
    ClienteDeleteDialogComponent,
    ClienteDeletePopupComponent
  ],
  entryComponents: [ClienteDeleteDialogComponent]
})
export class PrincipalClienteModule {}
