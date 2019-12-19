import { element, by, ElementFinder } from 'protractor';

export class PromoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-promo div table .btn-danger'));
  title = element.all(by.css('jhi-promo div h2#page-heading span')).first();

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

export class PromoUpdatePage {
  pageTitle = element(by.id('jhi-promo-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  promoNameInput = element(by.id('field_promoName'));
  descriptionInput = element(by.id('field_description'));
  startDateInput = element(by.id('field_startDate'));
  endDateInput = element(by.id('field_endDate'));
  saleInput = element(by.id('field_sale'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setPromoNameInput(promoName) {
    await this.promoNameInput.sendKeys(promoName);
  }

  async getPromoNameInput() {
    return await this.promoNameInput.getAttribute('value');
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return await this.descriptionInput.getAttribute('value');
  }

  async setStartDateInput(startDate) {
    await this.startDateInput.sendKeys(startDate);
  }

  async getStartDateInput() {
    return await this.startDateInput.getAttribute('value');
  }

  async setEndDateInput(endDate) {
    await this.endDateInput.sendKeys(endDate);
  }

  async getEndDateInput() {
    return await this.endDateInput.getAttribute('value');
  }

  async setSaleInput(sale) {
    await this.saleInput.sendKeys(sale);
  }

  async getSaleInput() {
    return await this.saleInput.getAttribute('value');
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

export class PromoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-promo-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-promo'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
