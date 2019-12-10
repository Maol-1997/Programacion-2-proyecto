import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'cliente',
        loadChildren: () => import('./cliente/cliente.module').then(m => m.PrincipalClienteModule)
      },
      {
        path: 'tarjeta',
        loadChildren: () => import('./tarjeta/tarjeta.module').then(m => m.PrincipalTarjetaModule)
      },
      {
        path: 'compra',
        loadChildren: () => import('./compra/compra.module').then(m => m.PrincipalCompraModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class PrincipalEntityModule {}
