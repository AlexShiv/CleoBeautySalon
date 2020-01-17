import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISalon, Salon } from 'app/shared/model/salon.model';
import { SalonService } from './salon.service';

@Component({
  selector: 'jhi-salon-update',
  templateUrl: './salon-update.component.html'
})
export class SalonUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    address: [],
    phone: []
  });

  constructor(protected salonService: SalonService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ salon }) => {
      this.updateForm(salon);
    });
  }

  updateForm(salon: ISalon) {
    this.editForm.patchValue({
      id: salon.id,
      address: salon.address,
      phone: salon.phone
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const salon = this.createFromForm();
    if (salon.id !== undefined) {
      this.subscribeToSaveResponse(this.salonService.update(salon));
    } else {
      this.subscribeToSaveResponse(this.salonService.create(salon));
    }
  }

  private createFromForm(): ISalon {
    return {
      ...new Salon(),
      id: this.editForm.get(['id']).value,
      address: this.editForm.get(['address']).value,
      phone: this.editForm.get(['phone']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalon>>) {
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
