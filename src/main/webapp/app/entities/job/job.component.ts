import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IJob } from 'app/shared/model/job.model';
import { JobService } from './job.service';
import { JobDeleteDialogComponent } from './job-delete-dialog.component';

@Component({
  selector: 'jhi-job',
  templateUrl: './job.component.html'
})
export class JobComponent implements OnInit, OnDestroy {
  jobs: IJob[];
  eventSubscriber: Subscription;

  constructor(protected jobService: JobService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.jobService.query().subscribe((res: HttpResponse<IJob[]>) => {
      this.jobs = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInJobs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IJob) {
    return item.id;
  }

  registerChangeInJobs() {
    this.eventSubscriber = this.eventManager.subscribe('jobListModification', () => this.loadAll());
  }

  delete(job: IJob) {
    const modalRef = this.modalService.open(JobDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.job = job;
  }
}
