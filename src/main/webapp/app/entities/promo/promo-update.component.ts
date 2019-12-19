import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IPromo, Promo } from 'app/shared/model/promo.model';
import { PromoService } from './promo.service';

@Component({
  selector: 'jhi-promo-update',
  templateUrl: './promo-update.component.html'
})
export class PromoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    promoName: [],
    description: [],
    startDate: [],
    endDate: [],
    sale: []
  });

  constructor(protected promoService: PromoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ promo }) => {
      this.updateForm(promo);
    });
  }

  updateForm(promo: IPromo) {
    this.editForm.patchValue({
      id: promo.id,
      promoName: promo.promoName,
      description: promo.description,
      startDate: promo.startDate != null ? promo.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: promo.endDate != null ? promo.endDate.format(DATE_TIME_FORMAT) : null,
      sale: promo.sale
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const promo = this.createFromForm();
    if (promo.id !== undefined) {
      this.subscribeToSaveResponse(this.promoService.update(promo));
    } else {
      this.subscribeToSaveResponse(this.promoService.create(promo));
    }
  }

  private createFromForm(): IPromo {
    return {
      ...new Promo(),
      id: this.editForm.get(['id']).value,
      promoName: this.editForm.get(['promoName']).value,
      description: this.editForm.get(['description']).value,
      startDate:
        this.editForm.get(['startDate']).value != null ? moment(this.editForm.get(['startDate']).value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate']).value != null ? moment(this.editForm.get(['endDate']).value, DATE_TIME_FORMAT) : undefined,
      sale: this.editForm.get(['sale']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPromo>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
