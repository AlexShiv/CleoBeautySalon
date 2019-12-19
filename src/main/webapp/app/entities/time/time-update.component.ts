import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ITime, Time } from 'app/shared/model/time.model';
import { TimeService } from './time.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';
import { IService } from 'app/shared/model/service.model';
import { ServiceService } from 'app/entities/service/service.service';

@Component({
  selector: 'jhi-time-update',
  templateUrl: './time-update.component.html'
})
export class TimeUpdateComponent implements OnInit {
  isSaving: boolean;

  clients: IClient[];

  services: IService[];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    date: [],
    phone: [],
    client: [],
    service: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected timeService: TimeService,
    protected clientService: ClientService,
    protected serviceService: ServiceService,
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
    this.serviceService
      .query()
      .subscribe((res: HttpResponse<IService[]>) => (this.services = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(time: ITime) {
    this.editForm.patchValue({
      id: time.id,
      date: time.date,
      phone: time.phone,
      client: time.client,
      service: time.service
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
      date: this.editForm.get(['date']).value,
      phone: this.editForm.get(['phone']).value,
      client: this.editForm.get(['client']).value,
      service: this.editForm.get(['service']).value
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

  trackServiceById(index: number, item: IService) {
    return item.id;
  }
}
