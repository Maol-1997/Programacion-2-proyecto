import { element, by, ElementFinder } from 'protractor';

export class TarjetaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-tarjeta div table .btn-danger'));
  title = element.all(by.css('jhi-tarjeta div h2#page-heading span')).first();

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

export class TarjetaUpdatePage {
  pageTitle = element(by.id('jhi-tarjeta-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  ultDigitosInput = element(by.id('field_ultDigitos'));
  tokenInput = element(by.id('field_token'));
  altaInput = element(by.id('field_alta'));
  clienteSelect = element(by.id('field_cliente'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setUltDigitosInput(ultDigitos) {
    await this.ultDigitosInput.sendKeys(ultDigitos);
  }

  async getUltDigitosInput() {
    return await this.ultDigitosInput.getAttribute('value');
  }

  async setTokenInput(token) {
    await this.tokenInput.sendKeys(token);
  }

  async getTokenInput() {
    return await this.tokenInput.getAttribute('value');
  }

  getAltaInput(timeout?: number) {
    return this.altaInput;
  }

  async clienteSelectLastOption(timeout?: number) {
    await this.clienteSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async clienteSelectOption(option) {
    await this.clienteSelect.sendKeys(option);
  }

  getClienteSelect(): ElementFinder {
    return this.clienteSelect;
  }

  async getClienteSelectedOption() {
    return await this.clienteSelect.element(by.css('option:checked')).getText();
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

export class TarjetaDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-tarjeta-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-tarjeta'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
