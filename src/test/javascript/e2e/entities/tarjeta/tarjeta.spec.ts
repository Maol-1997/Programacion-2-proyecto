// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { TarjetaComponentsPage, TarjetaDeleteDialog, TarjetaUpdatePage } from './tarjeta.page-object';

const expect = chai.expect;

describe('Tarjeta e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tarjetaUpdatePage: TarjetaUpdatePage;
  let tarjetaComponentsPage: TarjetaComponentsPage;
  let tarjetaDeleteDialog: TarjetaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Tarjetas', async () => {
    await navBarPage.goToEntity('tarjeta');
    tarjetaComponentsPage = new TarjetaComponentsPage();
    await browser.wait(ec.visibilityOf(tarjetaComponentsPage.title), 5000);
    expect(await tarjetaComponentsPage.getTitle()).to.eq('principalApp.tarjeta.home.title');
  });

  it('should load create Tarjeta page', async () => {
    await tarjetaComponentsPage.clickOnCreateButton();
    tarjetaUpdatePage = new TarjetaUpdatePage();
    expect(await tarjetaUpdatePage.getPageTitle()).to.eq('principalApp.tarjeta.home.createOrEditLabel');
    await tarjetaUpdatePage.cancel();
  });

  it('should create and save Tarjetas', async () => {
    const nbButtonsBeforeCreate = await tarjetaComponentsPage.countDeleteButtons();

    await tarjetaComponentsPage.clickOnCreateButton();
    await promise.all([
      tarjetaUpdatePage.setUltDigitosInput('5'),
      tarjetaUpdatePage.setTokenInput('token'),
      tarjetaUpdatePage.clienteSelectLastOption()
    ]);
    expect(await tarjetaUpdatePage.getUltDigitosInput()).to.eq('5', 'Expected ultDigitos value to be equals to 5');
    expect(await tarjetaUpdatePage.getTokenInput()).to.eq('token', 'Expected Token value to be equals to token');
    const selectedAlta = tarjetaUpdatePage.getAltaInput();
    if (await selectedAlta.isSelected()) {
      await tarjetaUpdatePage.getAltaInput().click();
      expect(await tarjetaUpdatePage.getAltaInput().isSelected(), 'Expected alta not to be selected').to.be.false;
    } else {
      await tarjetaUpdatePage.getAltaInput().click();
      expect(await tarjetaUpdatePage.getAltaInput().isSelected(), 'Expected alta to be selected').to.be.true;
    }
    await tarjetaUpdatePage.save();
    expect(await tarjetaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tarjetaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Tarjeta', async () => {
    const nbButtonsBeforeDelete = await tarjetaComponentsPage.countDeleteButtons();
    await tarjetaComponentsPage.clickOnLastDeleteButton();

    tarjetaDeleteDialog = new TarjetaDeleteDialog();
    expect(await tarjetaDeleteDialog.getDialogTitle()).to.eq('principalApp.tarjeta.delete.question');
    await tarjetaDeleteDialog.clickOnConfirmButton();

    expect(await tarjetaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
