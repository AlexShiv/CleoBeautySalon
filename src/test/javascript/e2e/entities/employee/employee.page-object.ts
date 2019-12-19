import { element, by, ElementFinder } from 'protractor';

export class EmployeeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-employee div table .btn-danger'));
  title = element.all(by.css('jhi-employee div h2#page-heading span')).first();

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

export class EmployeeUpdatePage {
  pageTitle = element(by.id('jhi-employee-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  firstNameInput = element(by.id('field_firstName'));
  lastNameInput = element(by.id('field_lastName'));
  patronomicInput = element(by.id('field_patronomic'));
  experienceInput = element(by.id('field_experience'));
  jobSelect = element(by.id('field_job'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setFirstNameInput(firstName) {
    await this.firstNameInput.sendKeys(firstName);
  }

  async getFirstNameInput() {
    return await this.firstNameInput.getAttribute('value');
  }

  async setLastNameInput(lastName) {
    await this.lastNameInput.sendKeys(lastName);
  }

  async getLastNameInput() {
    return await this.lastNameInput.getAttribute('value');
  }

  async setPatronomicInput(patronomic) {
    await this.patronomicInput.sendKeys(patronomic);
  }

  async getPatronomicInput() {
    return await this.patronomicInput.getAttribute('value');
  }

  async setExperienceInput(experience) {
    await this.experienceInput.sendKeys(experience);
  }

  async getExperienceInput() {
    return await this.experienceInput.getAttribute('value');
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

export class EmployeeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-employee-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-employee'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
