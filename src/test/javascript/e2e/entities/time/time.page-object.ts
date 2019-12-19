import { element, by, ElementFinder } from 'protractor';

export class TimeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-time div table .btn-danger'));
  title = element.all(by.css('jhi-time div h2#page-heading span')).first();

  async clickOnCreateButton() {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton() {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class TimeUpdatePage {
  pageTitle = element(by.id('jhi-time-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  dateInput = element(by.id('field_date'));
  phoneInput = element(by.id('field_phone'));
  clientSelect = element(by.id('field_client'));
  serviceSelect = element(by.id('field_service'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDateInput(date) {
    await this.dateInput.sendKeys(date);
  }

  async getDateInput() {
    return await this.dateInput.getAttribute('value');
  }

  async setPhoneInput(phone) {
    await this.phoneInput.sendKeys(phone);
  }

  async getPhoneInput() {
    return await this.phoneInput.getAttribute('value');
  }

  async clientSelectLastOption() {
    await this.clientSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async clientSelectOption(option) {
    await this.clientSelect.sendKeys(option);
  }

  getClientSelect(): ElementFinder {
    return this.clientSelect;
  }

  async getClientSelectedOption() {
    return await this.clientSelect.element(by.css('option:checked')).getText();
  }

  async serviceSelectLastOption() {
    await this.serviceSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async serviceSelectOption(option) {
    await this.serviceSelect.sendKeys(option);
  }

  getServiceSelect(): ElementFinder {
    return this.serviceSelect;
  }

  async getServiceSelectedOption() {
    return await this.serviceSelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class TimeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-time-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-time'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
