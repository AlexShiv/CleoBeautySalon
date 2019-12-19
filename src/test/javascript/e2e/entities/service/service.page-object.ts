import { element, by, ElementFinder } from 'protractor';

export class ServiceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-service div table .btn-danger'));
  title = element.all(by.css('jhi-service div h2#page-heading span')).first();

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

export class ServiceUpdatePage {
  pageTitle = element(by.id('jhi-service-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  serviceNameInput = element(by.id('field_serviceName'));
  maxDurationInput = element(by.id('field_maxDuration'));
  priceInput = element(by.id('field_price'));
  jobSelect = element(by.id('field_job'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setServiceNameInput(serviceName) {
    await this.serviceNameInput.sendKeys(serviceName);
  }

  async getServiceNameInput() {
    return await this.serviceNameInput.getAttribute('value');
  }

  async setMaxDurationInput(maxDuration) {
    await this.maxDurationInput.sendKeys(maxDuration);
  }

  async getMaxDurationInput() {
    return await this.maxDurationInput.getAttribute('value');
  }

  async setPriceInput(price) {
    await this.priceInput.sendKeys(price);
  }

  async getPriceInput() {
    return await this.priceInput.getAttribute('value');
  }

  async jobSelectLastOption() {
    await this.jobSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async jobSelectOption(option) {
    await this.jobSelect.sendKeys(option);
  }

  getJobSelect(): ElementFinder {
    return this.jobSelect;
  }

  async getJobSelectedOption() {
    return await this.jobSelect.element(by.css('option:checked')).getText();
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

export class ServiceDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-service-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-service'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
