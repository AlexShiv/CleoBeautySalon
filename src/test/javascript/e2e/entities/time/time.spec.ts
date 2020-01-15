import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TimeComponentsPage, TimeDeleteDialog, TimeUpdatePage } from './time.page-object';

const expect = chai.expect;

describe('Time e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let timeComponentsPage: TimeComponentsPage;
  let timeUpdatePage: TimeUpdatePage;
  let timeDeleteDialog: TimeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Times', async () => {
    await navBarPage.goToEntity('time');
    timeComponentsPage = new TimeComponentsPage();
    await browser.wait(ec.visibilityOf(timeComponentsPage.title), 5000);
    expect(await timeComponentsPage.getTitle()).to.eq('cleoBeautySalonApp.time.home.title');
  });

  it('should load create Time page', async () => {
    await timeComponentsPage.clickOnCreateButton();
    timeUpdatePage = new TimeUpdatePage();
    expect(await timeUpdatePage.getPageTitle()).to.eq('cleoBeautySalonApp.time.home.createOrEditLabel');
    await timeUpdatePage.cancel();
  });

  it('should create and save Times', async () => {
    const nbButtonsBeforeCreate = await timeComponentsPage.countDeleteButtons();

    await timeComponentsPage.clickOnCreateButton();
    await promise.all([
      timeUpdatePage.setDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      timeUpdatePage.setDurationInput('5'),
      timeUpdatePage.clientSelectLastOption(),
      timeUpdatePage.jobSelectLastOption(),
      timeUpdatePage.salonSelectLastOption()
    ]);
    expect(await timeUpdatePage.getDateInput()).to.contain('2001-01-01T02:30', 'Expected date value to be equals to 2000-12-31');
    expect(await timeUpdatePage.getDurationInput()).to.eq('5', 'Expected duration value to be equals to 5');
    await timeUpdatePage.save();
    expect(await timeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await timeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Time', async () => {
    const nbButtonsBeforeDelete = await timeComponentsPage.countDeleteButtons();
    await timeComponentsPage.clickOnLastDeleteButton();

    timeDeleteDialog = new TimeDeleteDialog();
    expect(await timeDeleteDialog.getDialogTitle()).to.eq('cleoBeautySalonApp.time.delete.question');
    await timeDeleteDialog.clickOnConfirmButton();

    expect(await timeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
