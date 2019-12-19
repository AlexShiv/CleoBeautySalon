import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ServiceComponentsPage, ServiceDeleteDialog, ServiceUpdatePage } from './service.page-object';

const expect = chai.expect;

describe('Service e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let serviceComponentsPage: ServiceComponentsPage;
  let serviceUpdatePage: ServiceUpdatePage;
  let serviceDeleteDialog: ServiceDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Services', async () => {
    await navBarPage.goToEntity('service');
    serviceComponentsPage = new ServiceComponentsPage();
    await browser.wait(ec.visibilityOf(serviceComponentsPage.title), 5000);
    expect(await serviceComponentsPage.getTitle()).to.eq('cleoBeautySalonApp.service.home.title');
  });

  it('should load create Service page', async () => {
    await serviceComponentsPage.clickOnCreateButton();
    serviceUpdatePage = new ServiceUpdatePage();
    expect(await serviceUpdatePage.getPageTitle()).to.eq('cleoBeautySalonApp.service.home.createOrEditLabel');
    await serviceUpdatePage.cancel();
  });

  it('should create and save Services', async () => {
    const nbButtonsBeforeCreate = await serviceComponentsPage.countDeleteButtons();

    await serviceComponentsPage.clickOnCreateButton();
    await promise.all([
      serviceUpdatePage.setServiceNameInput('serviceName'),
      serviceUpdatePage.setMaxDurationInput('5'),
      serviceUpdatePage.setPriceInput('5'),
      serviceUpdatePage.jobSelectLastOption()
    ]);
    expect(await serviceUpdatePage.getServiceNameInput()).to.eq('serviceName', 'Expected ServiceName value to be equals to serviceName');
    expect(await serviceUpdatePage.getMaxDurationInput()).to.eq('5', 'Expected maxDuration value to be equals to 5');
    expect(await serviceUpdatePage.getPriceInput()).to.eq('5', 'Expected price value to be equals to 5');
    await serviceUpdatePage.save();
    expect(await serviceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await serviceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Service', async () => {
    const nbButtonsBeforeDelete = await serviceComponentsPage.countDeleteButtons();
    await serviceComponentsPage.clickOnLastDeleteButton();

    serviceDeleteDialog = new ServiceDeleteDialog();
    expect(await serviceDeleteDialog.getDialogTitle()).to.eq('cleoBeautySalonApp.service.delete.question');
    await serviceDeleteDialog.clickOnConfirmButton();

    expect(await serviceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
