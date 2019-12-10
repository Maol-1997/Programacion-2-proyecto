import { element, by, ElementFinder } from 'protractor';

export class CompraComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-compra div table .btn-danger'));
  title = element.all(by.css('jhi-compra div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class CompraUpdatePage {
  pageTitle = element(by.id('jhi-compra-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  precioInput = element(by.id('field_precio'));
  tarjetaSelect = element(by.id('field_tarjeta'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setPrecioInput(precio) {
    await this.precioInput.sendKeys(precio);
  }

  async getPrecioInput() {
    return await this.precioInput.getAttribute('value');
  }

  async tarjetaSelectLastOption(timeout?: number) {
    await this.tarjetaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async tarjetaSelectOption(option) {
    await this.tarjetaSelect.sendKeys(option);
  }

  getTarjetaSelect(): ElementFinder {
    return this.tarjetaSelect;
  }

  async getTarjetaSelectedOption() {
    return await this.tarjetaSelect.element(by.css('option:checked')).getText();
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class CompraDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-compra-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-compra'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
