import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SalonComponentsPage, SalonDeleteDialog, SalonUpdatePage } from './salon.page-object';

const expect = chai.expect;

describe('Salon e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let salonComponentsPage: SalonComponentsPage;
  let salonUpdatePage: SalonUpdatePage;
  let salonDeleteDialog: SalonDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Salons', async () => {
    await navBarPage.goToEntity('salon');
    salonComponentsPage = new SalonComponentsPage();
    await browser.wait(ec.visibilityOf(salonComponentsPage.title), 5000);
    expect(await salonComponentsPage.getTitle()).to.eq('cleoBeautySalonApp.salon.home.title');
  });

  it('should load create Salon page', async () => {
    await salonComponentsPage.clickOnCreateButton();
    salonUpdatePage = new SalonUpdatePage();
    expect(await salonUpdatePage.getPageTitle()).to.eq('cleoBeautySalonApp.salon.home.createOrEditLabel');
    await salonUpdatePage.cancel();
  });

  it('should create and save Salons', async () => {
    const nbButtonsBeforeCreate = await salonComponentsPage.countDeleteButtons();

    await salonComponentsPage.clickOnCreateButton();
    await promise.all([salonUpdatePage.setAddressInput('address'), salonUpdatePage.setPhoneInput('phone')]);
    expect(await salonUpdatePage.getAddressInput()).to.eq('address', 'Expected Address value to be equals to address');
    expect(await salonUpdatePage.getPhoneInput()).to.eq('phone', 'Expected Phone value to be equals to phone');
    await salonUpdatePage.save();
    expect(await salonUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await salonComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Salon', async () => {
    const nbButtonsBeforeDelete = await salonComponentsPage.countDeleteButtons();
    await salonComponentsPage.clickOnLastDeleteButton();

    salonDeleteDialog = new SalonDeleteDialog();
    expect(await salonDeleteDialog.getDialogTitle()).to.eq('cleoBeautySalonApp.salon.delete.question');
    await salonDeleteDialog.clickOnConfirmButton();

    expect(await salonComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
