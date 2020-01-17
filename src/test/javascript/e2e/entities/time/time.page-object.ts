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
  durationInput = element(by.id('field_duration'));
  clientSelect = element(by.id('field_client'));
  jobSelect = element(by.id('field_job'));
  salonSelect = element(by.id('field_salon'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDateInput(date) {
    await this.dateInput.sendKeys(date);
  }

  async getDateInput() {
    return await this.dateInput.getAttribute('value');
  }

  async setDurationInput(duration) {
    await this.durationInput.sendKeys(duration);
  }

  async getDurationInput() {
    return await this.durationInput.getAttribute('value');
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

  async salonSelectLastOption() {
    await this.salonSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async salonSelectOption(option) {
    await this.salonSelect.sendKeys(option);
  }

  getSalonSelect(): ElementFinder {
    return this.salonSelect;
  }

  async getSalonSelectedOption() {
    return await this.salonSelect.element(by.css('option:checked')).getText();
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
