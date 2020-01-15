import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ITime, Time } from 'app/shared/model/time.model';
import { TimeService } from './time.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';
import { IJob } from 'app/shared/model/job.model';
import { JobService } from 'app/entities/job/job.service';
import { ISalon } from 'app/shared/model/salon.model';
import { SalonService } from 'app/entities/salon/salon.service';

@Component({
  selector: 'jhi-time-update',
  templateUrl: './time-update.component.html'
})
export class TimeUpdateComponent implements OnInit {
  isSaving: boolean;

  clients: IClient[];

  jobs: IJob[];

  salons: ISalon[];

  editForm = this.fb.group({
    id: [],
    date: [],
    duration: [],
    client: [],
    job: [],
    salon: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected timeService: TimeService,
    protected clientService: ClientService,
    protected jobService: JobService,
    protected salonService: SalonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ time }) => {
      this.updateForm(time);
    });
    this.clientService
      .query()
      .subscribe((res: HttpResponse<IClient[]>) => (this.clients = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.jobService
      .query()
      .subscribe((res: HttpResponse<IJob[]>) => (this.jobs = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.salonService
      .query()
      .subscribe((res: HttpResponse<ISalon[]>) => (this.salons = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(time: ITime) {
    this.editForm.patchValue({
      id: time.id,
      date: time.date != null ? time.date.format(DATE_TIME_FORMAT) : null,
      duration: time.duration,
      client: time.client,
      job: time.job,
      salon: time.salon
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const time = this.createFromForm();
    if (time.id !== undefined) {
      this.subscribeToSaveResponse(this.timeService.update(time));
    } else {
      this.subscribeToSaveResponse(this.timeService.create(time));
    }
  }

  private createFromForm(): ITime {
    return {
      ...new Time(),
      id: this.editForm.get(['id']).value,
      date: this.editForm.get(['date']).value != null ? moment(this.editForm.get(['date']).value, DATE_TIME_FORMAT) : undefined,
      duration: this.editForm.get(['duration']).value,
      client: this.editForm.get(['client']).value,
      job: this.editForm.get(['job']).value,
      salon: this.editForm.get(['salon']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITime>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackClientById(index: number, item: IClient) {
    return item.id;
  }

  trackJobById(index: number, item: IJob) {
    return item.id;
  }

  trackSalonById(index: number, item: ISalon) {
    return item.id;
  }
}
