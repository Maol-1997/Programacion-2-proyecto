import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrincipalTestModule } from '../../../test.module';
import { TarjetaDetailComponent } from 'app/entities/tarjeta/tarjeta-detail.component';
import { Tarjeta } from 'app/shared/model/tarjeta.model';

describe('Component Tests', () => {
  describe('Tarjeta Management Detail Component', () => {
    let comp: TarjetaDetailComponent;
    let fixture: ComponentFixture<TarjetaDetailComponent>;
    const route = ({ data: of({ tarjeta: new Tarjeta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PrincipalTestModule],
        declarations: [TarjetaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TarjetaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TarjetaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tarjeta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
