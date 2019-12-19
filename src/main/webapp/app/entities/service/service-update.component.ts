import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IService, Service } from 'app/shared/model/service.model';
import { ServiceService } from './service.service';
import { IJob } from 'app/shared/model/job.model';
import { JobService } from 'app/entities/job/job.service';

@Component({
  selector: 'jhi-service-update',
  templateUrl: './service-update.component.html'
})
export class ServiceUpdateComponent implements OnInit {
  isSaving: boolean;

  jobs: IJob[];

  editForm = this.fb.group({
    id: [],
    serviceName: [],
    maxDuration: [],
    price: [],
    job: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected serviceService: ServiceService,
    protected jobService: JobService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ service }) => {
      this.updateForm(service);
    });
    this.jobService
      .query()
      .subscribe((res: HttpResponse<IJob[]>) => (this.jobs = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(service: IService) {
    this.editForm.patchValue({
      id: service.id,
      serviceName: service.serviceName,
      maxDuration: service.maxDuration,
      price: service.price,
      job: service.job
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const service = this.createFromForm();
    if (service.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceService.update(service));
    } else {
      this.subscribeToSaveResponse(this.serviceService.create(service));
    }
  }

  private createFromForm(): IService {
    return {
      ...new Service(),
      id: this.editForm.get(['id']).value,
      serviceName: this.editForm.get(['serviceName']).value,
      maxDuration: this.editForm.get(['maxDuration']).value,
      price: this.editForm.get(['price']).value,
      job: this.editForm.get(['job']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IService>>) {
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

  trackJobById(index: number, item: IJob) {
    return item.id;
  }
}
